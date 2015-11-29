package com.example.isaimrafael.kardexv11;

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
import java.util.List;

public class Cursando extends AppCompatActivity {

    LocalDB BaseDatos;
    SQLiteDatabase db;
    private String res;
    private String contr, pass;
    private ListView lista;
    TareaArrayAdapter<Tarea> adaptador;
    String[] nombre = new String[]{"Dispositivos moviles","Aplicaciones Distribuidas",
            "Sistemas programables", "Auditoria","Graficacion"};
    String[] dias = new String[]{"LUN MAR MIE", "LUN JUE VIE", "LUN, MAR, JUE", "MIE JUE VIE","LUN, JUE VIE"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cursando);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lista = (ListView)findViewById(R.id.listaCursando);
        List<Tarea> TAREAS = new ArrayList<Tarea>();
        for (int i=0; i < nombre.length; i++)
            TAREAS.add(new Tarea(nombre[i], dias[i]));
        adaptador = new TareaArrayAdapter<Tarea>(Cursando.this, TAREAS);
        lista.setAdapter(adaptador);
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
            db.execSQL("CREATE TABLE IF NOT EXISTS temporal (control TEXT, passw TEXT)");
            db.execSQL("INSERT INTO temporal (control, passw) VALUES ('"+contr+"','"+pass+"');");
            new descargar().execute("");
        }else{
            String query = "SELECT * FROM temporal;";
            Cursor cs = db.rawQuery(query,null);
            if (db!= null){
                if (cs.moveToFirst()){
                    do{
                        contr = cs.getString(0);
                        pass = cs.getString(1);
                    }while (cs.moveToNext());
                }
                db.execSQL("DROP TABLE temporal;");
                new descargar().execute("");
            }
        }
    }

    private class descargar extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            ws_cursando ws = new ws_cursando();
            res = ws.Cargador(contr,pass);
            return 1;
        }
    }

    AdapterView.OnItemClickListener listaCursos = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                Tarea num = (Tarea)lista.getItemAtPosition(position);
                String nombre = num.getNombre();
                Intent i = new Intent(Cursando.this, Unidades.class);
                Bundle b = new Bundle();
                b.putString("nombre", nombre);
                i.putExtras(b);
                startActivity(i);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
}
