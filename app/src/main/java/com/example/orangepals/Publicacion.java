package com.example.orangepals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Publicacion extends AppCompatActivity implements View.OnClickListener {

    Button regresar_interfaz_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicacion);

        regresar_interfaz_usuario = (Button) findViewById(R.id.regresar);
        regresar_interfaz_usuario.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regresar:
                Intent RegresarInterfaz = new Intent(Publicacion.this, Interfaz_Usuario.class);
                startActivity(RegresarInterfaz);
        }
    }
}
