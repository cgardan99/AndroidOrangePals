package com.example.orangepals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
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

import java.util.ArrayList;

public class UserBookmarks extends AppCompatActivity {

    private JSONObject udata;
    private String usrid;
    private RequestsHelp rh = new RequestsHelp();
    private ListView pubItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_bookmarks);

        pubItems = (ListView) findViewById(R.id.misPublicaciones);
        final ArrayList<com.example.orangepals.models.Publicacion> listItems = new ArrayList<>();

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
                    MainAdapter adaptador = new MainAdapter(UserBookmarks.this, listItems);
                    pubItems.setAdapter(adaptador);

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
        StringRequest sr = new StringRequest(Request.Method.GET, rh.URI + "/bmarks/" + this.usrid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserBookmarks.this, "Error obteniendo las publicaciones.", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue rq = Volley.newRequestQueue(UserBookmarks.this);
        rq.add(sr);
    }
}