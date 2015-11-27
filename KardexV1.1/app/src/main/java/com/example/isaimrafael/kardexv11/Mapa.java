package com.example.isaimrafael.kardexv11;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    GoogleMap mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
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
                //mapa.clear();
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
                //mapa.clear();
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
        }

    }

    public void puntosTec() {
        GoogleMap mapa2 = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        LatLng Biblio = new LatLng(24.119901, -110.310025);
        mapa2.addMarker(new MarkerOptions().position(Biblio).title("Biblioteca")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng Macro = new LatLng(24.119062, -110.309047);
        mapa2.addMarker(new MarkerOptions().position(Macro).title("Macrocentro")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng Posgrado = new LatLng(24.118994, -110.308519);
        mapa2.addMarker(new MarkerOptions().position(Posgrado).title("Posgrado")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng Cafeteria = new LatLng(24.120060, -110.309591);
        mapa2.addMarker(new MarkerOptions().position(Cafeteria).title("Cafeteria")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng capi = new LatLng(24.120400, -110.308397);
        mapa2.addMarker(new MarkerOptions().position(capi).title("CAPI")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng gym = new LatLng(24.119783, -110.306045);
        mapa2.addMarker(new MarkerOptions().position(gym).title("Gimnasio/Auditorio")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng civil = new LatLng(24.118890, -110.310451);
        mapa2.addMarker(new MarkerOptions().position(civil).title("Lab. Ing. Civil")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng electro = new LatLng(24.120939, -110.309337);
        mapa2.addMarker(new MarkerOptions().position(electro).title("Lab. Ing. Electromecanica")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng bioq = new LatLng(24.119201, -110.310511);
        mapa2.addMarker(new MarkerOptions().position(bioq).title("Lab. Ing. Bioquimica")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng danza = new LatLng(24.120748, -110.308929);
        mapa2.addMarker(new MarkerOptions().position(danza).title("Salon de danza")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng Ccomputo  = new LatLng(24.119637, -110.310185);
        mapa2.addMarker(new MarkerOptions().position(Ccomputo).title("Centro de computo")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng pintura  = new LatLng(24.119569, -110.309806);
        mapa2.addMarker(new MarkerOptions().position(pintura).title("Salon de pintura")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng fisica = new LatLng(24.120071, -110.309014);
        mapa2.addMarker(new MarkerOptions().position(fisica).title("Lab. de Fisica")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng quimica = new LatLng(24.120608, -110.309344);
        mapa2.addMarker(new MarkerOptions().position(quimica).title("Lab. de Quimica")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng ecoadmin = new LatLng(24.120957, -110.309835);
        mapa2.addMarker(new MarkerOptions().position(ecoadmin).title("Lab. computo Economico/Administrativas")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
    }
}
