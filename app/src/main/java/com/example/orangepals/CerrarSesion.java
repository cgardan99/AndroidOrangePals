package com.example.orangepals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class CerrarSesion extends AppCompatActivity {

    private JSONObject udata;
    private String usrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cerrar_sesion);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().remove("usrinfo").apply();
        Toast.makeText(CerrarSesion.this, "Has cerrado la sesión", Toast.LENGTH_LONG).show();
        Intent TerminarRegistro = new Intent(CerrarSesion.this, Iniciar_Sesion.class);
        startActivity(TerminarRegistro);
    }
}