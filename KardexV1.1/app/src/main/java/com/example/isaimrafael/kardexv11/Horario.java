package com.example.isaimrafael.kardexv11;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.isaimrafael.kardexv11.web_service.ws_cursando;
import com.example.isaimrafael.kardexv11.web_service.ws_curso;
import com.example.isaimrafael.kardexv11.web_service.ws_horario;
import com.example.isaimrafael.kardexv11.web_service.ws_horario_aux;

import java.util.ArrayList;
import java.util.List;

public class Horario extends AppCompatActivity {

    private static ListView lista;
    private static TareaArrayAdapter<Tarea> adaptador;
    private static String[] pals;
    private static String[] horas;
    private static List<ws_horario_aux> lunes;
    private static List<ws_horario_aux> martes;
    private static List<ws_horario_aux> miercoles;
    private static List<ws_horario_aux> jueves;
    private static List<ws_horario_aux> viernes;
    String control, password;
    private ws_cursando cursando;
    private ProgressDialog pd = null;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        int display_mode = getResources().getConfiguration().orientation;
        if (display_mode == 1) {
            lunes = new ArrayList<>();
            martes = new ArrayList<>();
            miercoles = new ArrayList<>();
            jueves = new ArrayList<>();
            viernes = new ArrayList<>();
            Bundle b = getIntent().getExtras();
            control = b.getString("control");
            password = b.getString("passWS");
            new descargar().execute("");
            pd = ProgressDialog.show(context, "Porfavor espere", "Consultando datos del ITLP", true, false);
        }else{
            lunes = new ArrayList<>();
            martes = new ArrayList<>();
            miercoles = new ArrayList<>();
            jueves = new ArrayList<>();
            viernes = new ArrayList<>();
            Bundle b = getIntent().getExtras();
            control = b.getString("control");
            password = b.getString("passWS");
            new descargar().execute("");
            pd = ProgressDialog.show(context, "Porfavor espere", "Consultando datos del ITLP", true, false);
        }
    }

    private String getMin(List<ws_horario> horarios) {
        int menor = Integer.parseInt(horarios.get(0).getHoraInicio());
        for (int i = 0; i < horarios.size(); i++) {
            int x = Integer.parseInt(horarios.get(i).getHoraInicio());
            if (x < menor) {
                menor = x;
            }
        }
        return Integer.toString(menor);
    }

    private String getMax(List<ws_horario> horarios) {
        int mayor = Integer.parseInt(horarios.get(0).getHoraFin());
        for (int i = 0; i < horarios.size(); i++) {
            int x = Integer.parseInt(horarios.get(i).getHoraFin());
            if (x > mayor) {
                mayor = x;
            }
        }
        return Integer.toString(mayor);
    }

    public List<ws_horario_aux> sortList(List<ws_horario_aux> list) {
        int length = list.size() - 1;
        for (int i = 1; i <= length; i++) {
            for (int j = length; j >= i; j--) {
                if (list.get(j - 1).getHoraInicioInt() > list.get(j).getHoraInicioInt()) {
                    ws_horario_aux aux = list.get(j - 1);
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
                        pals[i] = lunes.get(i).getMateria();
                        horas[i] = lunes.get(i).getHorario();
                    }
                    break;
                }
                case 2:
                    textView.setText("Clases del dia Martes");
                    pals = new String[martes.size()];
                    horas = new String[martes.size()];
                    for (int i = 0; i < martes.size(); i++) {
                        pals[i] = martes.get(i).getMateria();
                        horas[i] = martes.get(i).getHorario();
                    }
                    break;
                case 3:
                    textView.setText("Clases del dia Miercoles");
                    pals = new String[miercoles.size()];
                    horas = new String[miercoles.size()];
                    for (int i = 0; i < miercoles.size(); i++) {
                        pals[i] = miercoles.get(i).getMateria();
                        horas[i] = miercoles.get(i).getHorario();
                    }
                    break;
                case 4:
                    textView.setText("Clases del dia Jueves");
                    pals = new String[jueves.size()];
                    horas = new String[jueves.size()];
                    for (int i = 0; i < jueves.size(); i++) {
                        pals[i] = jueves.get(i).getMateria();
                        horas[i] = jueves.get(i).getHorario();
                    }
                    break;
                case 5:
                    textView.setText("Clases del dia Viernes");
                    pals = new String[viernes.size()];
                    horas = new String[viernes.size()];
                    for (int i = 0; i < viernes.size(); i++) {
                        pals[i] = viernes.get(i).getMateria();
                        horas[i] = viernes.get(i).getHorario();
                    }
                    break;
            }
        }
    }

    private class descargar extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            cursando = new ws_cursando(control, password);
            return 1;
        }

        @Override
        protected void onPostExecute(Object result) {
            pd.dismiss();
            for (int i = 0; i < cursando.getCursos().size(); i++) {
                ws_curso curso = cursando.getCursos().get(i);
                // Aux Lists
                List<ws_horario> auxLunes = new ArrayList<ws_horario>();
                List<ws_horario> auxMartes = new ArrayList<ws_horario>();
                List<ws_horario> auxMiercoles = new ArrayList<ws_horario>();
                List<ws_horario> auxJueves = new ArrayList<ws_horario>();
                List<ws_horario> auxViernes = new ArrayList<ws_horario>();
                for (int j = 0; j < curso.getHorarios().size(); j++) {
                    ws_horario horario = curso.getHorarios().get(j);
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
                // Lunes
                if (auxLunes.size() > 0) {
                    ws_horario_aux aux = new ws_horario_aux();
                    aux.setMateria(curso.getMateria());
                    aux.setDia(auxLunes.get(0).getDia());
                    aux.setHoraInicio(getMin(auxLunes));
                    aux.setHoraFin(getMax(auxLunes));
                    aux.setAula(auxLunes.get(0).getAula());
                    lunes.add(aux);
                }
                // Martes
                if (auxMartes.size() > 0) {
                    ws_horario_aux aux = new ws_horario_aux();
                    aux.setMateria(curso.getMateria());
                    aux.setDia(auxMartes.get(0).getDia());
                    aux.setHoraInicio(getMin(auxMartes));
                    aux.setHoraFin(getMax(auxMartes));
                    aux.setAula(auxMartes.get(0).getAula());
                    martes.add(aux);
                }
                // Miercoles
                if (auxMiercoles.size() > 0) {
                    ws_horario_aux aux = new ws_horario_aux();
                    aux.setMateria(curso.getMateria());
                    aux.setDia(auxMiercoles.get(0).getDia());
                    aux.setHoraInicio(getMin(auxMiercoles));
                    aux.setHoraFin(getMax(auxMiercoles));
                    aux.setAula(auxMiercoles.get(0).getAula());
                    miercoles.add(aux);
                }
                // Jueves
                if (auxJueves.size() > 0) {
                    ws_horario_aux aux = new ws_horario_aux();
                    aux.setMateria(curso.getMateria());
                    aux.setDia(auxJueves.get(0).getDia());
                    aux.setHoraInicio(getMin(auxJueves));
                    aux.setHoraFin(getMax(auxJueves));
                    aux.setAula(auxJueves.get(0).getAula());
                    jueves.add(aux);
                }
                // Viernes
                if (auxViernes.size() > 0) {
                    ws_horario_aux aux = new ws_horario_aux();
                    aux.setMateria(curso.getMateria());
                    aux.setDia(auxViernes.get(0).getDia());
                    aux.setHoraInicio(getMin(auxViernes));
                    aux.setHoraFin(getMax(auxViernes));
                    aux.setAula(auxViernes.get(0).getAula());
                    viernes.add(aux);
                }
            }
            lunes = sortList(lunes);
            martes = sortList(martes);
            miercoles = sortList(miercoles);
            jueves = sortList(jueves);
            viernes = sortList(viernes);
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
