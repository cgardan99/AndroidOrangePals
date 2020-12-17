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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orangepals.interfaces.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EliminarUsuario extends AppCompatActivity implements View.OnClickListener {

    private JSONObject udata;
    private Button eliminarUsuario;
    private String usrid;
    private RequestsHelp rh = new RequestsHelp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_usuario);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            if(preferences.getString("usrinfo", null) != null) {
                udata = new JSONObject(preferences.getString("usrinfo", null));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        eliminarUsuario = (Button) findViewById(R.id.eliminarUsuario);
        eliminarUsuario.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void eliminarUsuario(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            usrid = udata.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest sr = new StringRequest(Request.Method.GET, rh.URI + "/delete_user/" + this.usrid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EliminarUsuario.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue rq = Volley.newRequestQueue(EliminarUsuario.this);
        rq.add(sr);
    }

    @Override
    public void onClick(View v) {
        eliminarUsuario(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                JSONObject r;
                try {
                    r = new JSONObject(response.trim());
                    Toast.makeText(EliminarUsuario.this, r.getString("desc"), Toast.LENGTH_LONG).show();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    preferences.edit().remove("usrinfo").apply();
                    Intent TerminarRegistro = new Intent(EliminarUsuario.this, Iniciar_Sesion.class);
                    startActivity(TerminarRegistro);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});
    }
}