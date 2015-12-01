package com.example.isaimrafael.kardexv11;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isaimrafael.kardexv11.web_service.ws_login;

public class Principal extends AppCompatActivity {

    String obControl, obContra;
    private EditText control, contra;
    View.OnClickListener AccederOnClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            obControl = control.getText().toString();
            obContra = contra.getText().toString().toUpperCase();
            online();
            new Descargar().execute("");
        }
    };
    private Button acceder, ubicanos;
    private String res;
    private String passWS = "a8%x*5$d4#1";
    private int regresar = 0;

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logopng_opt);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        control = (EditText) findViewById(R.id.editText2);
        contra = (EditText) findViewById(R.id.editText);
        acceder = (Button) findViewById(R.id.button);
        acceder.setOnClickListener(AccederOnClickLister);
    }

    protected void online() {
        if (!conectadoWifi() & !conectadoDatos()) {
            Toast.makeText(Principal.this, "No hay conexion a internet", Toast.LENGTH_SHORT).show();
        }
    }

    protected Boolean conectadoWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected())
                    return true;
            }
        }
        return false;
    }

    protected Boolean conectadoDatos() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected())
                    return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*control.setText("");
        contra.setText("");
        control.setHint("Numero de control");
        contra.setHint("Contraseña");
        control.requestFocus();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ubicacion) {
            Intent i = new Intent(Principal.this, Mapa.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class Descargar extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            ws_login wss = new ws_login();
            String cont = obControl;
            String pass = MD5(cont + obContra + "%1#5");
            res = wss.Cargador(cont, pass, passWS);
            if (res.equals("true")) {
                Intent i = new Intent(Principal.this, Menues.class);
                Bundle b = new Bundle();
                b.putString("control", cont);
                b.putString("passWS", passWS);
                i.putExtras(b);
                startActivity(i);
            } else {
                regresar = 1;
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (regresar != 0) {
                Toast.makeText(Principal.this, "El numero de control y/o\ncontraseña es incorrecto", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
