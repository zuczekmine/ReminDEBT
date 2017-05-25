package com.example.anna.testapplicationremindebt;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentNaleznosci.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FragmentNaleznosci extends Fragment {

    final private String KLUCZ = "naleznosci";
    private OnFragmentInteractionListener mListener;
    private ArrayList<Podmiot> podmiotyDoWyboru = new ArrayList<>();
    private ArrayList<Naleznosc> naleznosci = new ArrayList<>();
    private ArrayList<String> waluty = new ArrayList<>();
    private String nazwaPlikuPodm = "podmioty";
    private String walutaWybranaNal, dataWybranaNal, nazwaNaleznosci, kwotaWybranaNal;
    private FloatingActionButton fabNaleznosci;
    private EditText etNazwaNaleznosci;
    private Podmiot podmiotWybrany = new Podmiot("null");
    private Date data;
    private double kwotaNal;
    private EditText etWybierzDateNal, etKwotaNaleznosci, tytulNaleznosci;
    private Calendar myCalendar = Calendar.getInstance();
    private SimpleDateFormat sdf;
    private DatePickerDialog.OnDateSetListener date;

    public FragmentNaleznosci() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_naleznosci, container, false);
        waluty.add("PLN");
        waluty.add("USD");
        waluty.add("EUR");
        waluty.add("CHF");
        waluty.add("GBP");
        waluty.add("RUB");
        kwotaNal = 0.0;
        walutaWybranaNal = "";
        nazwaNaleznosci = "";
        dataWybranaNal = "";


        try {
            FileInputStream fis = getActivity().openFileInput(nazwaPlikuPodm);
            ObjectInputStream ois = new ObjectInputStream(fis);
            podmiotyDoWyboru =(ArrayList<Podmiot>) ois.readObject();
        } catch(Exception ex) {
            Toast.makeText(FragmentNaleznosci.this.getActivity(), "Błąd pobrania pliku", Toast.LENGTH_SHORT).show();
        }

        BindDictionary<Podmiot> dictionary = new BindDictionary<>();
        dictionary.addStringField(R.id.nazwaPodmiotuLayout, new StringExtractor<Podmiot>() {
            @Override
            public String getStringValue(Podmiot item, int position) {
                return item.getNazwa();
            }
        });
        FunDapter adapter = new FunDapter(FragmentNaleznosci.this.getActivity(),
                podmiotyDoWyboru, R.layout.layout_podmioty, dictionary);

        Spinner spinnerNaleznosci = (Spinner)view.findViewById(R.id.spinnerNaleznosci);
        spinnerNaleznosci.setAdapter(adapter);

        spinnerNaleznosci.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                podmiotWybrany = podmiotyDoWyboru.get(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        BindDictionary<String> dictionaryNalWaluta = new BindDictionary<>();
        dictionaryNalWaluta.addStringField(R.id.nazwaPodmiotuLayout, new StringExtractor<String>() {
            @Override
            public String getStringValue(String item, int position) {
                return item;
            }
        });
        FunDapter adapterWaluta = new FunDapter(FragmentNaleznosci.this.getActivity(),
                waluty, R.layout.layout_podmioty, dictionaryNalWaluta);
        Spinner spinnerNalWaluta = (Spinner)view.findViewById(R.id.spinnerNalWaluty);
        spinnerNalWaluta.setAdapter(adapterWaluta);

        spinnerNalWaluta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                walutaWybranaNal = waluty.get(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tytulNaleznosci = (EditText)view.findViewById(R.id.tytulNaleznosci);
        etKwotaNaleznosci = (EditText)view.findViewById(R.id.etKwotaNaleznosci);
        tytulNaleznosci.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nazwaNaleznosci = tytulNaleznosci.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etKwotaNaleznosci.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                kwotaWybranaNal = etKwotaNaleznosci.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        etWybierzDateNal = (EditText)view.findViewById(R.id.etWybierzDateNal);
        etWybierzDateNal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(FragmentNaleznosci.this.getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fabNaleznosci = (FloatingActionButton)view.findViewById(R.id.fabNaleznosci);
        fabNaleznosci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataWybranaNal.equals("")) {
                    Toast.makeText(FragmentNaleznosci.this.getActivity(), "Wybierz datę!", Toast.LENGTH_SHORT).show();
                } else if (nazwaNaleznosci.equals("")) {
                    Toast.makeText(FragmentNaleznosci.this.getActivity(), "Wybierz nazwę należności!", Toast.LENGTH_SHORT).show();
                } else if (walutaWybranaNal.equals("")) {
                    Toast.makeText(FragmentNaleznosci.this.getActivity(), "Wybierz walutę!", Toast.LENGTH_SHORT).show();
                } else if (kwotaWybranaNal.equals("")) {
                    Toast.makeText(FragmentNaleznosci.this.getActivity(), "Wybierz kwotę!", Toast.LENGTH_SHORT).show();
                } else if (podmiotWybrany.getNazwa() == "null") {
                    Toast.makeText(FragmentNaleznosci.this.getActivity(), "Wybierz podmiot!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        data = sdf.parse(dataWybranaNal);
                    } catch (ParseException e) {
                        Toast.makeText(FragmentNaleznosci.this.getActivity(), "Błąd daty!", Toast.LENGTH_SHORT).show();
                    }
                    kwotaNal = Double.parseDouble(kwotaWybranaNal);
                    Naleznosc naleznosc = new Naleznosc(nazwaNaleznosci, podmiotWybrany, data, kwotaNal, walutaWybranaNal);
                    naleznosci.add(naleznosc);
                    onButtonPressed(naleznosci, KLUCZ);
                }
            }
        });

        return view;
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.UK);

        etWybierzDateNal.setText(sdf.format(myCalendar.getTime()));
        dataWybranaNal = etWybierzDateNal.getText().toString();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(ArrayList naleznosci, String KLUCZ) {
        if (mListener != null) {
            mListener.onFragmentInteraction(naleznosci, KLUCZ);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(ArrayList naleznosci, String KLUCZ);
    }

}
