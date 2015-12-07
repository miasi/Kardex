package com.example.isaimrafael.kardexv11;

import android.app.ProgressDialog;
import android.content.Context;
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
    String control, password,palnombre;
    List<String> tituloUnidad;
    List<String> detalleUnidad;
    List<String> EnviarForo;
    private Context context;
    private ProgressDialog pd=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.unidades);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EnviarForo = new ArrayList<>();
        detalleUnidad = new ArrayList<>();
        tituloUnidad = new ArrayList<>();
        BaseDatos = new LocalDB(this, "Temporales", null,1);
        db = BaseDatos.getWritableDatabase();
        nombre = (TextView) findViewById(R.id.recibeNombre);
        unities = (ListView) findViewById(R.id.ListaUnidades);
        unities.setOnItemClickListener(abrirdetalle);
        revision();

        new descargarWS().execute("");
        pd = ProgressDialog.show(context, "Porfavor espere", "Consultando datos del ITLP", true, false);
    }

    private void revision(){
        Bundle b = getIntent().getExtras();
        if (b!= null) {
            nombre.setText(b.getString("nombre"));
            val = b.getInt("valor");
            control = b.getString("control");
            password = b.getString("contras");
            db.execSQL("DROP TABLE IF EXISTS temporal;");
            db.execSQL("DROP TABLE IF EXISTS temporal_dos;");
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
            }
            query = "SELECT * FROM temporal_dos;";
            cs = db.rawQuery(query,null);
            if (db != null){
                if (cs.moveToFirst()){
                    do{
                        palnombre = cs.getString(0);
                        val = Integer.parseInt(cs.getString(1));
                    }while (cs.moveToNext());
                    nombre.setText(palnombre);
                }
            }

        }
    }

    AdapterView.OnItemClickListener abrirdetalle = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle b = new Bundle();
            db.execSQL("CREATE TABLE IF NOT EXISTS temporal (control TEXT, passw TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS temporal_dos (nombre TEXT, valor TEXT)");
            db.execSQL("INSERT INTO temporal (control, passw) VALUES ('"+control+"','"+password+"');");
            db.execSQL("INSERT INTO temporal_dos (nombre, valor) VALUES ('"+nombre.getText()+"','"+ String.valueOf(val)+"');");
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
            String plan = EnviarForo.get(0);
            String año = EnviarForo.get(1);
            String periodo = EnviarForo.get(2);
            String materia = EnviarForo.get(3);
            String grupo = EnviarForo.get(4);
            String alumno = EnviarForo.get(5);
            Bundle b = new Bundle();
            b.putString("Plan", plan);
            b.putString("Año", año);
            b.putString("Periodo", periodo);
            b.putString("Materia", materia);
            b.putString("Grupo", grupo);
            b.putString("Alumno", alumno);
            intent.putExtras(b);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class descargarWS extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            cursando = new ws_cursando(control,password);
            return 1;
        }

        @Override
        protected void onPostExecute(Object result) {
            pd.dismiss();
            EnviarForo.add(cursando.getCursos().get(val).getGrupos().get(0).getPlan());
            EnviarForo.add(String.valueOf(cursando.getCursos().get(val).getAnio()));
            EnviarForo.add(cursando.getCursos().get(val).getPeriodo());
            EnviarForo.add(cursando.getCursos().get(val).getMateria());
            EnviarForo.add(cursando.getCursos().get(val).getGrupo());
            EnviarForo.add(cursando.getCursos().get(val).getAlumno());
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
            List<String> finalfinal = new ArrayList<>();
            for (int qw=0; qw < auxi.size();qw++){
                finalfinal.add("Unidad "+auxi.get(qw));
            }
            detalleUnidad = promedio_general(val);
            finalfinal.add("Calificación final");
            List<Tarea> TAREAS = new ArrayList<Tarea>();
            for (int i = 0; i < finalfinal.size(); i++)
                TAREAS.add(new Tarea(finalfinal.get(i), detalleUnidad.get(i)));
            adaptador = new TareaArrayAdapter<Tarea>(Unidades.this, TAREAS);
            unities.setAdapter(adaptador);
            super.onPostExecute(result);
        }
    }

    private List<String> promedio_general(int valores) {
        List<Integer> promedio = new ArrayList<>();
        List<String> valoresProm = new ArrayList<>();
        int val3 = 1;
        int i = 0;
        List<Integer> ponderacion = new ArrayList<>();
        detalleUnidad.clear();
        List<Integer> calificacion = new ArrayList<>();
        int hasta = cursando.getCursos().get(valores).getCalificaciones().size();
        for (i = 0; i < hasta; i++) {
            if (Integer.parseInt(cursando.getCursos().get(valores).getCalificaciones().get(i).getUnidad()) == val3) {
                calificacion.add(cursando.getCursos().get(valores).getCalificaciones().get(i).getCalificacion());
                ponderacion.add(cursando.getCursos().get(valores).getCalificaciones().get(i).getPonderacion());
            } else {
                int prom = 0;
                for (int ii = 0; ii < calificacion.size(); ii++) {
                    float mult = ponderacion.get(ii) / 100f;
                    mult = mult * calificacion.get(ii);
                    prom += mult;
                }
                promedio.add(prom);
                calificacion.clear();
                ponderacion.clear();
                if (prom > 70)
                valoresProm.add("Calificación: "+String.valueOf(prom));
                else
                valoresProm.add("NA");
                val3++;
                i--;
            }
        }
        if (hasta == i) {
            int prom = 0;
            for (int ii = 0; ii < calificacion.size(); ii++) {
                float mult = ponderacion.get(ii) / 100f;
                mult = mult * calificacion.get(ii);
                prom += mult;
            }
            if (prom > 70)
            valoresProm.add("Calificación: "+String.valueOf(prom));
            else
            valoresProm.add("NA");
            promedio.add(prom);
            calificacion.clear();
            ponderacion.clear();
        }
        int vals = 0;
        for (int gh = 0; gh < promedio.size(); gh++) {
            vals += promedio.get(gh);
        }
        int vals2 = vals / promedio.size();
        if (vals2 > 70)
            valoresProm.add("Calificación: "+String.valueOf(vals2));
        else
            valoresProm.add(String.valueOf("NA"));
        return valoresProm;
    }
    //endregion
}
