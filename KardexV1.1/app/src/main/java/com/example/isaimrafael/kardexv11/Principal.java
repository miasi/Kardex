package com.example.isaimrafael.kardexv11;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.isaimrafael.kardexv11.web_service.ws_info_alumno;
import com.example.isaimrafael.kardexv11.web_service.ws_login;

public class Principal extends AppCompatActivity {

    private EditText control, contra;
    private Button acceder, ubicanos;
    private String res;
    private String passWS = "a8%x*5$d4#1";
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
        ubicanos = (Button) findViewById(R.id.button2);
        acceder.setOnClickListener(AccederOnClickLister);
        ubicanos.setOnClickListener(AbrirMapaUbicacion);


    }

    View.OnClickListener AbrirMapaUbicacion = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Principal.this, Mapa.class);
            startActivity(i);
        }
    };

    View.OnClickListener AccederOnClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new Descargar().execute("");
        }
    };

    private class Descargar extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
                ws_login wss = new ws_login();
                String cont = control.getText().toString();
                String pass = MD5(cont + contra.getText().toString().toUpperCase() + "%1#5");
                res = wss.Cargador(cont, pass, passWS);
                if (res.equals("true")) {
                    Intent i = new Intent(Principal.this, Menues.class);
                    Bundle b = new Bundle();
                    b.putString("control", cont);
                    b.putString("passWS", passWS);
                    i.putExtras(b);
                    startActivity(i);
                }
            return 1;
        }
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
