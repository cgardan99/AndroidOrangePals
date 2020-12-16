package com.example.orangepals;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orangepals.adapters.CommentAdapter;
import com.example.orangepals.interfaces.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModificarPublicacion extends AppCompatActivity implements View.OnClickListener {
    RequestsHelp rh = new RequestsHelp();
    EditText edPubTitle, edPubText, edPubTags;
    Button edPubTerminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_publicacion);

        edPubTitle = (EditText) findViewById(R.id.edPubTitle);
        edPubText = (EditText) findViewById(R.id.edPubText);
        edPubTags = (EditText) findViewById(R.id.edPubTags);
        edPubTerminar = (Button) findViewById(R.id.edPubTerminar);
        edPubTerminar.setOnClickListener(this);

        getPublicacion(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                JSONObject respuesta;
                JSONObject r;
                try {
                    respuesta = new JSONObject(response.trim());
                    r = respuesta.getJSONObject("publicacion");
                    edPubTitle.setText(r.getString("TITULO"));
                    edPubText.setText(r.getString("TEXTO_PUBLICACION"));
                    edPubTags.setText(r.getString("TAGS"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});
    }

    public void getPublicacion(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.GET, rh.URI + "/get_publicacion/" + getIntent().getExtras().getInt("id_publicacion"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModificarPublicacion.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue rq = Volley.newRequestQueue(ModificarPublicacion.this);
        rq.add(sr);
    }

    public void modifyPublicacion(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, rh.URI + "/update_publicacion/" + getIntent().getExtras().getInt("id_publicacion"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModificarPublicacion.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("TEXTO_PUBLICACION", edPubText.getText().toString());
                params.put("TITULO", edPubTitle.getText().toString());
                params.put("ETIQUETAS", edPubTags.getText().toString());
                return params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(ModificarPublicacion.this);
        rq.add(sr);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edPubTerminar:
                modifyPublicacion(new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject respuesta;
                        JSONObject r;
                        try {
                            r = new JSONObject(response.trim());
                            Toast.makeText(ModificarPublicacion.this, r.getString("desc"), Toast.LENGTH_LONG).show();
                            Intent RegresarInterfaz = new Intent(ModificarPublicacion.this, Publicacion.class);
                            RegresarInterfaz.putExtra("id_publicacion", getIntent().getExtras().getInt("id_publicacion"));
                            startActivity(RegresarInterfaz);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }});
                break;
        }
    }
}