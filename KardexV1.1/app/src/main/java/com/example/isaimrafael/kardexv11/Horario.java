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
import com.example.isaimrafael.kardexv11.web_service.ws_cursando;
import com.example.isaimrafael.kardexv11.web_service.ws_curso;
import com.example.isaimrafael.kardexv11.web_service.ws_horario;
import com.example.isaimrafael.kardexv11.web_service.ws_horario_aux;

import java.util.ArrayList;
import java.util.List;

public class Horario extends AppCompatActivity {

    //private static List<String> MateriasFinal = new ArrayList<>();
    private static List<String> HorariosFinal = new ArrayList<>();
    private static ListView lista;
    private static TareaArrayAdapter<Tarea> adaptador;
    private static String[] pals;
    private static String[] horas;
    private static List<ws_horario_aux> lunes = new ArrayList<ws_horario_aux>();
    //List<String> horarioMateria = new ArrayList<>();
    //List<String> horarioElements = new ArrayList<>();
    //List<String> Lunes = new ArrayList<>();
    //List<String> Martes = new ArrayList<>();
    //List<String> Miercoles = new ArrayList<>();
    //List<String> Jueves = new ArrayList<>();
    //List<String> Viernes = new ArrayList<>();
    private static List<ws_horario_aux> martes = new ArrayList<ws_horario_aux>();
    private static List<ws_horario_aux> miercoles = new ArrayList<ws_horario_aux>();
    private static List<ws_horario_aux> jueves = new ArrayList<ws_horario_aux>();
    private static List<ws_horario_aux> viernes = new ArrayList<ws_horario_aux>();
    //private SectionsPagerAdapter mSectionsPagerAdapter;
    //private ViewPager mViewPager;
    String control, password;
    private ws_cursando cursando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();
        control = b.getString("control");
        password = b.getString("passWS");
        new descargar().execute("");
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


                    /*pals = new String[MateriasFinal.size()];//{"Poo", "ED", "moviles", "Distribuidas", "auditoria","Graficacion"};
                    for (int i = 0; i < pals.length; i++) {
                        pals[i] = MateriasFinal.get(i);
                    }
                    horas = new String[HorariosFinal.size()];//{"12:00 - 13:00", "9:00 - 11:00", "7:00 - 9:00"};
                    for (int i = 0; i < horas.length; i++) {
                        horas[i] = HorariosFinal.get(i);
                    }*/
                    break;
                }
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

    private class descargar extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            cursando = new ws_cursando(control, password);
            return 1;
        }

        @Override
        protected void onPostExecute(Object result) {
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
/*









            for (int i = 0; i <horario.getCursos().size(); i++) {

                ws_horario_aux aux = new ws_horario_aux();
                aux.setMateria(horario.getCursos().get(i).getMateria());
                for (int j=0; j<;j++) {
                    if ()
                }



                // Cursos...
                finalMateria.add(horario.getCursos().get(i).getMateria());

                int val2 = horario.getCursos().get(i).getHorarios().size();
                for (int j = 0; j < val2; j++) {
horarioElements.add(horario.getCursos().get(i).getHorarios().get(j).getDia()); //por dia
horarioElements.add(horario.getCursos().get(i).getHorarios().get(j).getHoraInicio()); // menor
horarioElements.add(horario.getCursos().get(i).getHorarios().get(j).getHoraFin()); // mayor
horarioElements.add(horario.getCursos().get(i).getHorarios().get(j).getAula()); // al principio
                }
                horarioElements.add("-----");
            }


            int aux = horarioElements.size();
            int sum = 0;
            int no = 0;
            int num = 0;
            for (; no < horarioMateria.size(); no++) {
                for (; num < aux; num++) {
                    String dia = horarioElements.get(num);
                    if (dia.equals("-----")) {
                        num++;
                        break;
                    }
                    switch (dia) {
                        case "LUNES": {
                            Lunes.add(horarioMateria.get(no));
                            num++;
                            Lunes.add(horarioElements.get(num));
                            num++;
                            Lunes.add(horarioElements.get(num));
                            num++;
                            Lunes.add(horarioElements.get(num));
                            break;
                        }
                        case "MARTES": {
                            Martes.add(horarioMateria.get(no));
                            num++;
                            Martes.add(horarioElements.get(num));
                            num++;
                            Martes.add(horarioElements.get(num));
                            num++;
                            Martes.add(horarioElements.get(num));
                            break;
                        }
                        case "MIERCOLES": {
                            Miercoles.add(horarioMateria.get(no));
                            num++;
                            Miercoles.add(horarioElements.get(num));
                            num++;
                            Miercoles.add(horarioElements.get(num));
                            num++;
                            Miercoles.add(horarioElements.get(num));
                            num++;
                            break;
                        }
                        case "JUEVES": {
                            Jueves.add(horarioMateria.get(no));
                            num++;
                            Jueves.add(horarioElements.get(num));
                            num++;
                            Jueves.add(horarioElements.get(num));
                            num++;
                            Jueves.add(horarioElements.get(num));
                            break;
                        }
                        case "VIERNES": {
                            Viernes.add(horarioMateria.get(no));
                            num++;
                            Viernes.add(horarioElements.get(num));
                            num++;
                            Viernes.add(horarioElements.get(num));
                            num++;
                            Viernes.add(horarioElements.get(num));
                            break;
                        }
                    }
                }
            }
            String cadena = "";
            for (int j = 0; j < Viernes.size(); j++) {
                MateriasFinal.add(Viernes.get(j));
                j++;
                cadena += Viernes.get(j) + " ";
                j++;
                cadena += Viernes.get(j) + " ";
                j++;
                cadena += Viernes.get(j);
                HorariosFinal.add(cadena);
                cadena = "";
            }*/
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
