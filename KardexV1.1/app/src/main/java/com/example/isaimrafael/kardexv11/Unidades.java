package com.example.isaimrafael.kardexv11;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.isaimrafael.kardexv11.rellena_materias_horario.Tarea;
import com.example.isaimrafael.kardexv11.rellena_materias_horario.TareaArrayAdapter;
import com.example.isaimrafael.kardexv11.web_service.ws_cursando;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Unidades extends AppCompatActivity {

    LocalDB BaseDatos;
    SQLiteDatabase db;
    TextView nombre;
    int val;
    ListView unities;
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
        BaseDatos = new LocalDB(this, "Temporales", null,1);
        db = BaseDatos.getWritableDatabase();
        nombre = (TextView) findViewById(R.id.recibeNombre);
        Bundle b = getIntent().getExtras();
        if (b!= null) {
            nombre.setText(b.getString("nombre"));
            val = b.getInt("valor");
            control = b.getString("control");
            password = b.getString("contras");
        }
        else {
            String query = "SELECT * FROM temporal;";
            Cursor cs = db.rawQuery(query, null);
            if (db != null) {
                if (cs.moveToFirst()) {
                    do {
                        control = cs.getString(0);
                        password = cs.getString(1);
                    } while (cs.moveToNext());
                }
                db.execSQL("DROP TABLE IF EXISTS temporal;");
            }
        }
        unities = (ListView) findViewById(R.id.ListaUnidades);
        unities.setOnItemClickListener(abrirdetalle);
        new descargarWS().execute("");
    }

    AdapterView.OnItemClickListener abrirdetalle = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle b = new Bundle();
            db.execSQL("CREATE TABLE IF NOT EXISTS temporal (control TEXT, passw TEXT)");
            db.execSQL("INSERT INTO temporal (control, passw) VALUES ('"+control+"','"+password+"');");
            b.putString("Control", control);
            b.putString("Password", password);
            b.putInt("Valor",val);
            b.putInt("Valor2", position);
            Intent i = new Intent(Unidades.this, Intern_Unidades.class);
            i.putExtras(b);
            startActivity(i);
        }
    };


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
            }
            HashSet hs = new HashSet();
            hs.addAll(tituloUnidad);
            tituloUnidad.clear();
            tituloUnidad.addAll(hs);
            List<Integer> auxi = new ArrayList<>();
            for (int i=0; i < tituloUnidad.size(); i++){
                auxi.add(Integer.parseInt(tituloUnidad.get(i)));
            }
            Collections.sort(auxi);
            for (int bor=0; bor < tituloUnidad.size(); bor++)
                detalleUnidad.add(" ");
            List<Tarea> TAREAS = new ArrayList<Tarea>();
            for (int i = 0; i < tituloUnidad.size(); i++)
                TAREAS.add(new Tarea("Unidad " + auxi.get(i), detalleUnidad.get(i)));
            adaptador = new TareaArrayAdapter<Tarea>(Unidades.this, TAREAS);
            unities.setAdapter(adaptador);
            super.onPostExecute(result);
        }
    }
    //endregion
}
