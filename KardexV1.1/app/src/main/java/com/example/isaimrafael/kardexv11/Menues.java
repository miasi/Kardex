package com.example.isaimrafael.kardexv11;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isaimrafael.kardexv11.web_service.ws_alumno;

import java.util.ArrayList;
import java.util.List;

public class Menues extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LocalDB BaseDatos;
    SQLiteDatabase db;
    private String contr, pass;
    List<String> parametros = new ArrayList<String>();
    List<String> valores = new ArrayList<String>();
    TextView nomalum,nomcarrera, nombre, carrera, semestre, creditos,modulo;
    ImageView logo;

    ws_alumno alumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menues);
        inicializadorMenu();
        SalvaLogin();
    }

    private void SalvaLogin() {
        BaseDatos = new LocalDB(this, "Temporales", null,1);
        db = BaseDatos.getWritableDatabase();
        Bundle b = getIntent().getExtras();
        if (b!= null) {
            contr = b.getString("control");
            pass = b.getString("passWS");
            db.execSQL("CREATE TABLE IF NOT EXISTS temporal (control TEXT, passw TEXT)");
            db.execSQL("INSERT INTO temporal (control, passw) VALUES ('"+contr+"','"+pass+"');");
            new descargar().execute("");
        }
        else{
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
            alumno = new ws_alumno(contr, pass);
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
            modulo = (TextView)findViewById(R.id.MENUModulo);

            nomalum.setText(alumno.getNombre());
            nomcarrera.setText(alumno.getEspecialidad().toLowerCase());

            nombre.setText(alumno.getNombre());
            carrera.setText(alumno.getEspecialidad());
            semestre.setText(String.valueOf(alumno.getSemestre()));
            creditos.setText(String.valueOf(alumno.getCreditosAcumulados()));
            modulo.setText(alumno.getModulo());
            if (modulo.getText().equals(""))
                modulo.setText("MODULO DE ESPECIALIDAD SIN ASIGNAR");
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
            db.execSQL("CREATE TABLE IF NOT EXISTS temporal (control TEXT, passw TEXT)");
            db.execSQL("INSERT INTO temporal (control, passw) VALUES ('"+contr+"','"+pass+"');");
            i = new Intent(Menues.this, Cursando.class);
            Bundle b = new Bundle();
            b.putString("control", contr);
            b.putString("passWS",pass);
            i.putExtras(b);
            startActivity(i);

        } else if (id == R.id.horario) {
            db.execSQL("CREATE TABLE IF NOT EXISTS temporal (control TEXT, passw TEXT)");
            db.execSQL("INSERT INTO temporal (control, passw) VALUES ('"+contr+"','"+pass+"');");
            i = new Intent(Menues.this, Horario.class);
            Bundle b = new Bundle();
            b.putString("control", contr);
            b.putString("passWS", pass);
            i.putExtras(b);
            startActivity(i);

        } else if (id == R.id.hoariomaestro) {
            db.execSQL("CREATE TABLE IF NOT EXISTS temporal (control TEXT, passw TEXT)");
            db.execSQL("INSERT INTO temporal (control, passw) VALUES ('"+contr+"','"+pass+"');");
            i = new Intent(Menues.this, HorarioMaestro.class);
            Bundle b = new Bundle();
            b.putString("control", contr);
            b.putString("passWS", pass);
            i.putExtras(b);
            startActivity(i);

        } else if (id == R.id.mapa) {
            db.execSQL("CREATE TABLE IF NOT EXISTS temporal (control TEXT, passw TEXT)");
            db.execSQL("INSERT INTO temporal (control, passw) VALUES ('"+contr+"','"+pass+"');");
            i = new Intent(Menues.this, MapaTec.class);
            Bundle bun = new Bundle();
            String pal = "hello";
            bun.putString("check", pal);
            i.putExtras(bun);
            startActivity(i);

        } else if (id == R.id.salir) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //endregion
}
