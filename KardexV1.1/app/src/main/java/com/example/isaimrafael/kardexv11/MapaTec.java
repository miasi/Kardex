package com.example.isaimrafael.kardexv11;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaTec extends AppCompatActivity {

    GoogleMap mapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_tec);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mapa = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mapa.setMyLocationEnabled(true);
        int display_mode = getResources().getConfiguration().orientation;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            LatLng Tec = new LatLng(24.119780, -110.308746);//24.119780, -110.308746
            if (display_mode == 1) {
                CameraPosition campos = new CameraPosition.Builder()
                        .target(Tec)
                        .zoom(17)
                        .bearing(95)
                        .build();
                CameraUpdate camu = CameraUpdateFactory.newCameraPosition(campos);
                mapa.animateCamera(camu);
                puntosTec();
            }
            else{
                CameraPosition campos = new CameraPosition.Builder()
                        .target(Tec)
                        .zoom(15)
                        .bearing(95)
                        .build();
                CameraUpdate camu = CameraUpdateFactory.newCameraPosition(campos);
                mapa.animateCamera(camu);
                puntosTec();
            }
        } else {
            LatLng tec = new LatLng(24.119700, -110.308787);
            mapa.addMarker(new MarkerOptions().position(tec).title("Tecnológico de La Paz")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            CameraPosition campo = new CameraPosition.Builder()
                    .target(tec)
                    .zoom(14)
                    .build();
            CameraUpdate camUP = CameraUpdateFactory.newCameraPosition(campo);
            mapa.animateCamera(camUP);
            puntosTec();
        }

    }

    public void puntosTec() {
        GoogleMap mapa2 = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        salones(mapa2);
        Adminis(mapa2);
        laboratorios(mapa2);
    }

    public void salones(GoogleMap mapa2){

        LatLng pintura  = new LatLng(24.119569, -110.309806);
        mapa2.addMarker(new MarkerOptions().position(pintura).title("Taller de pintura")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng SalonesO  = new LatLng(24.120362, -110.309644);
        mapa2.addMarker(new MarkerOptions().position(SalonesO).title("Aulas O")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        LatLng SalonesE = new LatLng(24.121190, -110.310249);
        mapa2.addMarker(new MarkerOptions().position(SalonesE).title("Aulas E")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        LatLng SalonesD = new LatLng(24.120991, -110.310472);
        mapa2.addMarker(new MarkerOptions().position(SalonesD).title("Aulas D")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        LatLng SalonesC = new LatLng(24.120719, -110.310536);
        mapa2.addMarker(new MarkerOptions().position(SalonesC).title("Aulas C")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng SalonesB = new LatLng(24.120315, -110.310499);
        mapa2.addMarker(new MarkerOptions().position(SalonesB).title("Aulas B")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        LatLng salonesS = new LatLng(24.119690, -110.309291);
        mapa2.addMarker(new MarkerOptions().position(salonesS).title("Aulas S")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng Posgrado = new LatLng(24.118994, -110.308519);
        mapa2.addMarker(new MarkerOptions().position(Posgrado).title("Aulas MD")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng Macro = new LatLng(24.119062, -110.309047);
        mapa2.addMarker(new MarkerOptions().position(Macro).title("Aulas MC")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng SalonesH = new LatLng(24.119382, -110.310385);
        mapa2.addMarker(new MarkerOptions().position(SalonesH).title("Aulas H")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng SalonesCS = new LatLng(24.118732, -110.308713);
        mapa2.addMarker(new MarkerOptions().position(SalonesCS).title("Aulas CS")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng SalonesL = new LatLng(24.118695, -110.310174);
        mapa2.addMarker(new MarkerOptions().position(SalonesL).title("Aulas L")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng salonT = new LatLng(24.120071, -110.309014);
        mapa2.addMarker(new MarkerOptions().position(salonT).title("Aulas T")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }

    public void Adminis(GoogleMap mapa2){
        LatLng ingenierias = new LatLng(24.119250, -110.309854);
        mapa2.addMarker(new MarkerOptions().position(ingenierias).title("Depto. de ingenierias")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        LatLng cafeteria = new LatLng(24.120042, -110.309599);
        mapa2.addMarker(new MarkerOptions().position(cafeteria).title("Cafeteria")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        LatLng incubadora = new LatLng(24.120339, -110.309218);
        mapa2.addMarker(new MarkerOptions().position(incubadora).title("Incubadora de empresas")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng difusion = new LatLng(24.120782, -110.309042);
        mapa2.addMarker(new MarkerOptions().position(difusion).title("Depto. de difusion y comunicación")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng EdAdmin = new LatLng(24.120038, -110.310723);
        mapa2.addMarker(new MarkerOptions().position(EdAdmin).title("Edificio administrativo")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng Biblio = new LatLng(24.119901, -110.310025);
        mapa2.addMarker(new MarkerOptions().position(Biblio).title("Biblioteca")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        LatLng capi = new LatLng(24.120400, -110.308397);
        mapa2.addMarker(new MarkerOptions().position(capi).title("CAPI")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        LatLng gym = new LatLng(24.119783, -110.306045);
        mapa2.addMarker(new MarkerOptions().position(gym).title("Gimnasio/Auditorio")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        LatLng Ccomputo  = new LatLng(24.119637, -110.310185);
        mapa2.addMarker(new MarkerOptions().position(Ccomputo).title("Centro de computo")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }

    public void laboratorios(GoogleMap mapa2){
        LatLng civil = new LatLng(24.118890, -110.310451);
        mapa2.addMarker(new MarkerOptions().position(civil).title("Lab. Ing. Civil")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        LatLng electro = new LatLng(24.120939, -110.309337);
        mapa2.addMarker(new MarkerOptions().position(electro).title("Lab. Ing. Electromecanica")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        LatLng bioq = new LatLng(24.119201, -110.310511);
        mapa2.addMarker(new MarkerOptions().position(bioq).title("Lab. Ing. Bioquimica")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        LatLng fisica = new LatLng(24.120071, -110.309014);
        mapa2.addMarker(new MarkerOptions().position(fisica).title("Lab. de Fisica")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        LatLng ecoadmin = new LatLng(24.120957, -110.309835);
        mapa2.addMarker(new MarkerOptions().position(ecoadmin).title("Lab. computo Economico/Administrativas")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        LatLng Macro = new LatLng(24.119062, -110.309047);
        mapa2.addMarker(new MarkerOptions().position(Macro).title("Lab. Ing. Sistemas")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        LatLng industrial = new LatLng(24.119914, -110.309351);
        mapa2.addMarker(new MarkerOptions().position(industrial).title("Lab. Industrial")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        LatLng quimica = new LatLng(24.120608, -110.309344);
        mapa2.addMarker(new MarkerOptions().position(quimica).title("Lab. de Quimica")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        LatLng computo = new LatLng(24.119027, -110.309870);
        mapa2.addMarker(new MarkerOptions().position(computo).title("Lab. de Computo")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        LatLng biotecnologia = new LatLng(24.119378, -110.309535);
        mapa2.addMarker(new MarkerOptions().position(biotecnologia).title("Lab. de Biotecnologia")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mapa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        GoogleMap mapa2 = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        int id = item.getItemId();
        if (id == R.id.todo) {
            puntosTec();
            return true;
        }
        if (id == R.id.salones){
            mapa.clear();
            salones(mapa2);
            return true;
        }
        if (id==R.id.edificiosAdmin){
            mapa.clear();
            Adminis(mapa2);
            return true;
        }
        if (id==R.id.laboratorios){
            mapa.clear();
            laboratorios(mapa2);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
