package com.example.orangepals;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class Nueva_Publicacion extends AppCompatActivity implements View.OnClickListener {

    Button enviar, cancelar;
    String usrid;
    JSONObject udata;
    RequestsHelp rh = new RequestsHelp();
    EditText titulo, escribe_algo, selecciona_tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_publicacion);

        enviar = (Button) findViewById(R.id.btn_enviar);
        enviar.setOnClickListener(this);

        cancelar = (Button) findViewById(R.id.btn_cancelar);
        cancelar.setOnClickListener(this);

        titulo = (EditText) findViewById(R.id.input1);
        escribe_algo = (EditText) findViewById(R.id.input2);
        selecciona_tags = (EditText) findViewById(R.id.input3);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_enviar:
                // Intent EnviarPublicacion = new Intent(Nueva_Publicacion.this, Publicacion.class);
                // startActivity(EnviarPublicacion);
                publicar(new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject r;
                        try {
                            r = new JSONObject(response.trim());
                            Toast.makeText(Nueva_Publicacion.this, r.getString("desc"), Toast.LENGTH_LONG).show();
                            Intent InterfazUsuario = new Intent(Nueva_Publicacion.this,Interfaz_Usuario.class);
                            startActivity(InterfazUsuario);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }});
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

    public void publicar(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            udata = new JSONObject(preferences.getString("usrinfo", null));
            usrid = udata.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest sr = new StringRequest(Request.Method.POST, rh.URI + "/publicar/" + this.usrid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Nueva_Publicacion.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("titulo", titulo.getText().toString());
                params.put("texto", escribe_algo.getText().toString());
                params.put("etiquetas", selecciona_tags.getText().toString());
                return params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(Nueva_Publicacion.this);
        rq.add(sr);
    }
}
