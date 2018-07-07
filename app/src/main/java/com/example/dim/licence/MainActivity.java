package com.example.dim.licence;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dim.licence.entities.AppUser;
import com.example.dim.licence.models.MasterModel;
import com.example.dim.licence.utils.uicustoms.LoginPanel;

import org.jasypt.util.password.StrongPasswordEncryptor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.utils.commons.Commons.MAIL_REGEX;

/**
 * IMPORT VILLES (FRANCE) from opendata.org
 */
public class MainActivity extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_NET_STATE_TAG = 8;
    /**
     * DEBUG TAG
     */
    public static final String ARG_DEBUG = "DEBUG";

    /**
     * Loading screen progress bar
     * shown on login/ creation success
     */
    private ProgressBar progressBar;
    /**
     * Basic model to access DAO's
     * Abstract to enable user-level data-access control
     */
    private MasterModel model;
    /**
     * List of login page vertical tabs
     */
    private List<LoginPanel> panels;
    /**
     * Container for login page vertical tabs
     */
    private ScrollView loginPanel;
    /**
     * create account mail
     */
    private EditText newMail;
    /**
     * create account mail confirmation
     */
    private EditText newMailConf;
    /**
     * create account password
     */
    private EditText newPwd;
    /**
     * create account password confirmation
     */
    private EditText newPwdConf;
    private Button loginButton;
    private Button loginCreateButton;
    private TextView forgotPwd;
    private EditText mail;
    private EditText pwd;

    private int resDisplayedImage;
    private int resFoldedImage;

    //
    private View requestPermissionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginPanel = findViewById(R.id.login_panel);
        progressBar = findViewById(R.id.determinateBar);
        newMail = findViewById(R.id.new_email);
        newMailConf = findViewById(R.id.confirm_new_email);
        newPwd = findViewById(R.id.new_pwd);
        newPwdConf = findViewById(R.id.confirm_new_pwd);
        loginButton = findViewById(R.id.login_button);
        loginCreateButton = findViewById(R.id.login_create_button);
        forgotPwd = findViewById(R.id.login_forgot_password);
        mail = findViewById(R.id.email);
        pwd = findViewById(R.id.password);


        // init progress bar state
        progressBar.setVisibility(View.GONE);
        progressBar.setProgress(0);

        model = MasterModel.getInstance(getApplicationContext());
        // init view
        initLoginCards();


        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ARG_DEBUG, "onClick: LOGIN ! ");
