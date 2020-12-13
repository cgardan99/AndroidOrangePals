package com.example.orangepals;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.List;
import java.util.Map;

public class Interfaz_Usuario extends AppCompatActivity implements View.OnClickListener {
    private ListView pubItems;
    private MainAdapter adaptador;
    private RequestsHelp rh = new RequestsHelp();

    Button crear_publicacion,ir_publicacion1,ir_publicacion2;
    TextView subtitulo;
    JSONObject udata;
    public String usrid;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interfaz_usuario);
        pubItems = (ListView) findViewById(R.id.pubItems);
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
                                publicaciones.getJSONObject(i).getBoolean("bookmark")));
                    }
                    Log.i("Publicaciones", listItems.toString());
                    adaptador = new MainAdapter(Interfaz_Usuario.this, listItems);
                    pubItems.setAdapter(adaptador);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});

        crear_publicacion = (Button) findViewById(R.id.btn_crear_publicacion);
        crear_publicacion.setOnClickListener(this);

        //ir_publicacion1 = (Button) findViewById(R.id.btn_publicacion1);
        //ir_publicacion1.setOnClickListener(this);

        //ir_publicacion2 = (Button) findViewById(R.id.btn_publicacion2);
        //ir_publicacion2.setOnClickListener(this);

        subtitulo = (TextView) findViewById(R.id.subtitulo);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            udata = new JSONObject(preferences.getString("usrinfo", null));
            subtitulo.setText("¡Hola " + udata.getString("username") + "!");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_crear_publicacion:
                Intent CrearPublicacion = new Intent(Interfaz_Usuario.this, Nueva_Publicacion.class);
                startActivity(CrearPublicacion);
                break;
            //case R.id.btn_publicacion1:
            //    Intent IrPublicacion1 = new Intent(Interfaz_Usuario.this, Publicacion.class);
            //    startActivity(IrPublicacion1);
            //    break;
            //case R.id.btn_publicacion2:
            //    Intent IrPublicacion2 = new Intent(Interfaz_Usuario.this, Publicacion.class);
            //    startActivity(IrPublicacion2);
            //    break;
        }
    }

    public void getPublicaciones(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            udata = new JSONObject(preferences.getString("usrinfo", null));
            usrid = udata.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest sr = new StringRequest(Request.Method.GET, rh.URI + "/obtener_publicaciones/" + this.usrid,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    callback.onSuccess(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse.statusCode == 403) {
                        Toast.makeText(Interfaz_Usuario.this, "Ocurrió un error obteniendo las publicaciones.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        RequestQueue rq = Volley.newRequestQueue(Interfaz_Usuario.this);
        rq.add(sr);
    }
}
