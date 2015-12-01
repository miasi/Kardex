package com.example.isaimrafael.kardexv11;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.isaimrafael.kardexv11.web_service.ws_foro;
import com.example.isaimrafael.kardexv11.web_service.ws_foro_mensaje;

import java.util.List;

public class Foro extends AppCompatActivity {

    private ws_foro foro = new ws_foro();
    private List<ws_foro_mensaje> mensajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new descargar().execute("");
    }

    private class descargar extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            mensajes = foro.consultarMensaje("ISIC-2010-224", "2015", "Agosto-Diciembre", "AUDITORIA INFORMATICA", "K", "a8%x*5$d4#1");
            String s = foro.crearMensaje("ISIC-2010-224", "2015", "Agosto-Diciembre", "AUDITORIA INFORMATICA", "K", "11310222", "test", "a8%x*5$d4#1");
            return 1;
        }
    }
}
