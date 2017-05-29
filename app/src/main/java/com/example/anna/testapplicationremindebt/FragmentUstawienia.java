package com.example.anna.testapplicationremindebt;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentUstawienia.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FragmentUstawienia extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Button btnUsunPodmiot, btnUsunNal, btnUsunZob;
    private Spinner spinnerUsunPodm, spinnerUsunNal, spinnerUsunZob;
    private final String KLUCZ_PODM = "podmiotyUsun";
    private final String KLUCZ_NAL = "naleznosciUsun";
    private final String KLUCZ_ZOB = "zobowiazaniaUsun";
    private ArrayList<Podmiot> podmioty = new ArrayList<>();
    private ArrayList<Naleznosc> naleznosci = new ArrayList<>();
    private ArrayList<Zobowiazanie> zobowiazania = new ArrayList<>();
    private String nazwaPlikuPodm = "podmioty";
    private String nazwaPlikuNal = "naleznosci";
    private String nazwaPlikuZob = "zobowiazania";
    private int pozycjaUsunPodm, pozycjaUsunNal, pozycjaUsunZob;

    public FragmentUstawienia() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ustawienia, container, false);

        try {
            FileInputStream fis = getActivity().openFileInput(nazwaPlikuPodm);
            ObjectInputStream ois = new ObjectInputStream(fis);
            podmioty =(ArrayList<Podmiot>) ois.readObject();
        } catch(Exception ex) {
            Toast.makeText(FragmentUstawienia.this.getActivity(), "Błąd pobrania podmiotów", Toast.LENGTH_SHORT).show();
        }

        try {
            FileInputStream fis = getActivity().openFileInput(nazwaPlikuNal);
            ObjectInputStream ois = new ObjectInputStream(fis);
            naleznosci =(ArrayList<Naleznosc>) ois.readObject();
        } catch(Exception ex) {
            Toast.makeText(FragmentUstawienia.this.getActivity(), "Błąd pobrania naleznosci", Toast.LENGTH_SHORT).show();
        }

        try {
            FileInputStream fis = getActivity().openFileInput(nazwaPlikuZob);
            ObjectInputStream ois = new ObjectInputStream(fis);
            zobowiazania =(ArrayList<Zobowiazanie>) ois.readObject();
        } catch(Exception ex) {
            Toast.makeText(FragmentUstawienia.this.getActivity(), "Błąd pobrania zobowiązań", Toast.LENGTH_SHORT).show();
        }

        BindDictionary<Podmiot> dictionary = new BindDictionary<>();
        dictionary.addStringField(R.id.nazwaPodmiotuLayout, new StringExtractor<Podmiot>() {
            @Override
            public String getStringValue(Podmiot item, int position) {
                return item.getNazwa();
            }
        });
        FunDapter adapter = new FunDapter(FragmentUstawienia.this.getActivity(),
                podmioty, R.layout.layout_podmioty, dictionary);

        spinnerUsunPodm = (Spinner)view.findViewById(R.id.spinnerUsunPodm);
        spinnerUsunPodm.setAdapter(adapter);

        spinnerUsunPodm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                pozycjaUsunPodm = pos;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        BindDictionary<Naleznosc> dictionaryNal = new BindDictionary<>();
        dictionaryNal.addStringField(R.id.nazwaPodmiotuLayout, new StringExtractor<Naleznosc>() {
            @Override
            public String getStringValue(Naleznosc item, int position) {
                return item.getNazwa();
            }
        });
        FunDapter adapterNal = new FunDapter(FragmentUstawienia.this.getActivity(),
                naleznosci, R.layout.layout_podmioty, dictionaryNal);

        spinnerUsunNal = (Spinner)view.findViewById(R.id.spinnerUsunNal);
        spinnerUsunNal.setAdapter(adapterNal);

        spinnerUsunNal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                pozycjaUsunNal = pos;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        BindDictionary<Zobowiazanie> dictionaryZob = new BindDictionary<>();
        dictionaryZob.addStringField(R.id.nazwaPodmiotuLayout, new StringExtractor<Zobowiazanie>() {
            @Override
            public String getStringValue(Zobowiazanie item, int position) {
                return item.getNazwa();
            }
        });
        FunDapter adapterZob = new FunDapter(FragmentUstawienia.this.getActivity(),
                zobowiazania, R.layout.layout_podmioty, dictionaryZob);

        spinnerUsunZob = (Spinner)view.findViewById(R.id.spinnerUsunZob);
        spinnerUsunZob.setAdapter(adapterZob);

        spinnerUsunZob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                pozycjaUsunZob = pos;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnUsunPodmiot = (Button)view.findViewById(R.id.btnUsunPodmiot);
        btnUsunPodmiot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(podmioty.isEmpty()) {
                    Toast.makeText(FragmentUstawienia.this.getActivity(), "Brak podmiotu do usunięcia!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(FragmentUstawienia.this.getActivity(), "Usunięto podmiot: "+ podmioty.get(pozycjaUsunPodm), Toast.LENGTH_SHORT).show();
                    podmioty.remove(pozycjaUsunPodm);
                    onButtonPressed(podmioty, KLUCZ_PODM);
                }

            }
        });

        btnUsunNal = (Button)view.findViewById(R.id.btnUsunNal);
        btnUsunNal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(naleznosci.isEmpty()) {
                    Toast.makeText(FragmentUstawienia.this.getActivity(), "Brak należności do usunięcia!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FragmentUstawienia.this.getActivity(), "Usunięto należność: "+ naleznosci.get(pozycjaUsunNal), Toast.LENGTH_SHORT).show();
                    naleznosci.remove(pozycjaUsunNal);
                    onButtonPressed(naleznosci, KLUCZ_NAL);
                }
            }
        });

        btnUsunZob = (Button)view.findViewById(R.id.btnUsunZob);
        btnUsunZob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zobowiazania.isEmpty()) {
                    Toast.makeText(FragmentUstawienia.this.getActivity(), "Brak należności do usunięcia!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FragmentUstawienia.this.getActivity(), "Usunięto zobowiązanie: "+ zobowiazania.get(pozycjaUsunZob), Toast.LENGTH_SHORT).show();
                    zobowiazania.remove(pozycjaUsunZob);
                    onButtonPressed(zobowiazania, KLUCZ_ZOB);
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(ArrayList lista, String KLUCZ) {
        if (mListener != null) {
            mListener.onFragmentInteraction(lista, KLUCZ);
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
        void onFragmentInteraction(ArrayList lista, String KLUCZ);
    }

}
