package com.example.orangepals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button iniciar_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciar_app=(Button) findViewById(R.id.iniciar);
        iniciar_app.setOnClickListener(this);
        getSupportActionBar().hide();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iniciar:
                Intent SegundaVentana = new Intent(MainActivity.this, Iniciar_Sesion.class);
                startActivity(SegundaVentana);
        }
    }
}