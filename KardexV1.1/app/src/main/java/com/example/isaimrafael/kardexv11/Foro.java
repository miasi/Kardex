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

import java.util.List;

public class Foro extends AppCompatActivity {

    private ws_foro foro = new ws_foro();
    private List<ws_foro_mensaje> mensajes;
    ImageButton enviar;
    EditText texto;
    ScrollView scroll;
    TextView mensaje;
    LinearLayout contenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new descargar().execute("");
        enviar = (ImageButton)findViewById(R.id.BtnEnviar);
        texto = (EditText)findViewById(R.id.EnviarMensaje);
        contenido = (LinearLayout)findViewById(R.id.containter);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TextView tv = new TextView(Foro.this);
                    tv.setText(texto.getText());
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
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private class descargar extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            mensajes = foro.consultarMensaje("ISIC-2010-224", "2015", "Agosto-Diciembre", "AUDITORIA INFORMATICA", "K", "a8%x*5$d4#1");
            String s = foro.crearMensaje("ISIC-2010-224", "2015", "Agosto-Diciembre", "AUDITORIA INFORMATICA", "K", "11310222", "test", "a8%x*5$d4#1");
            return 1;
        }
    }
}
