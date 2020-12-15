package com.example.orangepals.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.orangepals.R;
import com.example.orangepals.models.Comentario;
import com.example.orangepals.models.Publicacion;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Comentario> listItems;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comentario item = (Comentario) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item_comentario, null);
        TextView comNombre = (TextView) convertView.findViewById(R.id.cnombre);
        TextView comFecha = (TextView) convertView.findViewById(R.id.cfecha);
        TextView comTexto = (TextView) convertView.findViewById(R.id.ctexto);
        TextView comLikes = (TextView) convertView.findViewById(R.id.clikes);

        comNombre.setText(item.getUsername());
        comFecha.setText(item.getFecha().toString());
        comTexto.setText(item.getTexto());
        comLikes.setText(item.getLikes().toString());

        //pubTitle.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Intent IrPublicacion1 = new Intent(v.getContext(), com.example.orangepals.Publicacion.class);
        //        context.startActivity(IrPublicacion1);
        //    }
        //});

        return convertView;
    }
}
