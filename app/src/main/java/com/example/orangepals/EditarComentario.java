package com.example.orangepals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import com.example.orangepals.adapters.MainAdapter;
import com.example.orangepals.interfaces.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditarComentario extends AppCompatActivity implements View.OnClickListener {
    EditText editCmText;
    Button btnCmEditEnd;
    RequestsHelp rh = new RequestsHelp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_comentario);

        editCmText = (EditText) findViewById(R.id.editCmText);
        btnCmEditEnd = (Button) findViewById(R.id.btnCmEditEnd);
        btnCmEditEnd.setOnClickListener(this);

        getComentario(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                JSONObject r;
                try {
                    r = new JSONObject(response.trim());
                    editCmText.setText(r.getString("texto"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCmEditEnd:
                updateComentario(new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject r;
                        try {
                            r = new JSONObject(response.trim());
                            Toast.makeText(EditarComentario.this, r.getString("desc"), Toast.LENGTH_LONG).show();
                            Intent IrPublicacion = new Intent(EditarComentario.this, Publicacion.class);
                            IrPublicacion.putExtra("id_publicacion", getIntent().getExtras().getInt("id_publicacion"));
                            startActivity(IrPublicacion);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }});
                break;
        }
    }

    public void getComentario(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.GET, rh.URI + "/get_comentario/" + getIntent().getExtras().getInt("id_comentario"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditarComentario.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue rq = Volley.newRequestQueue(EditarComentario.this);
        rq.add(sr);
    }

    public void updateComentario(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, rh.URI + "/update_comentario/" + getIntent().getExtras().getInt("id_comentario"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditarComentario.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("TEXTO", editCmText.getText().toString());
                return params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(EditarComentario.this);
        rq.add(sr);
    }
}