package com.example.orangepals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    Button regresar_interfaz, terminar_registro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        regresar_interfaz = (Button) findViewById(R.id.regresar);
        regresar_interfaz.setOnClickListener(this);

        terminar_registro = (Button) findViewById(R.id.btn_terminar_registro);
        terminar_registro.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regresar:
                Intent RegresarInterfaz = new Intent(Registro.this, Iniciar_Sesion.class);
                startActivity(RegresarInterfaz);
                break;
            case R.id.btn_terminar_registro:
                Intent TerminarRegistro = new Intent(Registro.this,Interfaz_Usuario.class);
                startActivity(TerminarRegistro);
                break;
        }
    }
}
