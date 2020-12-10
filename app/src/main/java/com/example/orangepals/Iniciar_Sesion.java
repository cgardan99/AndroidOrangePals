package com.example.orangepals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Iniciar_Sesion extends AppCompatActivity implements View.OnClickListener{

    Button ingresar, registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion);

        ingresar = (Button) findViewById(R.id.btn_enviar);
        ingresar.setOnClickListener(this);

        registrarse = (Button) findViewById(R.id.btn_Registrarse);
        registrarse.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_enviar:
                Intent InterfazUsuario = new Intent(Iniciar_Sesion.this,Interfaz_Usuario.class);
                startActivity(InterfazUsuario);
                break;
            case R.id.btn_Registrarse:
                Intent RegistrarUsuario = new Intent(Iniciar_Sesion.this,Registro.class);
                startActivity(RegistrarUsuario);
                break;
        }
    }
}
