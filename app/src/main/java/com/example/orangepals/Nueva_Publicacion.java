package com.example.orangepals;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Nueva_Publicacion extends AppCompatActivity implements View.OnClickListener {

    Button enviar, cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_publicacion);

        enviar = (Button) findViewById(R.id.btn_enviar);
        enviar.setOnClickListener(this);

        cancelar = (Button) findViewById(R.id.btn_cancelar);
        cancelar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_enviar:
                Intent EnviarPublicacion = new Intent(Nueva_Publicacion.this, Publicacion.class);
                startActivity(EnviarPublicacion);
                break;
            case R.id.btn_cancelar:
                Intent CancelarPublicacion = new Intent(Nueva_Publicacion.this, Interfaz_Usuario.class);
                startActivity(CancelarPublicacion);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
