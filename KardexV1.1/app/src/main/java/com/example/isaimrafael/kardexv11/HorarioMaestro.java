package com.example.isaimrafael.kardexv11;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.isaimrafael.kardexv11.rellena_materias_horario.Tarea;
import com.example.isaimrafael.kardexv11.rellena_materias_horario.TareaArrayAdapter;
import com.example.isaimrafael.kardexv11.web_service.ws_cursando;
import com.example.isaimrafael.kardexv11.web_service.ws_horario_maestro;

import java.util.ArrayList;
import java.util.List;

public class HorarioMaestro extends AppCompatActivity {

    LocalDB BaseDatos;
    SQLiteDatabase db;
    String control, password;
    ws_cursando cursando;
    ws_horario_maestro maestros;
    List<String> nombreMaestro;
    TareaArrayAdapter<Tarea> adaptador;
    List<String> nombre = new ArrayList<>();
    List<String> dias = new ArrayList<>();
    private ListView lista;
    AdapterView.OnItemClickListener golistaHora = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle b = new Bundle();
            Tarea nombres = (Tarea) lista.getItemAtPosition(position);
            String nombre = nombres.getNombre();
            b.putString("Password", password);
            b.putString("Nombre", nombre);
            Intent i = new Intent(HorarioMaestro.this, HorarioReal_maestro.class);
            i.putExtras(b);
            startActivity(i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horario_maestro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();
        nombreMaestro = new ArrayList<>();
        dias = new ArrayList<>();
        nombreMaestro.clear();
        BaseDatos = new LocalDB(this, "Temporales", null,1);
        db = BaseDatos.getWritableDatabase();
        if (b!= null) {
            control = b.getString("control");
            password = b.getString("passWS");
            db.execSQL("CREATE TABLE IF NOT EXISTS temporal (control TEXT, passw TEXT)");
            db.execSQL("INSERT INTO temporal (control, passw) VALUES ('"+control+"','"+password+"');");
        }else {
            String query = "SELECT * FROM temporal;";
            Cursor cs = db.rawQuery(query, null);
            if (db != null) {
                if (cs.moveToFirst()) {
                    do {
                        control = cs.getString(0);
                        password = cs.getString(1);
                    } while (cs.moveToNext());
                }
                new descargar().execute("");
            }
        }
        lista = (ListView) findViewById(R.id.ListaMaestros);
        lista.setOnItemClickListener(golistaHora);
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
            nombreMaestro.clear();
            for (int i = 0; i < val; i++) {
                nombreMaestro.add(cursando.getCursos().get(i).getGrupos().get(0).getMaestro());
                dias.add(" ");
            }
            List<Tarea> TAREAS = new ArrayList<Tarea>();
            for (int i = 0; i < nombreMaestro.size(); i++)
                TAREAS.add(new Tarea(nombreMaestro.get(i), dias.get(i)));
            adaptador = new TareaArrayAdapter<Tarea>(HorarioMaestro.this, TAREAS);
            lista.setAdapter(adaptador);
            super.onPostExecute(result);
        }
    }

    @Override
    protected void onResume(){
        nombreMaestro = new ArrayList<>();
        dias = new ArrayList<>();
        super.onResume();
    }
}
