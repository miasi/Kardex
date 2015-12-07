package com.example.isaimrafael.kardexv11;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.isaimrafael.kardexv11.rellena_materias_horario.Tarea;
import com.example.isaimrafael.kardexv11.rellena_materias_horario.TareaArrayAdapter;
import com.example.isaimrafael.kardexv11.web_service.ws_cursando;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Cursando extends AppCompatActivity {

    LocalDB BaseDatos;
    SQLiteDatabase db;
    private String contr, pass;
    private ListView lista;
    TareaArrayAdapter<Tarea> adaptador;
    ws_cursando cursando;
    List<String> nombre = new ArrayList<>();
    private Context context;
    private ProgressDialog pd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cursando);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        lista = (ListView)findViewById(R.id.listaCursando);
        lista.setOnItemClickListener(listaCursos);
        SalvaLogin();
    }

    private void SalvaLogin(){
        BaseDatos = new LocalDB(this, "Temporales", null,1);
        db = BaseDatos.getWritableDatabase();
        Bundle b = getIntent().getExtras();
        if (b!= null) {
            contr = b.getString("control");
            pass = b.getString("passWS");
            db.execSQL("DROP TABLE IF EXISTS temporalDos;");
        }else{
            String query = "SELECT * FROM temporalDos;";
            Cursor cs = db.rawQuery(query,null);
            if (db!= null){
                if (cs.moveToFirst()){
                    do{
                        contr = cs.getString(0);
                        pass = cs.getString(1);
                    }while (cs.moveToNext());
                }
            }
        }
        new descargar().execute("");
        pd = ProgressDialog.show(context, "Porfavor espere", "Consultando datos del ITLP", true, false);
    }

    private class descargar extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            try {
                cursando = new ws_cursando(contr, pass);
            }catch (Exception e){
                e.printStackTrace();
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Object result){
            try {
                pd.dismiss();
                int val = cursando.getCursos().size();
                for (int i = 0; i < val; i++) {
                    nombre.add(cursando.getCursos().get(i).getMateria());
                }
                List<Tarea> TAREAS = new ArrayList<Tarea>();
                for (int i=0; i < nombre.size(); i++)
                    TAREAS.add(new Tarea(nombre.get(i), ""));
                adaptador = new TareaArrayAdapter<Tarea>(Cursando.this, TAREAS);
                lista.setAdapter(adaptador);
                super.onPostExecute(result);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    AdapterView.OnItemClickListener listaCursos = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS temporalDos (control TEXT, passw TEXT)");
                db.execSQL("INSERT INTO temporalDos (control, passw) VALUES ('"+contr+"','"+pass+"');");
                Tarea num = (Tarea)lista.getItemAtPosition(position);
                int val = position;
                String nombre = num.getNombre();
                Intent i = new Intent(Cursando.this, Unidades.class);
                Bundle b = new Bundle();
                b.putString("nombre", nombre);
                b.putInt("valor", val);
                b.putString("contras", pass);
                b.putString("control", contr);
                i.putExtras(b);
                startActivity(i);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
}
