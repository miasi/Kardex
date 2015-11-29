package com.example.isaimrafael.kardexv11;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.example.isaimrafael.kardexv11.rellena_materias_horario.Tarea;
import com.example.isaimrafael.kardexv11.rellena_materias_horario.TareaArrayAdapter;
import java.util.ArrayList;
import java.util.List;

public class Horario extends AppCompatActivity {

    private static ListView lista;
    private static TareaArrayAdapter<Tarea> adaptador;
    private static String[] pals;
    private static String[] horas;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }
    //region menuOptions
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_horario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    //endregion

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "LUN";
                case 1:
                    return "MAR";
                case 2:
                    return "MIE";
                case 3:
                    return "JUE";
                case 4:
                    return "VIE";
            }
            return null;
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_horario, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            lista = (ListView) rootView.findViewById(R.id.listClases);
            int val = getArguments().getInt(ARG_SECTION_NUMBER);
            Textos(val, textView);
            List<Tarea> TAREAS = new ArrayList<Tarea>();
            for (int i = 0; i < pals.length; i++) {
                TAREAS.add(new Tarea(pals[i], horas[i]));
            }
            adaptador = new TareaArrayAdapter<Tarea>(getActivity(), TAREAS);
            lista.setAdapter(adaptador);
            return rootView;
        }
        public void Textos(int val, TextView textView){
            switch (val) {
                case 1:
                    textView.setText("Clases del dia Lunes");
                    pals = new String[]{"Poo", "ED", "moviles", "Distribuidas", "auditoria","Graficacion"};
                    horas = new String[]{"12:00 - 13:00", "9:00 - 11:00", "7:00 - 9:00", "8:00 - 12:00", "12:00 - 13:00","15:00 - 19:00"};
                    break;
                case 2:
                    textView.setText("Clases del dia Martes");
                    pals = new String[]{"Quimica", "ED", "estadistica", "Distribuidas", "auditoria"};
                    horas = new String[]{"12:00 - 13:00", "9:00 - 11:00", "7:00 - 9:00", "8:00 - 12:00", "12:00 - 13:00"};
                    break;
                case 3:
                    textView.setText("Clases del dia Miercoles");
                    pals = new String[]{"Poo", "ED", "calculo", "Distribuidas", "vectorial"};
                    horas = new String[]{"12:00 - 13:00", "9:00 - 11:00", "7:00 - 9:00", "8:00 - 12:00", "12:00 - 13:00"};
                    break;
                case 4:
                    textView.setText("Clases del dia Jueves");
                    pals = new String[]{"web", "ED", "algebra", "Distribuidas", "Poo"};
                    horas = new String[]{"12:00 - 13:00", "9:00 - 11:00", "7:00 - 9:00", "8:00 - 12:00", "12:00 - 13:00"};
                    break;
                case 5:
                    textView.setText("Clases del dia Viernes");
                    pals = new String[]{"Base datos", "ED", "moviles", "Simulacion", "auditoria"};
                    horas = new String[]{"12:00 - 13:00", "9:00 - 11:00", "7:00 - 9:00", "8:00 - 12:00", "12:00 - 13:00"};
                    break;
            }


    }
    }
}