/*
                if (isInvalidLoginInput()) {
                    return;
                }
  */              // change view to show loading screen while sync ..
                prepareView();

                // Launch vigneron task
                LoadAsyncTask iniTask = new LoadAsyncTask();
                iniTask.execute();

            }
        });


        loginCreateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
              if (isInvalidCreateLoginInput()) {
                    return;
                }

                // Launch vigneron task
                LoadAsyncTask iniTask = new LoadAsyncTask();
                iniTask.execute();
            }
        });


        forgotPwd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {
                    // DO ONLINE STUFF
                    // ForgotPasswordAsyncTask createAccountAsyncTask = new ForgotPasswordAsyncTask();
                    // ForgotPasswordAsyncTask.execute(newUser.getAppUserPwd());
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Impossible d'effectuer cette opération sans une connexion réseau ! "
                                    + "\nVeuillez vérifier que vous disposez bien d'une connexion ! ",
                            Toast.LENGTH_LONG).show();
                }
            }
        });





    }


    /**
     * inserts user on local database.
     * AppUser is returned by a server query
     */
    private void createLocalUser(/*AppUser appUser*/) {
        AppUser appUser = new AppUser();
        appUser.setAppUserMail(mail.getText().toString());
        appUser.setAppUserMaster(true);
        // password encryption
        StrongPasswordEncryptor strongPasswordEncryptor = new StrongPasswordEncryptor();
        String epwd = strongPasswordEncryptor.encryptPassword(pwd.getText().toString());
        // apiKey encryption
        String apiKey = mail.getText().toString() + pwd.getText().toString();
        String eapiKey = strongPasswordEncryptor.encryptPassword(apiKey);

        appUser.setAppUserPwd(epwd);
        appUser.setAppUserApiKey(eapiKey);

        model.createAccount(appUser);
    }

    private void prepareView() {
        loginPanel.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        newMail.setVisibility(View.GONE);
        newMailConf.setVisibility(View.GONE);
        newPwd.setVisibility(View.GONE);
        newPwdConf.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);
        loginCreateButton.setVisibility(View.GONE);
        forgotPwd.setVisibility(View.GONE);
        mail.setVisibility(View.GONE);
        pwd.setVisibility(View.GONE);

        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Converts the contents of an InputStream to a String.
     */
    public static String readStream(InputStream stream)
            throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        StringBuilder sb = null;
        if (reader.ready()) {
            do {
                sb.append((char) reader.read());
            } while (reader.ready());
        }

        return sb.toString();
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        Log.d(ARG_DEBUG, "Wifi connected: " + isWifiConn);
        Log.d(ARG_DEBUG, "Mobile connected: " + isMobileConn);
        return (isWifiConn || isMobileConn);
    }

    private boolean isInvalidCreateLoginInput() {
        if (newMail.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Champs requis !", Toast.LENGTH_SHORT).show();
            newMail.requestFocus();
            return true;
        }
        if (newMailConf.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Champs requis !", Toast.LENGTH_SHORT).show();
            newMailConf.requestFocus();
            return true;
        }
        if (newPwd.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Champs requis !", Toast.LENGTH_SHORT).show();
            newPwd.requestFocus();
            return true;
        }
        if (newPwdConf.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Champs requis !", Toast.LENGTH_SHORT).show();
            newPwdConf.requestFocus();
            return true;
        }

         if (!newMail.getText().toString().matches(MAIL_REGEX)) {
            Toast.makeText(MainActivity.this, "Champs invalide !", Toast.LENGTH_SHORT).show();
            newMail.requestFocus();
            return true;
        }
        if (!newMailConf.getText().toString().matches(MAIL_REGEX)) {
            Toast.makeText(MainActivity.this, "Champs invalide !", Toast.LENGTH_SHORT).show();
            newMailConf.requestFocus();
            return true;
        }
        if (!newMail.equals(newMailConf)) {
            Toast.makeText(MainActivity.this, "Les adresses mails saisies ne sont pas identiques !", Toast.LENGTH_SHORT).show();
            newMail.requestFocus();
            return false;
        }
        if (!newPwd.equals(newMailConf)) {
            Toast.makeText(MainActivity.this, "Les mots de passe saisis ne sont pas identiques !", Toast.LENGTH_SHORT).show();
            newPwd.requestFocus();
            return false;
        }
        return false;
    }

    private boolean isInvalidLoginInput() {
        if (mail.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Champs requis !", Toast.LENGTH_SHORT).show();
            mail.requestFocus();
            return true;
        }
        if (pwd.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Champs requis !", Toast.LENGTH_SHORT).show();
            pwd.requestFocus();
            return true;
        }

        if (!mail.getText().toString().matches(MAIL_REGEX)) {
            Toast.makeText(MainActivity.this, "Champs invalide !", Toast.LENGTH_SHORT).show();
            mail.requestFocus();
            return true;
        }

        return false;
    }

    private void initLoginCards() {

        resDisplayedImage = R.drawable.ic_card_display;
        resFoldedImage = R.drawable.ic_card_fold;
        panels = new ArrayList<>();

        findViewById(R.id.login_content).setVisibility(View.VISIBLE);
        findViewById(R.id.login_header_image).setBackgroundResource(resFoldedImage);
        findViewById(R.id.login_create_content).setVisibility(View.GONE);
        findViewById(R.id.login_create_header_image).setBackgroundResource(resDisplayedImage);

        findViewById(R.id.login_header).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCardDisplay(R.id.login_header);
            }
        });

        findViewById(R.id.login_create_header).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCardDisplay(R.id.login_create_header);
            }
        });
    }

    private void handleCardDisplay(int login_create_header) {
        int index = findCardIndexFromHeader(login_create_header);

        if (index != -1 && index < panels.size()) {
            boolean state = panels.get(index).isCardDisplayed();
            state = !state;

            findViewById(panels.get(index).getResContent())
                    .setVisibility(state ? View.VISIBLE : View.GONE);

            findViewById(panels.get(index).getResHeaderImage())
                    .setBackgroundResource(state ? resFoldedImage : resDisplayedImage);
            panels.get(index).setCardDisplayed(state);
        }
    }

    private int findCardIndexFromHeader(int header) {
        int index = -1;
        for (LoginPanel card :
                panels) {
            if (card.getResHeader() == header) {
                return card.getCardId();
            }
        }
        return -1;
    }

    private void initDumyData() {
       // insert dic

        /* DUMMY DATA
        Vigneron v;
        Geolocalisation geoloc;

        for (int i = 0; i < 9; i++) {
            v = new Vigneron();
            geoloc = new Geolocalisation();
            geoloc.setGeolocVille("PARIS");
            geoloc.setGeolocCode("7500" + i);
            geoloc.setGeolocPays("FRANCE");
            geoloc.setGeolocAdresse(i + " rue de paris");
            long gid = model.insertGeolocalisation(model.getWritableDatabase(), geoloc);
            v.setVigneronLibelle("Vigneron " + i);
            v.setVigneronDomaine("domaine " + i);
            v.setVigneronFixe("000000000" + i);
            v.setVigneronGeoloc(geoloc);
            model.getVigneronDAO().insertVigneron(model.getWritableDatabase(), v, Integer.valueOf(String.valueOf(gid)));
        }
 */
    }

    class LoadAsyncTask extends AsyncTask<Void, Integer, Void> {

        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                progressBar.incrementProgressBy(20);
            }
        };

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(MainActivity.this, CaveActivity.class);
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
                    progress += 20;
                    onProgressUpdate(progress);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }


    static class CreateAccountAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            try {
                URL url = new URL("http://192.168.42.195:64902/api/login/createaccount?pwd=titi");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Allow", "GET");
                urlConnection.setRequestProperty("Content-Type", "text/plain");
                urlConnection.setRequestProperty("Charset", "utf-8");
                urlConnection.setDoInput(true); // RESPONSE HAS BODY !
                //urlConnection.setChunkedStreamingMode(0); // IMPORTANT unknown string length !

                // urlConnection.connect();
                InputStream stream = urlConnection.getInputStream();

                Log.i(ARG_DEBUG, "doInBackground: HTTP response code: " + urlConnection.getResponseCode());
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    // Converts Stream to String with max length of 1000.
                    result = readStream(stream);
                    Log.i(ARG_DEBUG, "doInBackground: RESPONSE=" + result);

                } else {

                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    urlConnection.disconnect();
                    throw new IOException("HTTP : " + urlConnection.getResponseMessage());

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
    }

}
