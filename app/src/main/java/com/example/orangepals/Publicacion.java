package com.example.orangepals;

import android.annotation.SuppressLint;
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
    private Boolean guardado, liked;

    // Elementos generales de la vista
    private TextView titulo;
    private TextView uf;
    private TextView pub;
    private TextView pl;
    private TextView pc;
    private Button publicarComentario, saved, editar_publicacion, eliminar_publicacion, icon_like, icon_comentario;
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
        saved = (Button) findViewById(R.id.saved);
        editar_publicacion = (Button) findViewById(R.id.editar_publicacion);
        eliminar_publicacion = (Button) findViewById(R.id.eliminar_publicacion);
        TextoComentario = (EditText) findViewById(R.id.input_comentario);
        icon_like = (Button) findViewById(R.id.icon_like);
        icon_comentario = (Button) findViewById(R.id.icon_comentario);

        regresar_interfaz_usuario = (Button) findViewById(R.id.regresar);
        regresar_interfaz_usuario.setOnClickListener(this);

        publicarComentario = (Button) findViewById(R.id.publicarComentario);
        publicarComentario.setOnClickListener(this);
        eliminar_publicacion.setOnClickListener(this);
        editar_publicacion.setOnClickListener(this);
        saved.setOnClickListener(this);
        icon_like.setOnClickListener(this);

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
                    guardado = r.getBoolean("bookmark");
                    liked = r.getBoolean("like_mio");
                    if(guardado) {
                        saved.setBackgroundResource(R.drawable.bookmark_s);
                    } else {
                        saved.setBackgroundResource(R.drawable.bookmark);
                    }
                    if(liked) {
                        icon_like.setBackgroundResource(R.drawable.icon_like_s);
                    } else {
                        icon_like.setBackgroundResource(R.drawable.icon_like);
                    }
                    if(r.getBoolean("es_mio")) {
                        editar_publicacion.setVisibility(View.VISIBLE);
                        eliminar_publicacion.setVisibility(View.VISIBLE);
                    }
                    if(r.getBoolean("like_mio"))
                        icon_like.setBackgroundResource(R.drawable.icon_like_s);
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
                                comentarios.getJSONObject(i).getBoolean("like_mio"),
                                comentarios.getJSONObject(i).getInt("publicacion_id")
                        ));
                    }
                    pub_id = r.getInt("id");
                    adaptador = new CommentAdapter(Publicacion.this, listItems);
                    comItems.setAdapter(adaptador);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regresar:
                Intent RegresarInterfaz = new Intent(Publicacion.this, Interfaz_Usuario.class);
                startActivity(RegresarInterfaz);
                break;
            case R.id.publicarComentario:
                StringRequest peticion = new StringRequest(Request.Method.POST, rh.URI + "/publicar_comentario/" + pub_id + "/" + this.usrid,
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
                                                JSONArray comentarios = r.getJSONObject("publicacion").getJSONArray("comentarios");
                                                for(int i = 0; i < comentarios.length(); i++) {
                                                    listItems.add(new com.example.orangepals.models.Comentario(
                                                            comentarios.getJSONObject(i).getInt("comentario_id"),
                                                            comentarios.getJSONObject(i).getInt("usuario_id"),
                                                            comentarios.getJSONObject(i).getString("username"),
                                                            comentarios.getJSONObject(i).getString("texto"),
                                                            comentarios.getJSONObject(i).getString("fecha"),
                                                            comentarios.getJSONObject(i).getInt("likes"),
                                                            comentarios.getJSONObject(i).getBoolean("es_mio"),
                                                            comentarios.getJSONObject(i).getBoolean("like_mio"),
                                                            comentarios.getJSONObject(i).getInt("publicacion_id")
                                                    ));
                                                }
                                                adaptador = new CommentAdapter(Publicacion.this, listItems);
                                                comItems.setAdapter(adaptador);
                                                TextoComentario.setText("");
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
                                Toast.makeText(Publicacion.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
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
                rq.add(peticion);
                break;
            case R.id.eliminar_publicacion:
                eliminarPublicacion(new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject respuesta;
                        String r;
                        try {
                            respuesta = new JSONObject(response.trim());
                            r = respuesta.getString("desc");
                            Toast.makeText(Publicacion.this, r, Toast.LENGTH_LONG).show();
                            Intent RegresarInterfaz = new Intent(Publicacion.this, Interfaz_Usuario.class);
                            startActivity(RegresarInterfaz);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }});
                break;
            case R.id.editar_publicacion:
                Intent EditarPublicacion = new Intent(Publicacion.this, ModificarPublicacion.class);
                EditarPublicacion.putExtra("id_publicacion", pub_id);
                startActivity(EditarPublicacion);
                break;
            case R.id.saved:
                if(guardado) {
                    saved.setBackgroundResource(R.drawable.bookmark);
                } else {
                    saved.setBackgroundResource(R.drawable.bookmark_s);
                }
                toggleBookmark(new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject respuesta;
                        String r;
                        try {
                            respuesta = new JSONObject(response.trim());
                            r = respuesta.getString("desc");
                            guardado = !guardado;
                            Toast.makeText(Publicacion.this, r, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }});
                break;
            case R.id.icon_like:
                if(liked) {
                    icon_like.setBackgroundResource(R.drawable.icon_like);
                } else {
                    icon_like.setBackgroundResource(R.drawable.icon_like_s);
                }
                toggleLike(new VolleyCallback() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(String response) {
                        JSONObject respuesta;
                        String r;
                        try {
                            respuesta = new JSONObject(response.trim());
                            r = respuesta.getString("desc");
                            if(liked)
                                pl.setText("" + (Integer.parseInt(pl.getText().toString()) - 1));
                            else
                                pl.setText("" + (Integer.parseInt(pl.getText().toString()) + 1));
                            liked = !liked;
                            Toast.makeText(Publicacion.this, r, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }});
                break;
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
                        Toast.makeText(Publicacion.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue rq = Volley.newRequestQueue(Publicacion.this);
        rq.add(sr);
    }

    public void eliminarPublicacion(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            udata = new JSONObject(preferences.getString("usrinfo", null));
            usrid = udata.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest sr = new StringRequest(Request.Method.GET, rh.URI + "/delete_publicacion/" + getIntent().getExtras().getInt("id_publicacion"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Publicacion.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue rq = Volley.newRequestQueue(Publicacion.this);
        rq.add(sr);
    }

    public void toggleBookmark(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            udata = new JSONObject(preferences.getString("usrinfo", null));
            usrid = udata.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest sr = new StringRequest(Request.Method.POST, rh.URI + "/toggle_bmark/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Publicacion.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("PUBLICACION_ID", String.valueOf(getIntent().getExtras().getInt("id_publicacion")));
                params.put("USUARIO_ID", usrid);
                params.put("MARCADO", guardado.toString());
                return params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(Publicacion.this);
        rq.add(sr);
    }

    public void toggleLike(final VolleyCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            udata = new JSONObject(preferences.getString("usrinfo", null));
            usrid = udata.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest sr = new StringRequest(Request.Method.POST, rh.URI + "/toggle_corazon/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Publicacion.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("PUBLICACION_ID", String.valueOf(getIntent().getExtras().getInt("id_publicacion")));
                params.put("USUARIO_ID", usrid);
                params.put("LIKED", liked.toString());
                return params;
            }
        };
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
