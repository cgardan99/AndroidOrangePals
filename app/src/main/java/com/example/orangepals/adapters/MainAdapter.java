package com.example.orangepals.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.orangepals.R;
import com.example.orangepals.models.Publicacion;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter  {
    private Context context;
    private ArrayList<Publicacion> listItems;

    public MainAdapter(Context context, ArrayList<Publicacion> listItems) {
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
        final Publicacion item = (Publicacion) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item_publicacion, null);
        TextView pubTitle = (TextView) convertView.findViewById(R.id.title);
        TextView pubContent = (TextView) convertView.findViewById(R.id.content);
        TextView pubLikes = (TextView) convertView.findViewById(R.id.likes);
        TextView pubComents = (TextView) convertView.findViewById(R.id.comments);


        pubTitle.setText(item.getTitulo());
        pubContent.setText(item.getDescripcion());
        pubLikes.setText(item.getCorazones().toString());
        pubComents.setText(item.getComentarios().toString());



        pubTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IrPublicacion1 = new Intent(v.getContext(), com.example.orangepals.Publicacion.class);
                IrPublicacion1.putExtra("id_publicacion", item.getId_publicacion());
                context.startActivity(IrPublicacion1);
            }
        });

        return convertView;
    }
}
