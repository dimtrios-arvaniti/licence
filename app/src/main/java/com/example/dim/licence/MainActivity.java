package com.example.dim.licence;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.dim.licence.models.ApplicationModel;
import com.example.dim.licence.vignerons.VigneronActivity;

public class MainActivity extends AppCompatActivity {

    public static final String ARG_DEBUG = "DEBUG";

    private ProgressBar progressBar;
    //private SQLiteDatabase db;
    private ApplicationModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.determinateBar);
        progressBar.setProgress(0);

        model = ApplicationModel.getInstance(getApplicationContext());


/* DUMMY DATA
        Vigneron v;
        Geolocalisation geoloc;

        for (int i =0; i < 5; i++) {
            v = new Vigneron();
            geoloc = new Geolocalisation();
            geoloc.setGeolocVille("PARIS");
            geoloc.setGeolocCode("7500"+i);
            geoloc.setGeolocPays("FRANCE");
            geoloc.setGeolocAdresse(i+" rue de paris");
            v.setVigneronLibelle("Vigneron "+i);
            v.setVigneronDomaine("domaine "+ i);
            v.setVigneronFixe("000000000"+i);
            v.setVigneronGeoloc(geoloc);
            model.getVigneronDAO().insertVigneron(model.getWritableDatabase(), v);
        }
*/

        //db = helper.getWritableDatabase();


/* MAKE DUMMY ITEMS
        for (int i = 0; i < 5; i++) {
            Geolocalisation geo = new Geolocalisation();
            geo.setGeolocPays("FRANCE");
            geo.setGeolocVille("MEULAN");
            int nbr = 24 + i;
            geo.setGeolocAdresse(nbr+" ile belle");
            geo.setGeolocCode("78250");
            daoSession.getGeolocalisationDao().insert(geo);
            Vigneron v = new Vigneron();
            v.setVigneronLibelle("Vigneron test "+i);
            v.setVigneronDomaine("Domaine "+i);
            v.setVigneronGeoloc(geo);
            v.setVigneronFixe("000000000"+i);
            daoSession.getVigneronDao().insert(v);
        }
*/

        LoadAsyncTask iniTask = new LoadAsyncTask();
        iniTask.execute();
    }

        class LoadAsyncTask extends AsyncTask<Void, Integer, Void> {

            Thread t = new Thread() {
                @Override
                public void run() {
                    super.run();
                    progressBar.incrementProgressBy(10);
                }
            };

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.i("DEBUG", "onPostExecute: OnPostExecute");
                Intent intent = new Intent(MainActivity.this, VigneronActivity.class);
                startActivity(intent);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                runOnUiThread(t);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                int progress = 0;

                while (progress < 100) {
                    try {
                        Thread.sleep(200);
                        progress += 10;
                        onProgressUpdate(progress);
                        Log.i("DEBUG", "doInBackground: !"+progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }


}
