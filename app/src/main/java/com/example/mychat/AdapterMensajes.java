package com.example.mychat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.Timestamp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdapterMensajes  extends RecyclerView.Adapter<HolderMensaje> {
    private List<Mensaje> listMensaje = new ArrayList<>();
    private Context c;

    public AdapterMensajes(Context c) {
        this.c = c;
    }

    public void addMensaje(Mensaje m){
    listMensaje.add(m);
    notifyItemInserted(listMensaje.size());
    }


    @NonNull
    @Override
    public HolderMensaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes,parent,false);
        return new HolderMensaje(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMensaje holder, int position) {
        Mensaje mensaje = listMensaje.get(position);

        holder.getNombre().setText(listMensaje.get(position).getNombre());

        holder.getMensaje().setText(listMensaje.get(position).getMensaje());
        if(listMensaje.get(position).getType_mensaje().equals("2")){
            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.VISIBLE);
            Glide.with(c).load(listMensaje.get(position).getUrlFoto()).into(holder.getFotoMensaje());
        }else if(listMensaje.get(position).getType_mensaje().equals("1")){
            holder.getFotoMensaje().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return listMensaje.size();
    }


}
