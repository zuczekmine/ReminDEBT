package com.example.anna.testapplicationremindebt;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tunyk.currencyconverter.api.CurrencyConverter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentDodajPodm.OnFragmentInteractionListener,
        FragmentNaleznosci.OnFragmentInteractionListener {
    private ArrayList<Podmiot> podmiotyMain;
    private ArrayList<Podmiot> podmiotyPrzekazane;
    private ArrayList<Naleznosc> naleznosciMain;
    private ArrayList<Naleznosc> naleznosciPrzekazane;
    private ArrayList<Zobowiazanie> zobowiazaniaMain;
    private ArrayList<Zobowiazanie> zobowiazaniaPrzekazane;
    private String nazwaPlikuPodm = "podmioty";
    private String nazwaPlikuNal = "naleznosci";
    private String nazwaPlikuZob = "zobowiazania";
    private String klucz;
    private Date data;
    private CurrencyConverter currencyConverter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FileInputStream fis;
        try {
            fis = openFileInput(nazwaPlikuPodm);
            ObjectInputStream ois = new ObjectInputStream(fis);
            podmiotyMain =(ArrayList<Podmiot>) ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dodajPodmiot) {
            FragmentDodajPodm fragmentDodajPodm = new FragmentDodajPodm();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.layoutMain, fragmentDodajPodm).commit();

        } else if (id == R.id.nav_naleznosci) {
            FragmentNaleznosci fragmentNaleznosci = new FragmentNaleznosci();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.layoutMain, fragmentNaleznosci).commit();

        } else if (id == R.id.nav_zobowiazania) {
            FragmentZobowiazania fragmentZobowiazania = new FragmentZobowiazania();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.layoutMain, fragmentZobowiazania).commit();
        } else if (id == R.id.nav_statystyka) {

        } else if (id == R.id.nav_ustawienia) {

        } else if (id == R.id.nav_tworcy) {
            FragmentTworcy fragmentTworcy = new FragmentTworcy();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.layoutMain, fragmentTworcy).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(ArrayList obiekty, String klucz) {
        this.klucz = klucz;
        switch (klucz) {
            case "podmioty":
                this.podmiotyPrzekazane = obiekty;
                for(Podmiot podmiot : podmiotyPrzekazane) {
                    podmiotyMain.add(podmiot);
                }
                try {
                    FileOutputStream fos;
                    fos = openFileOutput(nazwaPlikuPodm, Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(podmiotyMain);
                    oos.close();
                    fos.close();
                    Toast.makeText(this, "Zapisano podmiot", Toast.LENGTH_SHORT).show();
                } catch(Exception ex) {
                    Toast.makeText(this, "Błąd", Toast.LENGTH_SHORT).show();
                }
                break;
            case "naleznosci":
                this.naleznosciPrzekazane = obiekty;
                for(Naleznosc naleznosc : naleznosciPrzekazane) {
                    naleznosciMain.add(naleznosc);
                }
                try {
                    FileOutputStream fos;
                    fos = openFileOutput(nazwaPlikuNal, Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(naleznosciMain);
                    oos.close();
                    fos.close();
                    Toast.makeText(this, "Zapisano należność", Toast.LENGTH_SHORT).show();
                } catch(Exception ex) {
                    Toast.makeText(this, "Błąd", Toast.LENGTH_SHORT).show();
                }
                break;
            case "zobowiazania":
                this.zobowiazaniaPrzekazane = obiekty;
                for(Zobowiazanie zobowiazanie : zobowiazaniaPrzekazane) {
                    zobowiazaniaMain.add(zobowiazanie);
                }
                try {
                    FileOutputStream fos;
                    fos = openFileOutput(nazwaPlikuZob, Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(zobowiazaniaMain);
                    oos.close();
                    fos.close();
                    Toast.makeText(this, "Zapisano podmiot", Toast.LENGTH_SHORT).show();
                } catch(Exception ex) {
                    Toast.makeText(this, "Błąd", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
