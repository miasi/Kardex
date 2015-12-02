package com.example.isaimrafael.kardexv11;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.isaimrafael.kardexv11.rellena_materias_horario.Tarea;
import com.example.isaimrafael.kardexv11.rellena_materias_horario.TareaArrayAdapter;
import com.example.isaimrafael.kardexv11.web_service.ws_horario_maestro;
import com.example.isaimrafael.kardexv11.web_service.ws_horario_maestro_personal;

import java.util.ArrayList;
import java.util.List;

public class HorarioReal_maestro extends AppCompatActivity {

    private static ListView lista;
    private static TareaArrayAdapter<Tarea> adaptador;
    private static String[] pals;
    private static String[] horas;
    private static List<ws_horario_maestro_personal> lunes;
    private static List<ws_horario_maestro_personal> martes;
    private static List<ws_horario_maestro_personal> miercoles;
    private static List<ws_horario_maestro_personal> jueves;
    private static List<ws_horario_maestro_personal> viernes;
    private String nombre, password;
    private ws_horario_maestro maestro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lunes = new ArrayList<>();
        martes = new ArrayList<>();
        miercoles = new ArrayList<>();
        jueves = new ArrayList<>();
        viernes = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        password = b.getString("Password");
        nombre = b.getString("Nombre");
        new descargar().execute("");
    }

    private List<ws_horario_maestro_personal> sortList(List<ws_horario_maestro_personal> list) {
        int length = list.size() - 1;
        for (int i = 1; i <= length; i++) {
            for (int j = length; j >= i; j--) {
                if (list.get(j - 1).getHoraInt() > list.get(j).getHoraInt()) {
                    ws_horario_maestro_personal aux = list.get(j - 1);
                    list.set(j - 1, list.get(j));
                    list.set(j, aux);
                }
            }
        }
        return list;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        public void Textos(int val, TextView textView) {
            switch (val) {
                case 1: {
                    textView.setText("Clases del dia Lunes");
                    pals = new String[lunes.size()];
                    horas = new String[lunes.size()];
                    for (int i = 0; i < lunes.size(); i++) {
                        pals[i] = lunes.get(i).getLugar();
                        horas[i] = lunes.get(i).getHorario();
                    }
                    break;
                }
                case 2:
                    textView.setText("Clases del dia Martes");
                    pals = new String[martes.size()];
                    horas = new String[martes.size()];
                    for (int i = 0; i < martes.size(); i++) {
                        pals[i] = martes.get(i).getLugar();
                        horas[i] = martes.get(i).getHorario();
                    }
                    break;
                case 3:
                    textView.setText("Clases del dia Miercoles");
                    pals = new String[miercoles.size()];
                    horas = new String[miercoles.size()];
                    for (int i = 0; i < miercoles.size(); i++) {
                        pals[i] = miercoles.get(i).getLugar();
                        horas[i] = miercoles.get(i).getHorario();
                    }
                    break;
                case 4:

                    textView.setText("Clases del dia Jueves");
                    pals = new String[jueves.size()];
                    horas = new String[jueves.size()];
                    for (int i = 0; i < jueves.size(); i++) {
                        pals[i] = jueves.get(i).getLugar();
                        horas[i] = jueves.get(i).getHorario();
                    }
                    break;
                case 5:
                    textView.setText("Clases del dia Viernes");
                    pals = new String[viernes.size()];
                    horas = new String[viernes.size()];
                    for (int i = 0; i < viernes.size(); i++) {
                        pals[i] = viernes.get(i).getLugar();
                        horas[i] = viernes.get(i).getHorario();
                    }
                    break;
            }
        }
    }

    private class descargar extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            maestro = new ws_horario_maestro(nombre, password);
            return 1;
        }

        @Override
        protected void onPostExecute(Object result) {
            // Aux Lists
            List<ws_horario_maestro_personal> auxLunes = new ArrayList<>();
            List<ws_horario_maestro_personal> auxMartes = new ArrayList<>();
            List<ws_horario_maestro_personal> auxMiercoles = new ArrayList<>();
            List<ws_horario_maestro_personal> auxJueves = new ArrayList<>();
            List<ws_horario_maestro_personal> auxViernes = new ArrayList<>();
            for (int j = 0; j < maestro.getHorariosMaestros().size(); j++) {
                ws_horario_maestro_personal horario = maestro.getHorariosMaestros().get(j);
                if (horario.getDia().toLowerCase().equals("lunes")) {
                    auxLunes.add(horario);
                } else if (horario.getDia().toLowerCase().equals("martes")) {
                    auxMartes.add(horario);
                } else if (horario.getDia().toLowerCase().equals("miercoles")) {
                    auxMiercoles.add(horario);
                } else if (horario.getDia().toLowerCase().equals("jueves")) {
                    auxJueves.add(horario);
                } else if (horario.getDia().toLowerCase().equals("viernes")) {
                    auxViernes.add(horario);
                }
            }
            // Sort Lists
            auxLunes = sortList(auxLunes);
            auxMartes = sortList(auxMartes);
            auxMiercoles = sortList(auxMiercoles);
            auxJueves = sortList(auxJueves);
            auxViernes = sortList(auxViernes);
            // Lunes
            if (auxLunes.size() > 0) {
                int length = auxLunes.size() - 1;
                int i = 0;
                do {
                    ws_horario_maestro_personal aux = new ws_horario_maestro_personal();
                    aux.setDia(auxLunes.get(i).getDia());
                    aux.setHora(auxLunes.get(i).getHora());
                    aux.setLugar(auxLunes.get(i).getLugar());
                    while (aux.getLugar().equals(auxLunes.get(i).getLugar()) && i < length) {
                        i++;
                    }
                    aux.setHoraFin(auxLunes.get(i - 1).getHora());
                    lunes.add(aux);
                } while (i < length);
            }
            // Martes
            if (auxMartes.size() > 0) {
                int length = auxMartes.size() - 1;
                int i = 0;
                do {
                    ws_horario_maestro_personal aux = new ws_horario_maestro_personal();
                    aux.setDia(auxMartes.get(i).getDia());
                    aux.setHora(auxMartes.get(i).getHora());
                    aux.setLugar(auxMartes.get(i).getLugar());
                    while (aux.getLugar().equals(auxMartes.get(i).getLugar()) && i < length) {
                        i++;
                    }
                    aux.setHoraFin(auxMartes.get(i - 1).getHora());
                    martes.add(aux);
                } while (i < length);
            }
            // Miercoles
            if (auxMiercoles.size() > 0) {
                int length = auxMiercoles.size() - 1;
                int i = 0;
                do {
                    ws_horario_maestro_personal aux = new ws_horario_maestro_personal();
                    aux.setDia(auxMiercoles.get(i).getDia());
                    aux.setHora(auxMiercoles.get(i).getHora());
                    aux.setLugar(auxMiercoles.get(i).getLugar());
                    while (aux.getLugar().equals(auxMiercoles.get(i).getLugar()) && i < length) {
                        i++;
                    }
                    aux.setHoraFin(auxMiercoles.get(i - 1).getHora());
                    miercoles.add(aux);
                } while (i < length);
            }
            // Jueves
            if (auxJueves.size() > 0) {
                int length = auxJueves.size() - 1;
                int i = 0;
                do {
                    ws_horario_maestro_personal aux = new ws_horario_maestro_personal();
                    aux.setDia(auxJueves.get(i).getDia());
                    aux.setHora(auxJueves.get(i).getHora());
                    aux.setLugar(auxJueves.get(i).getLugar());
                    while (aux.getLugar().equals(auxJueves.get(i).getLugar()) && i < length) {
                        i++;
                    }
                    aux.setHoraFin(auxJueves.get(i - 1).getHora());
                    jueves.add(aux);
                } while (i < length);
            }
            // Viernes
            if (auxViernes.size() > 0) {
                int length = auxViernes.size() - 1;
                int i = 0;
                do {
                    ws_horario_maestro_personal aux = new ws_horario_maestro_personal();
                    aux.setDia(auxViernes.get(i).getDia());
                    aux.setHora(auxViernes.get(i).getHora());
                    aux.setLugar(auxViernes.get(i).getLugar());
                    while (aux.getLugar().equals(auxViernes.get(i).getLugar()) && i < length) {
                        i++;
                    }
                    aux.setHoraFin(auxViernes.get(i - 1).getHora());
                    viernes.add(aux);
                } while (i < length);
            }
            try {
                ViewPager mViewPager;
                SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                mViewPager = (ViewPager) findViewById(R.id.container);
                mViewPager.setAdapter(mSectionsPagerAdapter);
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(mViewPager);
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }

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
}
