package com.example.orangepals;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Iniciar_Sesion extends AppCompatActivity implements View.OnClickListener{

    Button ingresar, registrarse;
    TextView email, pwd;
    Boolean error = false;
    RequestsHelp rh = new RequestsHelp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion);

        ingresar = (Button) findViewById(R.id.btn_enviar);
        ingresar.setOnClickListener(this);

        registrarse = (Button) findViewById(R.id.btn_Registrarse);
        registrarse.setOnClickListener(this);

        email = (EditText) findViewById(R.id.input1);
        pwd = (EditText) findViewById(R.id.input2);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_enviar:
                if(email.getText().toString().isEmpty()) {
                    email.setError("Completa este campo, por favor.");
                    error = true;
                }
                if(pwd.getText().toString().isEmpty()) {
                    pwd.setError("Completa este campo, por favor.");
                    error = true;
                }
                if(!error) {
                    StringRequest sr = new StringRequest(Request.Method.POST, rh.URI + "/login/",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    JSONObject r;
                                    try {
                                        r = new JSONObject(response.trim());
                                        Toast.makeText(Iniciar_Sesion.this, r.getString("desc"), Toast.LENGTH_LONG).show();
                                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString("usrinfo", r.getString("usrinfo"));
                                        editor.commit();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Intent InterfazUsuario = new Intent(Iniciar_Sesion.this,Interfaz_Usuario.class);
                                    startActivity(InterfazUsuario);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    if(error.networkResponse.statusCode == 403) {
                                        Toast.makeText(Iniciar_Sesion.this, "Credenciales incorrectas.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", email.getText().toString());
                            params.put("pwd", pwd.getText().toString());
                            return params;
                        }
                    };
                    RequestQueue rq = Volley.newRequestQueue(Iniciar_Sesion.this);
                    rq.add(sr);
                }
                break;
            case R.id.btn_Registrarse:
                Intent RegistrarUsuario = new Intent(Iniciar_Sesion.this,Registro.class);
                startActivity(RegistrarUsuario);
                break;
        }
    }
}
