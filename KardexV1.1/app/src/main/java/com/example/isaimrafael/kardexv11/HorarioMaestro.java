package com.example.isaimrafael.kardexv11;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.isaimrafael.kardexv11.web_service.ws_cursando;
import com.example.isaimrafael.kardexv11.web_service.ws_horario_maestro;

import java.util.ArrayList;
import java.util.List;

public class HorarioMaestro extends AppCompatActivity {

    String control, password;
    ws_cursando cursando;
    ws_horario_maestro maestros;
    List<String> nombreMaestro = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horario_maestro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();
        control = b.getString("control");
        password = b.getString("passWS");
        new descargar().execute("");
    }

    private class descargar extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            cursando = new ws_cursando(control, password);
            return 1;
        }

        @Override
        protected void onPostExecute(Object result) {
            int val = cursando.getCursos().size();
            for (int i = 0; i < val; i++) {
                nombreMaestro.add(cursando.getCursos().get(i).getGrupos().get(0).getMaestro());
            }
            new descargarWSMaestro().execute("");
            super.onPostExecute(result);
        }
    }

    private class descargarWSMaestro extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            maestros = new ws_horario_maestro(nombreMaestro.get(0), password);
            return 1;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
        }
    }
}
