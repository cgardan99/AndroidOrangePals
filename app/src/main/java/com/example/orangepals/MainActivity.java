package com.example.orangepals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ResourceBundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button iniciar_app;
    private JSONObject udata;
    private String usrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciar_app=(Button) findViewById(R.id.iniciar);
        iniciar_app.setOnClickListener(this);
        getSupportActionBar().hide();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {

            if(preferences.getString("usrinfo", null) != null) {
                udata = new JSONObject(preferences.getString("usrinfo", null));
                Toast.makeText(MainActivity.this, "Bienvenido " + udata.getString("username"), Toast.LENGTH_LONG).show();
                Intent TerminarRegistro = new Intent(MainActivity.this, Interfaz_Usuario.class);
                startActivity(TerminarRegistro);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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