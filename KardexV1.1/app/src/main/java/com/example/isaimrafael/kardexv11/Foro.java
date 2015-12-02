package com.example.isaimrafael.kardexv11;

import android.app.ActionBar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isaimrafael.kardexv11.web_service.ws_foro;
import com.example.isaimrafael.kardexv11.web_service.ws_foro_mensaje;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Foro extends AppCompatActivity {

    private ws_foro foro = new ws_foro();
    private List<ws_foro_mensaje> mensajes;
    ImageButton enviar;
    EditText texto;
    ScrollView scroll;
    TextView mensaje;
    LinearLayout contenido;
    String plan, año, periodo, materia, grupo, alumno, cadena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();
        plan = b.getString("Plan");
        año = b.getString("Año");
        periodo = b.getString("Periodo");
        materia = b.getString("Materia");
        grupo = b.getString("Grupo");
        alumno = b.getString("Alumno");
        enviar = (ImageButton)findViewById(R.id.BtnEnviar);
        texto = (EditText)findViewById(R.id.EnviarMensaje);
        contenido = (LinearLayout)findViewById(R.id.containter);
        enviar.setOnClickListener(enviodeChat);
        new descargar().execute("");
    }

    View.OnClickListener enviodeChat = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                TextView tv = new TextView(Foro.this);
                tv.setText(texto.getText());
                cadena = texto.getText().toString();
                RelativeLayout rl = new RelativeLayout(Foro.this);
                RelativeLayout.LayoutParams parametros = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(parametros);
                rl.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                rl.addView(tv);
                contenido.addView(rl);
                texto.setText("");
                texto.setHint("Escribe aquí...");
                texto.setFocusable(true);
                new descargarEnvio().execute("");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private class descargarEnvio extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            String s = foro.crearMensaje(plan, año, periodo, materia, grupo, alumno, cadena , Principal.passWS);
            String check = s;
            if (check.equals("")){}
            return 1;
        }
    }

    private class descargar extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            mensajes = foro.consultarMensaje(plan, año, periodo, materia, grupo, Principal.passWS);
            return 1;
        }

        @Override
        protected void onPostExecute(Object result) {
            String results=null;
            for (int i =0; i < mensajes.size(); i++){
                results = mensajes.get(i).getNombre()+": "+mensajes.get(i).getMensaje();
                TextView tv = new TextView(Foro.this);
                tv.setText(results);
                RelativeLayout rl = new RelativeLayout(Foro.this);
                RelativeLayout.LayoutParams parametros = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(parametros);
                rl.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                rl.addView(tv);
                contenido.addView(rl);
            }
        }
    }
}
