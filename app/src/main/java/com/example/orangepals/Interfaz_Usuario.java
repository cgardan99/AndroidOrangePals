package com.example.orangepals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Interfaz_Usuario extends AppCompatActivity implements View.OnClickListener {

    Button crear_publicacion,ir_publicacion1,ir_publicacion2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interfaz_usuario);

        crear_publicacion = (Button) findViewById(R.id.btn_crear_publicacion);
        crear_publicacion.setOnClickListener(this);

        ir_publicacion1 = (Button) findViewById(R.id.btn_publicacion1);
        ir_publicacion1.setOnClickListener(this);

        ir_publicacion2 = (Button) findViewById(R.id.btn_publicacion2);
        ir_publicacion2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_crear_publicacion:
                Intent CrearPublicacion = new Intent(Interfaz_Usuario.this, Nueva_Publicacion.class);
                startActivity(CrearPublicacion);
                break;
            case R.id.btn_publicacion1:
                Intent IrPublicacion1 = new Intent(Interfaz_Usuario.this, Publicacion.class);
                startActivity(IrPublicacion1);
                break;
            case R.id.btn_publicacion2:
                Intent IrPublicacion2 = new Intent(Interfaz_Usuario.this, Publicacion.class);
                startActivity(IrPublicacion2);
                break;
        }
    }
}
