package com.example.isaimrafael.kardexv11;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.isaimrafael.kardexv11.rellena_materias_horario.Tarea;
import com.example.isaimrafael.kardexv11.rellena_materias_horario.TareaArrayAdapter;
import com.example.isaimrafael.kardexv11.web_service.ws_cursando;

import java.util.ArrayList;
import java.util.List;

public class Unidades extends AppCompatActivity {

    TextView nombre;
    int val;
    ListView calis, unities;
    TareaArrayAdapter<Tarea> adaptador;
    ws_cursando cursando;
    String control, password;
    List<String> tituloUnidad = new ArrayList<>();
    List<String> detalleUnidad = new ArrayList<>();
    List<Integer> calificacion = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unidades);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nombre = (TextView) findViewById(R.id.recibeNombre);
        Bundle b = getIntent().getExtras();
        nombre.setText(b.getString("nombre"));
        val = b.getInt("valor");
        control = b.getString("control");
        password = b.getString("contras");
        unities = (ListView) findViewById(R.id.ListaUnidades);
        new descargarWS().execute("");
    }

    //region menuChat
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_unidades, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.chat) {
            Intent intent = new Intent(Unidades.this, Foro.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class descargarWS extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            cursando = new ws_cursando(control, password);
            return 1;
        }

        @Override
        protected void onPostExecute(Object result) {
            int vals = cursando.getCursos().get(val).getCalificaciones().size();
            for (int i = 0; i < vals; i++) {
                tituloUnidad.add(cursando.getCursos().get(val).getCalificaciones().get(i).getUnidad());
                detalleUnidad.add(cursando.getCursos().get(val).getCalificaciones().get(i).getCriterio());
                calificacion.add(cursando.getCursos().get(val).getCalificaciones().get(i).getCalificacion());
            }
            List<Tarea> TAREAS = new ArrayList<Tarea>();
            for (int i = 0; i < vals; i++)
                TAREAS.add(new Tarea("Unidad" + tituloUnidad.get(i), detalleUnidad.get(i) + "\n" + calificacion.get(i)));
            adaptador = new TareaArrayAdapter<Tarea>(Unidades.this, TAREAS);
            unities.setAdapter(adaptador);
            super.onPostExecute(result);
        }
    }
    //endregion
}
