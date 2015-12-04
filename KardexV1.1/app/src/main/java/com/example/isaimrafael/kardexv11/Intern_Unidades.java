package com.example.isaimrafael.kardexv11;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.isaimrafael.kardexv11.rellena_materias_horario.Tarea;
import com.example.isaimrafael.kardexv11.rellena_materias_horario.TareaArrayAdapter;
import com.example.isaimrafael.kardexv11.web_service.ws_cursando;

import java.util.ArrayList;
import java.util.List;

public class Intern_Unidades extends AppCompatActivity {

    private ws_cursando cursando;
    TareaArrayAdapter<Tarea> adaptador;
    List<String> tituloUnidad;
    List<String> detalleUnidad;
    List<Integer> calificacion;
    ListView detalle;
    String control, password;
    int val, val2;
    private ProgressDialog pd=null;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intern__unidades);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detalle = (ListView)findViewById(R.id.DetalleUnidades);
        context = this;
        Bundle b = getIntent().getExtras();
        control = b.getString("Control");
        password = b.getString("Password");
        val = b.getInt("Valor");
        val2 = b.getInt("Valor2");
        tituloUnidad = new ArrayList<>();
        detalleUnidad = new ArrayList<>();
        calificacion = new ArrayList<>();
        new descargarWS().execute("");
        pd = ProgressDialog.show(context, "Porfavor espere", "Consultando datos del ITLP", true, false);

    }

    private class descargarWS extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            cursando = new ws_cursando(control, password);
            return 1;
        }

        @Override
        protected void onPostExecute(Object result) {
            pd.dismiss();
            int vals = cursando.getCursos().get(val).getCalificaciones().size();
            int val3 = val2 + 1;
            List<Integer> ponderacion = new ArrayList<>();
            detalleUnidad.clear();
            calificacion.clear();
            for (int i = 0; i < vals; i++) {
                if (Integer.parseInt(cursando.getCursos().get(val).getCalificaciones().get(i).getUnidad()) == val3) {
                    tituloUnidad.add(cursando.getCursos().get(val).getCalificaciones().get(i).getUnidad());
                    detalleUnidad.add(cursando.getCursos().get(val).getCalificaciones().get(i).getCriterio());
                    calificacion.add(cursando.getCursos().get(val).getCalificaciones().get(i).getCalificacion());
                    ponderacion.add(cursando.getCursos().get(val).getCalificaciones().get(i).getPonderacion());
                }
            }
            int prom = 0;
            for (int i = 0; i < calificacion.size(); i++) {
                float mult = ponderacion.get(i) / 100f;
                mult = mult * calificacion.get(i);
                prom += mult;
            }
            detalleUnidad.add("Calificacion de unidad");
            calificacion.add(prom);
            List<Tarea> TAREAS = new ArrayList<Tarea>();
            for (int i = 0; i < detalleUnidad.size(); i++)
                if (detalleUnidad.get(i).equals("Calificacion de unidad")) {
                    if (calificacion.get(i) >= 70)
                        TAREAS.add(new Tarea(detalleUnidad.get(i), String.valueOf(calificacion.get(i))));
                    else
                        TAREAS.add(new Tarea(detalleUnidad.get(i), "NA"));
                } else
                    TAREAS.add(new Tarea(detalleUnidad.get(i), String.valueOf(calificacion.get(i))));
            adaptador = new TareaArrayAdapter<Tarea>(Intern_Unidades.this, TAREAS);
            detalle.setAdapter(adaptador);
            super.onPostExecute(result);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        adaptador.clear();
        Intern_Unidades.this.finish();
        finish();
    }

}
