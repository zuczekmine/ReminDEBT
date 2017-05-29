package com.example.anna.testapplicationremindebt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;

import org.joda.time.DateTime;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStatystyki extends Fragment {
    private ArrayList<String> waluty = new ArrayList<>();
    private ArrayList<Naleznosc> naleznosci;
    private ArrayList<Zobowiazanie> zobowiazania;
    private ArrayList<String> stawki;
    private String nazwaPlikuWaluty = "waluty";
    private String nazwaPlikuNal = "naleznosci";
    private String nazwaPlikuZob = "zobowiazania";
    private String walutaWybranaStat = "";
    private double kwotaCalkowitaNalMies, kwotaCalkowitaNalRok, kwotaCalkowitaZobMies, kwotaCalkowitaZobRok, przelicznik;
    private boolean czyJestNal, czyJestZob;
    private DateTime dzisiaj, dataMiesiac, dataRok;
    private TextView tvKwotaRokNal, tvKwotaRokZob, tvKwotaMiesNal, tvKwotaMiesZob;



    public FragmentStatystyki() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statystyki, container, false);
        waluty.add("PLN");
        waluty.add("USD");
        waluty.add("EUR");
        czyJestNal = false;
        czyJestZob = false;
        kwotaCalkowitaZobRok = 0.0;
        kwotaCalkowitaZobMies = 0.0;
        kwotaCalkowitaNalRok = 0.0;
        kwotaCalkowitaNalMies = 0.0;
        tvKwotaRokNal = (TextView)view.findViewById(R.id.tvKwotaRokNal);
        tvKwotaMiesNal = (TextView)view.findViewById(R.id.tvKwotaMiesNal);
        tvKwotaRokZob = (TextView)view.findViewById(R.id.tvKwotaRokZob);
        tvKwotaMiesZob = (TextView)view.findViewById(R.id.tvKwotaMiesZob);


        FileInputStream fis;
        try {
            fis = getActivity().openFileInput(nazwaPlikuNal);
            ObjectInputStream ois = new ObjectInputStream(fis);
            naleznosci =(ArrayList<Naleznosc>) ois.readObject();
            if(!naleznosci.isEmpty()) {
                czyJestNal = true;
            }
        } catch (Exception e) {e.printStackTrace();}
        try {
            fis = getActivity().openFileInput(nazwaPlikuZob);
            ObjectInputStream ois = new ObjectInputStream(fis);
            zobowiazania =(ArrayList<Zobowiazanie>) ois.readObject();
            if(!zobowiazania.isEmpty()) {
                czyJestZob = true;
            }
        } catch (Exception e) {e.printStackTrace();}
        try {
            fis = getActivity().openFileInput(nazwaPlikuWaluty);
            ObjectInputStream ois = new ObjectInputStream(fis);
            stawki =(ArrayList<String>) ois.readObject();
        } catch (Exception e) {e.printStackTrace();}

        if(czyJestNal == false && czyJestZob == false) {
            FragmentPusty fragmentPusty = new FragmentPusty();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.layoutMain, fragmentPusty).commit();
        } else {
            BindDictionary<String> dictionaryZobWaluta = new BindDictionary<>();
            dictionaryZobWaluta.addStringField(R.id.nazwaPodmiotuLayout, new StringExtractor<String>() {
                @Override
                public String getStringValue(String item, int position) {
                    return item;
                }
            });
            FunDapter adapterWaluta = new FunDapter(FragmentStatystyki.this.getActivity(),
                    waluty, R.layout.layout_podmioty, dictionaryZobWaluta);
            Spinner spinnerZobWaluta = (Spinner) view.findViewById(R.id.spinnerStatWaluty);
            spinnerZobWaluta.setAdapter(adapterWaluta);

            spinnerZobWaluta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    kwotaCalkowitaZobRok = 0.0;
                    kwotaCalkowitaZobMies = 0.0;
                    kwotaCalkowitaNalRok = 0.0;
                    kwotaCalkowitaNalMies = 0.0;
                    walutaWybranaStat = waluty.get(pos);
                    obliczKwoty(walutaWybranaStat);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        return view;
    }

    public void obliczKwoty(String walutaWybrana) {
        dzisiaj = new DateTime();
        if (!naleznosci.isEmpty()) {
            for (Naleznosc naleznosc : naleznosci) {
                String walutaNal = naleznosc.getWaluta();
                dataMiesiac = new DateTime(naleznosc.getData());
                if (dzisiaj.monthOfYear().equals(dataMiesiac.monthOfYear())) {
                    if (walutaNal == walutaWybrana) {
                        kwotaCalkowitaNalMies += naleznosc.getKwota();
                    } else {
                        String pomoc = walutaWybrana+"/" + walutaNal;
                        switch (pomoc) {
                            case "PLN/EUR":
                                przelicznik = Double.parseDouble(stawki.get(0));
                                break;
                            case "EUR/PLN":
                                przelicznik = Double.parseDouble(stawki.get(1));
                                break;
                            case "PLN/USD":
                                przelicznik = Double.parseDouble(stawki.get(2));
                                break;
                            case "USD/PLN":
                                przelicznik = Double.parseDouble(stawki.get(3));
                                break;
                            case "EUR/USD":
                                przelicznik = Double.parseDouble(stawki.get(4));
                                break;
                            case "USD/EUR":
                                przelicznik = Double.parseDouble(stawki.get(5));
                                break;
                        }
                        kwotaCalkowitaNalMies += naleznosc.getKwota() * przelicznik;
                        kwotaCalkowitaNalMies *= 100;
                        kwotaCalkowitaNalMies = Math.round(kwotaCalkowitaZobRok);
                        kwotaCalkowitaNalMies /= 100;
                    }

                }
                dataRok = new DateTime(naleznosc.getData());
                if (dzisiaj.year().equals(dataRok.year())) {
                    if (walutaNal == walutaWybrana) {
                        kwotaCalkowitaNalRok += naleznosc.getKwota();
                    } else {
                        String pomoc = walutaWybrana+"/" + walutaNal;
                        switch (pomoc) {
                            case "PLN/EUR":
                                przelicznik = Double.parseDouble(stawki.get(0));
                                break;
                            case "EUR/PLN":
                                przelicznik = Double.parseDouble(stawki.get(1));
                                break;
                            case "PLN/USD":
                                przelicznik = Double.parseDouble(stawki.get(2));
                                break;
                            case "USD/PLN":
                                przelicznik = Double.parseDouble(stawki.get(3));
                                break;
                            case "EUR/USD":
                                przelicznik = Double.parseDouble(stawki.get(4));
                                break;
                            case "USD/EUR":
                                przelicznik = Double.parseDouble(stawki.get(5));
                                break;
                        }
                        kwotaCalkowitaNalRok += naleznosc.getKwota() * przelicznik;
                        kwotaCalkowitaNalRok *= 100;
                        kwotaCalkowitaNalRok = Math.round(kwotaCalkowitaZobRok);
                        kwotaCalkowitaNalRok /= 100;
                    }
                }

            }
        }
        if (!zobowiazania.isEmpty()) {
            for (Zobowiazanie zobowiazanie : zobowiazania) {
                String walutaZob = zobowiazanie.getWaluta();
                dataMiesiac = new DateTime(zobowiazanie.getData());
                if (dzisiaj.monthOfYear().equals(dataMiesiac.monthOfYear())) {
                    if (walutaZob == walutaWybrana) {
                        kwotaCalkowitaZobMies += zobowiazanie.getKwota();
                    } else {
                        String pomoc = walutaZob+"/" + walutaWybrana;
                        Log.i("STATYSTYKI", pomoc);
                        switch (pomoc) {
                            case "PLN/EUR":
                                przelicznik = Double.parseDouble(stawki.get(0));
                                break;
                            case "EUR/PLN":
                                przelicznik = Double.parseDouble(stawki.get(1));
                                break;
                            case "PLN/USD":
                                przelicznik = Double.parseDouble(stawki.get(2));
                                break;
                            case "USD/PLN":
                                przelicznik = Double.parseDouble(stawki.get(3));
                                break;
                            case "EUR/USD":
                                przelicznik = Double.parseDouble(stawki.get(4));
                                break;
                            case "USD/EUR":
                                przelicznik = Double.parseDouble(stawki.get(5));
                                break;
                        }
                        kwotaCalkowitaZobMies += zobowiazanie.getKwota() * przelicznik;
                        kwotaCalkowitaZobMies *= 100;
                        kwotaCalkowitaZobMies = Math.round(kwotaCalkowitaZobRok);
                        kwotaCalkowitaZobMies /= 100;
                    }

                }
                dataRok = new DateTime(zobowiazanie.getData());
                if (dzisiaj.year().equals(dataRok.year())) {
                    if (walutaZob == walutaWybrana) {
                        kwotaCalkowitaZobRok += zobowiazanie.getKwota();
                    } else {
                        String pomoc = walutaZob+"/" +walutaWybrana;
                        switch (pomoc) {
                            case "PLN/EUR":
                                przelicznik = Double.parseDouble(stawki.get(0));
                                break;
                            case "EUR/PLN":
                                przelicznik = Double.parseDouble(stawki.get(1));
                                break;
                            case "PLN/USD":
                                przelicznik = Double.parseDouble(stawki.get(2));
                                break;
                            case "USD/PLN":
                                przelicznik = Double.parseDouble(stawki.get(3));
                                break;
                            case "EUR/USD":
                                przelicznik = Double.parseDouble(stawki.get(4));
                                break;
                            case "USD/EUR":
                                przelicznik = Double.parseDouble(stawki.get(5));
                                break;
                        }
                        kwotaCalkowitaZobRok += zobowiazanie.getKwota() * przelicznik;
                        kwotaCalkowitaZobRok *= 100;
                        kwotaCalkowitaZobRok = Math.round(kwotaCalkowitaZobRok);
                        kwotaCalkowitaZobRok /= 100;
                    }
                }

            }
        }
        tvKwotaRokNal.setText(String.valueOf(kwotaCalkowitaNalRok));
        tvKwotaMiesNal.setText(String.valueOf(kwotaCalkowitaNalMies));
        tvKwotaRokZob.setText(String.valueOf(kwotaCalkowitaZobRok));
        tvKwotaMiesZob.setText(String.valueOf(kwotaCalkowitaZobMies));
    }

}
