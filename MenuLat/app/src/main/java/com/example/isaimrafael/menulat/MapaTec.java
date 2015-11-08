package com.example.isaimrafael.menulat;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaTec extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_tec);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        int display_mode = getResources().getConfiguration().orientation;
        GoogleMap mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        LatLng Tec = new LatLng(24.119780, -110.308746);//24.119780, -110.308746
        //mapa.addMarker(new MarkerOptions().position(Tec).title("Instituto Tecnologico De La Paz"));
        if (display_mode==1) {
            CameraPosition campos = new CameraPosition.Builder()
                    .target(Tec)
                    .zoom(17)
                    .bearing(95)
                    .build();
            CameraUpdate camu = CameraUpdateFactory.newCameraPosition(campos);
            mapa.animateCamera(camu);
            //mapa.clear();
            puntosTec();
        }else{

        }
    }

    public void puntosTec()
    {
        GoogleMap mapa2 = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        LatLng Biblio = new LatLng(24.119901, -110.310025);
        mapa2.addMarker(new MarkerOptions().position(Biblio).title("Biblioteca"));
        LatLng Macro = new LatLng(24.119062, -110.309047);
        mapa2.addMarker(new MarkerOptions().position(Macro).title("Lab. Ing. Sitemas"));
        LatLng Posgrado = new LatLng(24.118994, -110.308519);
        mapa2.addMarker(new MarkerOptions().position(Posgrado).title("Posgrado"));
        LatLng Cafeteria = new LatLng(24.120060, -110.309591);
        mapa2.addMarker(new MarkerOptions().position(Cafeteria).title("Cafeteria"));
        LatLng capi = new LatLng(24.120400, -110.308397);
        mapa2.addMarker(new MarkerOptions().position(capi).title("CAPI"));
        LatLng gym = new LatLng(24.119783, -110.306045);
        mapa2.addMarker(new MarkerOptions().position(gym).title("Gimnasio/Auditorio"));
        LatLng civil = new LatLng(24.118890, -110.310451);
        mapa2.addMarker(new MarkerOptions().position(civil).title("Lab. Ing. Civil"));
        LatLng electro = new LatLng(24.120939, -110.309337);
        mapa2.addMarker(new MarkerOptions().position(electro).title("Lab. Ing. Electromecanica"));
        LatLng bioq = new LatLng(24.119201, -110.310511);
        mapa2.addMarker(new MarkerOptions().position(bioq).title("Lab. Ing. Electromecanica"));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*mMap = googleMap;
        LatLng Tec = new LatLng(24.119829, -110.309486);
        CameraPosition campos = new CameraPosition.Builder()
                .target(Tec)
                .zoom(18)
                .build();
        CameraUpdate camu = CameraUpdateFactory.newCameraPosition(campos);
        mMap.animateCamera(camu);
        mMap.addMarker(new MarkerOptions().position(Tec).title("Instituto Tecnologico De La Paz"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Tec)); */
    }
}
