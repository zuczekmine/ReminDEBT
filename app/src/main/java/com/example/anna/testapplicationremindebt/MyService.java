package com.example.anna.testapplicationremindebt;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import org.joda.time.DateTime;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MyService extends Service {

    private static final String TAG = "ServiceExample";
    private ArrayList<Naleznosc> naleznosci;
    private ArrayList<Zobowiazanie> zobowiazania;
    private ArrayList<String> waluty = new ArrayList<>();
    private String nazwaPlikuNal = "naleznosci";
    private String nazwaPlikuZob = "zobowiazania";
    private String myFormat = "dd/MM/yy";
    private String nazwaPlikuWaluty = "waluty";
    private String plik = "plik.xml";
    private String tytul, wiadomosc;
    private SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
    private DateTime dzisiaj, jutro, data;
    public static final int NOTIFICATION_ID = 234;
    private String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22PLNEUR%22%2C%20%22EURPLN%22%2C%20%22PLNUSD%22%2C%20%22USDPLN%22%2C%20%22EURUSD%22%2C%20%22USDEUR%22)&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    private final String KEY_RESULTS = "rate";
    private final String KEY_NAME = "Name";
    private final String KEY_RATE = "Rate";
    private File file;


    @Override
    public void onCreate() {
        waluty.add("PLN");
        waluty.add("USD");
        waluty.add("EUR");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service onStartCommand " + startId);
        final int currentId = startId;


        Runnable r = new Runnable() {
            public void run() {

                while (true) {
                    try {
                        FileInputStream fis;
                        dzisiaj = new DateTime();
                        jutro = dzisiaj.plusDays(1);

                        try {
                            fis = openFileInput(nazwaPlikuNal);
                            ObjectInputStream ois = new ObjectInputStream(fis);
                            naleznosci = (ArrayList<Naleznosc>) ois.readObject();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            fis = openFileInput(nazwaPlikuZob);
                            ObjectInputStream ois = new ObjectInputStream(fis);
                            zobowiazania = (ArrayList<Zobowiazanie>) ois.readObject();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(!naleznosci.isEmpty()) {
                            for(Naleznosc naleznosc : naleznosci) {
                                data = new DateTime(naleznosc.getData());
                                if((jutro.toLocalDate()).equals(data.toLocalDate())) {
                                    tytul = "ReminDEBT - przypomnienie";
                                    wiadomosc = "Nadchodząca należność: "+naleznosc.getNazwa();
                                    stworzPowiadomienie(tytul, wiadomosc);
                                }
                            }
                        }
                        if(!zobowiazania.isEmpty()) {
                            for(Zobowiazanie zobowiazanie : zobowiazania) {
                                data = new DateTime(zobowiazanie.getData());
                                if((jutro.toLocalDate()).equals(data.toLocalDate())) {
                                    tytul = "ReminDEBT - przypomnienie";
                                    wiadomosc = "Nadchodzące zobowiązanie: "+zobowiazanie.getNazwa();
                                    stworzPowiadomienie(tytul, wiadomosc);
                                }
                            }
                        }

                        new WalutyPobrane().execute(url);
                        //Thread.sleep(1000*60*2);
                        Thread.sleep(1000*60*60*24);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t = new Thread(r);
        t.start();
        return Service.START_STICKY;
    }

    public void stworzPowiadomienie(String tytul, String wiadomosc){

        Intent intent = new Intent(this, MainActivity.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(tytul)
                .setContentText(wiadomosc)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Service onDestroy");
    }

    private class WalutyPobrane extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... urls) {
            String plikXML = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
                String s = "";
                Log.i("Currencies", getFilesDir().toString());
                File path = new File(getFilesDir().toString()+"/plikiXML/");
                path.mkdirs();
                file = new File(path,plik);
                FileWriter fw = new FileWriter(file,true);
                while ((s = buffer.readLine())!=null) {
                    plikXML += s;
                    fw.write(s);
                }
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return plikXML;
        }

        @Override
        protected void onPostExecute(String plikXML) {
            XMLParser parser = new XMLParser();
            Document doc = parser.getDOMElement(plikXML);

            ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
            ArrayList<String> nazwy = new ArrayList<>();
            ArrayList<String> stawki = new ArrayList<>();

            NodeList nl = doc.getElementsByTagName(KEY_RESULTS);
            for (int i=0; i<nl.getLength(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                Element e =(Element)nl.item(i);
                nazwy.add(parser.getValue(e, KEY_NAME));
                stawki.add(parser.getValue(e, KEY_RATE));
                map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
                map.put(KEY_RATE, parser.getValue(e, KEY_RATE));

                menuItems.add(map);
            }
            try {
                FileOutputStream fos;
                fos = openFileOutput(nazwaPlikuWaluty, Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(stawki);
                oos.close();
                fos.close();
            } catch(Exception ex) {
                Log.i("Currencies", "You made a HUGE MISTAKE");
            }
            //Log.i("Currencies", String.valueOf(nazwy)+"   "+String.valueOf(stawki));

        }
    }
}