package com.example.orangepals;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.orangepals.adapters.CommentAdapter;
import com.example.orangepals.adapters.MainAdapter;
import com.example.orangepals.interfaces.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Publicacion extends AppCompatActivity implements View.OnClickListener {
    JSONObject udata;
    public String usrid;
    private final RequestsHelp rh = new RequestsHelp();
    private ListView comItems;
    private CommentAdapter adaptador;
    private Integer pub_id;

    // Elementos generales de la vista
    private TextView titulo;
    private TextView uf;
    private TextView pub;
    private TextView pl;
    private TextView pc;
    private Button publicarComentario;
    private EditText TextoComentario;

    Button regresar_interfaz_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicacion);

        // Elementos generales de la vista
        titulo = (TextView) findViewById(R.id.Titulo);
        uf = (TextView) findViewById(R.id.username_fecha);
        pub = (TextView) findViewById(R.id.publicacion);
        pl = (TextView) findViewById(R.id.pub_likes);
        pc = (TextView) findViewById(R.id.pub_comments);
        publicarComentario = (Button) findViewById(R.id.publicarComentario);
        TextoComentario = (EditText) findViewById(R.id.input_comentario);

        regresar_interfaz_usuario = (Button) findViewById(R.id.regresar);
        regresar_interfaz_usuario.setOnClickListener(this);

        publicarComentario.setOnClickListener(this);

        comItems = (ListView) findViewById(R.id.comItems);
        final ArrayList<com.example.orangepals.models.Comentario> listItems = new ArrayList<>();

        getComentarios(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                JSONObject respuesta;
                JSONObject r;
                try {
                    respuesta = new JSONObject(response.trim());
                    r = respuesta.getJSONObject("publicacion");
                    titulo.setText(r.getString("titulo"));
                    uf.setText(String.format("%s %s", r.getString("username"), r.getString("fecha")));
                    pub.setText(r.getString("texto"));
                    pl.setText(r.getString("likes"));
                    pc.setText(r.getString("n_comentarios"));
                    JSONArray comentarios = r.getJSONArray("comentarios");
                    for(int i = 0; i < comentarios.length(); i++) {
                        listItems.add(new com.example.orangepals.models.Comentario(
                                comentarios.getJSONObject(i).getInt("comentario_id"),
                                comentarios.getJSONObject(i).getInt("usuario_id"),
                                comentarios.getJSONObject(i).getString("username"),
                                comentarios.getJSONObject(i).getString("texto"),
                                comentarios.getJSONObject(i).getString("fecha"),
                                comentarios.getJSONObject(i).getInt("likes"),
                                comentarios.getJSONObject(i).getBoolean("es_mio"),
                                comentarios.getJSONObject(i).getBoolean("like_mio")
                        ));
                    }
                    pub_id = r.getInt("id");
                    Log.i("Comentarios", listItems.toString());
                    adaptador = new CommentAdapter(Publicacion.this, listItems);
                    comItems.setAdapter(adaptador);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regresar:
                Intent RegresarInterfaz = new Intent(Publicacion.this, Interfaz_Usuario.class);
                startActivity(RegresarInterfaz);
                break;
            case R.id.publicarComentario:
                StringRequest sr = new StringRequest(Request.Method.POST, rh.URI + "/publicar_comentario/" + pub_id,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject r;
                                try {
                                    r = new JSONObject(response.trim());
                                    Toast.makeText(Publicacion.this, r.getString("desc"), Toast.LENGTH_LONG).show();
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    comItems = (ListView) findViewById(R.id.comItems);
                                    final ArrayList<com.example.orangepals.models.Comentario> listItems = new ArrayList<>();
                                    getComentarios(new VolleyCallback() {
                                        @Override
                                        public void onSuccess(String response) {
                                            JSONObject r;
                                            try {
                                                r = new JSONObject(response.trim());
                                                JSONArray comentarios = r.getJSONArray("comentarios");
                                                for(int i = 0; i < comentarios.length(); i++) {
                                                    listItems.add(new com.example.orangepals.models.Comentario(
                                                            comentarios.getJSONObject(i).getInt("comentario_id"),
                                                            comentarios.getJSONObject(i).getInt("usuario_id"),
                                                            comentarios.getJSONObject(i).getString("username"),
                                                            comentarios.getJSONObject(i).getString("texto"),
                                                            comentarios.getJSONObject(i).getString("fecha"),
                                                            comentarios.getJSONObject(i).getInt("likes"),
                                                            comentarios.getJSONObject(i).getBoolean("es_mio"),
                                                            comentarios.getJSONObject(i).getBoolean("like_mio")
                                                    ));
                                                }
                                                pub_id = r.getInt("id");
                                                Log.i("Comentarios", listItems.toString());
                                                adaptador = new CommentAdapter(Publicacion.this, listItems);
                                                comItems.setAdapter(adaptador);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }});
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if(error.networkResponse.statusCode == 403) {
                                    Toast.makeText(Publicacion.this, "Credenciales incorrectas.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("texto", TextoComentario.getText().toString());
                        return params;
                    }
                };
                RequestQueue rq = Volley.newRequestQueue(Publicacion.this);
                rq.add(sr);
        }
    }

    public void getComentarios(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            udata = new JSONObject(preferences.getString("usrinfo", null));
            usrid = udata.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest sr = new StringRequest(Request.Method.GET, rh.URI + "/detalle_publicacion/" + this.usrid + "/" + getIntent().getExtras().getInt("id_publicacion"),
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
                            Toast.makeText(Publicacion.this, "Ocurrió un error obteniendo los comentarios.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        RequestQueue rq = Volley.newRequestQueue(Publicacion.this);
        rq.add(sr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
