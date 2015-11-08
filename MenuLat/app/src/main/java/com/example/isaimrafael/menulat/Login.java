package com.example.isaimrafael.menulat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private EditText control, contra;
    private Button acceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        control = (EditText)findViewById(R.id.editText);
        contra = (EditText)findViewById(R.id.editText2);
        acceder = (Button)findViewById(R.id.button);
        acceder.setOnClickListener(AccederOnClickLister);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.logotit);
    }

    OnClickListener AccederOnClickLister = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Login.this, Menu.class);
            startActivity(i);
        }
    };


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, (android.view.Menu) menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

