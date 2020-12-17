package com.example.orangepals;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orangepals.adapters.MainAdapter;
import com.example.orangepals.interfaces.VolleyCallback;
import com.example.orangepals.models.Publicacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetalleUsuario extends AppCompatActivity implements View.OnClickListener {
    TextView us_username, us_pais, us_email;
    private JSONObject udata;
    private String usrid;
    private RequestsHelp rh = new RequestsHelp();
    private ListView pubItems;
    private Button editarUsuario, eliminarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_usuario);

        us_username = (TextView) findViewById(R.id.us_username);
        us_pais = (TextView) findViewById(R.id.us_pais);
        us_email = (TextView) findViewById(R.id.us_email);
        editarUsuario = (Button) findViewById(R.id.editarUsuario);
        editarUsuario.setOnClickListener(this);
        eliminarUsuario = (Button) findViewById(R.id.eliminarUsuario);
        eliminarUsuario.setOnClickListener(this);

        pubItems = (ListView) findViewById(R.id.us_publicaciones);
        final ArrayList<Publicacion> listItems = new ArrayList<>();

        getPublicaciones(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                JSONObject r;
                try {
                    r = new JSONObject(response.trim());
                    JSONArray publicaciones = r.getJSONArray("publicaciones");
                    for(int i = 0; i < publicaciones.length(); i++) {
                        listItems.add(new Publicacion(publicaciones.getJSONObject(i).getString("titulo"),
                                publicaciones.getJSONObject(i).getString("texto"),
                                publicaciones.getJSONObject(i).getInt("corazones"),
                                publicaciones.getJSONObject(i).getInt("comentarios"),
                                publicaciones.getJSONObject(i).getBoolean("bookmark"),
                                publicaciones.getJSONObject(i).getInt("id"),
                                publicaciones.getJSONObject(i).getBoolean("es_mio"),
                                publicaciones.getJSONObject(i).getString("fecha")));
                    }
                    Log.i("Publicaciones", listItems.toString());
                    MainAdapter adaptador = new MainAdapter(DetalleUsuario.this, listItems);
                    pubItems.setAdapter(adaptador);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});

        getUsuario(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                JSONObject r;
                try {
                    r = new JSONObject(response.trim());
                    us_username.setText(r.getString("USERNAME"));
                    us_email.setText(r.getString("EMAIL"));
                    us_pais.setText(r.getString("PAIS"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});

    }

    public void getPublicaciones(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            udata = new JSONObject(preferences.getString("usrinfo", null));
            usrid = udata.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest sr = new StringRequest(Request.Method.GET, rh.URI + "/mias/" + this.usrid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetalleUsuario.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue rq = Volley.newRequestQueue(DetalleUsuario.this);
        rq.add(sr);
    }

    public void getUsuario(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            udata = new JSONObject(preferences.getString("usrinfo", null));
            usrid = udata.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest sr = new StringRequest(Request.Method.GET, rh.URI + "/get_user/" + this.usrid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetalleUsuario.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue rq = Volley.newRequestQueue(DetalleUsuario.this);
        rq.add(sr);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editarUsuario:
                Intent mUser = new Intent(DetalleUsuario.this, ModificarUsuario.class);
                startActivity(mUser);
                break;
            case R.id.eliminarUsuario:
                Intent eUser = new Intent(DetalleUsuario.this, EliminarUsuario.class);
                startActivity(eUser);
                break;
        }
    }
}