package com.example.orangepals.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orangepals.EditarComentario;
import com.example.orangepals.Interfaz_Usuario;
import com.example.orangepals.Publicacion;
import com.example.orangepals.R;
import com.example.orangepals.RequestsHelp;
import com.example.orangepals.UserBookmarks;
import com.example.orangepals.interfaces.VolleyCallback;
import com.example.orangepals.models.Comentario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Comentario> listItems;
    private JSONObject udata;
    private String usrid;
    private RequestsHelp rh = new RequestsHelp();

    public CommentAdapter(Context context, ArrayList<Comentario> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return this.listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Comentario item = (Comentario) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item_comentario, null);
        TextView comNombre = (TextView) convertView.findViewById(R.id.cnombre);
        TextView comFecha = (TextView) convertView.findViewById(R.id.cfecha);
        TextView comTexto = (TextView) convertView.findViewById(R.id.ctexto);
        final TextView comLikes = (TextView) convertView.findViewById(R.id.clikes);
        final Button comBoton = (Button) convertView.findViewById(R.id.comLike);
        Button cm_edit = (Button) convertView.findViewById(R.id.cm_edit);
        Button cm_delete = (Button) convertView.findViewById(R.id.cm_delete);

        comNombre.setText(item.getUsername());
        comFecha.setText(item.getFecha().toString());
        comTexto.setText(item.getTexto());
        comLikes.setText(item.getLikes().toString());

        if(item.isLike_mio())
            comBoton.setBackgroundResource(R.drawable.icon_like_s);
        else
            comBoton.setBackgroundResource(R.drawable.icon_like);

        if(item.isEs_mio()) {
            cm_edit.setVisibility(View.VISIBLE);
            cm_delete.setVisibility(View.VISIBLE);
        }

        comBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                likeComentario(new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject r;
                        try {
                            r = new JSONObject(response.trim());
                            if(item.isLike_mio()) {
                                comBoton.setBackgroundResource(R.drawable.icon_like);
                                comLikes.setText("" + (Integer.parseInt(comLikes.getText().toString()) - 1));
                            }
                            else {
                                comBoton.setBackgroundResource(R.drawable.icon_like_s);
                                comLikes.setText("" + (Integer.parseInt(comLikes.getText().toString()) + 1));
                            }
                            item.setLike_mio(!item.isLike_mio());
                            Toast.makeText(context, r.getString("desc"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }}, item.getComentario_id(), item.isLike_mio());
            }
        });

        cm_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IrPublicacion = new Intent(v.getContext(), EditarComentario.class);
                IrPublicacion.putExtra("id_publicacion", item.getPid());
                IrPublicacion.putExtra("id_comentario", item.getComentario_id());
                context.startActivity(IrPublicacion);
            }
        });

        cm_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                eliminarComentario(new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject r;
                        try {
                            r = new JSONObject(response.trim());
                            Toast.makeText(context, r.getString("desc"), Toast.LENGTH_LONG).show();
                            Intent IrPublicacion = new Intent(v.getContext(), com.example.orangepals.Publicacion.class);
                            IrPublicacion.putExtra("id_publicacion", item.getPid());
                            context.startActivity(IrPublicacion);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }}, item.getComentario_id());

            }
        });

        return convertView;
    }

    public void eliminarComentario(final VolleyCallback callback, Integer cm_id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        try {
            udata = new JSONObject(preferences.getString("usrinfo", null));
            usrid = udata.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest sr = new StringRequest(Request.Method.GET, rh.URI + "/delete_comentario/" + cm_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue rq = Volley.newRequestQueue(context);
        rq.add(sr);
    }

    public void likeComentario(final VolleyCallback callback, final Integer cm_id, final Boolean es_mio) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        try {
            udata = new JSONObject(preferences.getString("usrinfo", null));
            usrid = udata.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest sr = new StringRequest(Request.Method.POST, rh.URI + "/toggle_corazon_cm/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("USUARIO_ID", usrid);
                params.put("COMENTARIO_ID", String.valueOf(cm_id));
                params.put("LIKED", String.valueOf(es_mio));
                return params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(context);
        rq.add(sr);
    }
}
