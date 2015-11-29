package com.example.isaimrafael.kardexv11;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isaimrafael.kardexv11.web_service.ws_info_alumno;

import java.util.ArrayList;
import java.util.List;

public class Menues extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LocalDB BaseDatos;
    SQLiteDatabase db;
    private String res;
    private String contr, pass;
    List<String> parametros = new ArrayList<String>();
    List<String> valores = new ArrayList<String>();
    TextView nomalum,nomcarrera, nombre, carrera, semestre, creditos;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menues);
        inicializadorMenu();
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
            ws_info_alumno ws = new ws_info_alumno();
            res = ws.Cargador(contr,pass);
            char[] prube = res.toCharArray();
            String aux="", aux2="";
            boolean band = false;
            for (int i=0; i < res.length(); i++){
                if (prube[i]=='{'){
                    i++;
                    while(prube[i]!='}' & !band){
                        aux += prube[i];
                        i++;
                        if (prube[i]=='=') {
                            i++;
                            parametros.add(aux);
                            aux="";
                            while(prube[i]!=';'){
                                aux2+= prube[i];
                                i++;
                            }
                            i++;
                            i++;
                            valores.add(aux2);
                            aux2="";
                        }
                    }
                }
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Object result){
            MostrarInfoLayout();
            super.onPostExecute(result);
        }
    }

    public void MostrarInfoLayout(){
        try {
            nomalum = (TextView)findViewById(R.id.NomAlumno);
            nomcarrera = (TextView)findViewById(R.id.NomCarrera);
            logo = (ImageView)findViewById(R.id.imagenLogo);
            nombre = (TextView)findViewById(R.id.MENUnombreAlumno);
            carrera = (TextView)findViewById(R.id.MENUnombreCarrera);
            semestre = (TextView)findViewById(R.id.MENUnombreSemestre);
            creditos = (TextView)findViewById(R.id.MENUCreditos);
            nomalum.setText(valores.get(1));
            nomcarrera.setText(valores.get(5).toLowerCase());
            nombre.setText(valores.get(1));
            carrera.setText(valores.get(5));
            semestre.setText(valores.get(8));
            creditos.setText(valores.get(9));
            if (nomcarrera.getText().equals("ingenieria en sistemas computacionales"))
                logo.setImageResource(R.drawable.logo_sistemas);
            else if (nomcarrera.getText().equals("ingenieria civil"))
                logo.setImageResource(R.drawable.logo_civil);
            else if (nomcarrera.getText().equals("licenciatura en administracion"))
                logo.setImageResource(R.drawable.logo_administracion);
            else if (nomcarrera.getText().equals("ingenieria bioquimica"))
                logo.setImageResource(R.drawable.logo_bioquimica);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //region menulateral
    public void inicializadorMenu(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent i;
        int id = item.getItemId();

        if (id == R.id.cursando) {
            i = new Intent(Menues.this, Cursando.class);
            Bundle b = new Bundle();
            b.putString("control", contr);
            b.putString("passWS",pass);
            i.putExtras(b);
            startActivity(i);

        } else if (id == R.id.horario) {
            i = new Intent(Menues.this, Horario.class);
            startActivity(i);

        } else if (id == R.id.hoariomaestro) {
            i = new Intent(Menues.this, HorarioMaestro.class);
            startActivity(i);

        } else if (id == R.id.mapa) {
            i = new Intent(Menues.this, MapaTec.class);
            Bundle bun = new Bundle();
            String pal = "hello";
            bun.putString("check", pal);
            i.putExtras(bun);
            startActivity(i);

        } else if (id == R.id.salir) {
            db.execSQL("DROP TABLE temporal;");
            /*Intent intent = new Intent(Menues.this, Principal.class);
            Bundle b = new Bundle();
            String valuess="reset";
            b.putString("Reset", valuess);
            intent.putExtras(b);
            startActivity(intent);*/
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //endregion
}