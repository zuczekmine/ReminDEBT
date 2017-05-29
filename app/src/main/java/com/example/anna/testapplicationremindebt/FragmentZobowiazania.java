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
 * {@link FragmentZobowiazania.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FragmentZobowiazania extends Fragment {

    final private String KLUCZ = "zobowiazania";
    private OnFragmentInteractionListener mListener;
    private ArrayList<Podmiot> podmiotyDoWyboru = new ArrayList<>();
    private ArrayList<Zobowiazanie> zobowiazania = new ArrayList<>();
    private ArrayList<String> waluty = new ArrayList<>();
    private String nazwaPlikuPodm = "podmioty";
    private String walutaWybranaZob, dataWybranaZob, nazwaZobowiazania, kwotaWybranaZob;
    private FloatingActionButton fabZobowiazania;
    private EditText etNazwaZobowiazania;
    private Podmiot podmiotWybrany = new Podmiot("null");
    private Date data;
    private double kwotaZob;
    private EditText etWybierzDateZob, etKwotaZobowiazania, tytulZobowiazania;
    private Calendar myCalendar = Calendar.getInstance();
    private SimpleDateFormat sdf;
    private DatePickerDialog.OnDateSetListener date;

    public FragmentZobowiazania() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zobowiazania, container, false);
        waluty.add("PLN");
        waluty.add("USD");
        waluty.add("EUR");
        kwotaZob = 0.0;
        walutaWybranaZob = "";
        nazwaZobowiazania = "";
        kwotaWybranaZob = "";
        dataWybranaZob = "";


        try {
            FileInputStream fis = getActivity().openFileInput(nazwaPlikuPodm);
            ObjectInputStream ois = new ObjectInputStream(fis);
            podmiotyDoWyboru =(ArrayList<Podmiot>) ois.readObject();
        } catch(Exception ex) {
            Toast.makeText(FragmentZobowiazania.this.getActivity(), "Błąd pobrania pliku", Toast.LENGTH_SHORT).show();
        }

        BindDictionary<Podmiot> dictionary = new BindDictionary<>();
        dictionary.addStringField(R.id.nazwaPodmiotuLayout, new StringExtractor<Podmiot>() {
            @Override
            public String getStringValue(Podmiot item, int position) {
                return item.getNazwa();
            }
        });
        FunDapter adapter = new FunDapter(FragmentZobowiazania.this.getActivity(),
                podmiotyDoWyboru, R.layout.layout_podmioty, dictionary);

        Spinner spinnerZobowiazania = (Spinner)view.findViewById(R.id.spinnerZobowiazania);
        spinnerZobowiazania.setAdapter(adapter);

        spinnerZobowiazania.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                podmiotWybrany = podmiotyDoWyboru.get(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        BindDictionary<String> dictionaryZobWaluta = new BindDictionary<>();
        dictionaryZobWaluta.addStringField(R.id.nazwaPodmiotuLayout, new StringExtractor<String>() {
            @Override
            public String getStringValue(String item, int position) {
                return item;
            }
        });
        FunDapter adapterWaluta = new FunDapter(FragmentZobowiazania.this.getActivity(),
                waluty, R.layout.layout_podmioty, dictionaryZobWaluta);
        Spinner spinnerZobWaluta = (Spinner)view.findViewById(R.id.spinnerZobWaluty);
        spinnerZobWaluta.setAdapter(adapterWaluta);

        spinnerZobWaluta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                walutaWybranaZob = waluty.get(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tytulZobowiazania = (EditText)view.findViewById(R.id.tytulZobowiazania);
        etKwotaZobowiazania = (EditText)view.findViewById(R.id.etKwotaZobowiazania);
        tytulZobowiazania.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nazwaZobowiazania = tytulZobowiazania.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        etKwotaZobowiazania.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                kwotaWybranaZob = etKwotaZobowiazania.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        etWybierzDateZob = (EditText)view.findViewById(R.id.etWybierzDateZob);
        etWybierzDateZob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(FragmentZobowiazania.this.getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fabZobowiazania = (FloatingActionButton)view.findViewById(R.id.fabZobowiazania);
        fabZobowiazania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataWybranaZob.equals("")) {
                    Toast.makeText(FragmentZobowiazania.this.getActivity(), "Wybierz datę!", Toast.LENGTH_SHORT).show();
                } else if (nazwaZobowiazania.equals("")) {
                    Toast.makeText(FragmentZobowiazania.this.getActivity(), "Wybierz nazwę zobowiązania!", Toast.LENGTH_SHORT).show();
                } else if (walutaWybranaZob.equals("")) {
                    Toast.makeText(FragmentZobowiazania.this.getActivity(), "Wybierz walutę!", Toast.LENGTH_SHORT).show();
                } else if (kwotaWybranaZob.equals("")) {
                    Toast.makeText(FragmentZobowiazania.this.getActivity(), "Wybierz kwotę!", Toast.LENGTH_SHORT).show();
                } else if (podmiotWybrany.getNazwa() == "null") {
                    Toast.makeText(FragmentZobowiazania.this.getActivity(), "Wybierz podmiot!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        data = sdf.parse(dataWybranaZob);
                    } catch (ParseException e) {
                        Toast.makeText(FragmentZobowiazania.this.getActivity(), "Błąd daty!", Toast.LENGTH_SHORT).show();
                    }
                    kwotaZob = Double.parseDouble(kwotaWybranaZob);
                    Zobowiazanie zobowiazanie = new Zobowiazanie(nazwaZobowiazania, podmiotWybrany, data, kwotaZob, walutaWybranaZob);
                    zobowiazania.add(zobowiazanie);
                    onButtonPressed(zobowiazania, KLUCZ);
                }
            }
        });

        return view;
    }

   private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat);

        etWybierzDateZob.setText(sdf.format(myCalendar.getTime()));
        dataWybranaZob = etWybierzDateZob.getText().toString();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(ArrayList zobowiazania, String KLUCZ) {
        if (mListener != null) {
            mListener.onFragmentInteraction(zobowiazania, KLUCZ);
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
        void onFragmentInteraction(ArrayList zobowiazania, String KLUCZ);
    }

}
