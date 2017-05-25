package com.example.anna.testapplicationremindebt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDodajPodm.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FragmentDodajPodm extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ArrayList<Podmiot> podmioty = new ArrayList<>();
    private String nazwaPodmiotu;
    final private String KLUCZ = "podmioty";
    private boolean czyPrzedsiebiorstwo;

    private OnFragmentInteractionListener mListener;

    public FragmentDodajPodm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentDodajPodm.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View VIEW = inflater.inflate(R.layout.fragment_dodaj_podm, container, false);
        CheckBox checkBox = (CheckBox) VIEW.findViewById(R.id.chbxPodm);
        checkBox.setOnCheckedChangeListener(myCheckboxListener);
        FloatingActionButton fab = (FloatingActionButton)VIEW.findViewById(R.id.btnDodajPodm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nazwa =(EditText)VIEW.findViewById(R.id.nazwaPodmiotu);
                nazwaPodmiotu = nazwa.getText().toString();
                if(czyPrzedsiebiorstwo) {
                    podmioty.add(new Przedsiebiorstwo(nazwaPodmiotu));
                    onButtonPressed(podmioty, KLUCZ);
                    Toast.makeText(FragmentDodajPodm.this.getActivity(), "Zapisano przedsiębiorstwo", Toast.LENGTH_LONG).show();
                } else {
                    podmioty.add(new OsobaPrywatna(nazwaPodmiotu));
                    onButtonPressed(podmioty, KLUCZ);
                    Toast.makeText(FragmentDodajPodm.this.getActivity(), "Zapisano osobę", Toast.LENGTH_LONG).show();
                }
            }
        });
        return VIEW;
    }

    private CompoundButton.OnCheckedChangeListener myCheckboxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                czyPrzedsiebiorstwo = true;
            } else czyPrzedsiebiorstwo = false;
        }
    };


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(ArrayList podmioty, String KLUCZ) {
        if (mListener != null) {
            mListener.onFragmentInteraction(podmioty, KLUCZ);
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
        void onFragmentInteraction(ArrayList liczba, String KLUCZ);
    }
}
