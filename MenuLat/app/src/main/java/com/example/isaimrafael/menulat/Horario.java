package com.example.isaimrafael.menulat;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.isaimrafael.menulat.MenuLateral.NavigationAdapter;
import com.example.isaimrafael.menulat.MenuLateral.item_objct;

import java.util.ArrayList;

public class Horario extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //region VariablesMenu
    private String[] titulos;
    private DrawerLayout NavDrawerLayout;
    private ListView NavList;
    private ArrayList<item_objct> NavItms;
    private TypedArray NavIcons;
    NavigationAdapter NavAdapter;
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horario);
        CreadorMenu();
    }

    //region Menu
    public void CreadorMenu() {
        NavDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        NavList = (ListView) findViewById(R.id.lista);
        View header = getLayoutInflater().inflate(R.layout.activity_header, null);
        NavList.addHeaderView(header);
        NavIcons = getResources().obtainTypedArray(R.array.navigation_iconos);
        titulos = getResources().getStringArray(R.array.nav_options);
        NavItms = new ArrayList<item_objct>();
        NavItms.add(new item_objct(titulos[0], NavIcons.getResourceId(0, -1)));
        NavItms.add(new item_objct(titulos[1], NavIcons.getResourceId(1, -1)));
        NavItms.add(new item_objct(titulos[2], NavIcons.getResourceId(2, -1)));
        NavItms.add(new item_objct(titulos[3], NavIcons.getResourceId(3, -1)));
        NavItms.add(new item_objct(titulos[4], NavIcons.getResourceId(4, -1)));
        NavItms.add(new item_objct(titulos[5], NavIcons.getResourceId(5, -1)));
        NavAdapter = new NavigationAdapter(this, NavItms);
        NavList.setAdapter(NavAdapter);

        NavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                CreaActivities(position);
            }
        });
    }

    private void CreaActivities(int pos) {
        switch (pos)
        {
            //inicio
            case 1:
            {
                break;
            }
            //Cursando
            case 2:
            {
                break;
            }
            //Horario
            case 3:
            {
                break;
            }
            //Horario Maestros
            case 4:
            {
                break;
            }
            //Mapa
            case 5:
            {
                Intent i = new Intent(Horario.this, MapaTec.class);
                break;
            }
            //Salir
            case 6:
            {
                break;
            }
        }
        Toast toast = Toast.makeText(Horario.this, titulos[pos-1], Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (NavDrawerLayout.isDrawerOpen(NavList)){
                    NavDrawerLayout.closeDrawers();
                }else {
                    NavDrawerLayout.openDrawer(NavList);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

}
