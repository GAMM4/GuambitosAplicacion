package com.gammadelta.gambitos.Padre;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gammadelta.gambitos.R;

import java.util.List;

/*public class HijosAdapterRecyclerView extends RecyclerView.Adapter<HijosAdapterRecyclerView.HijosView> {

    private String[] hijosDatos;

    public static class HijosView extends RecyclerView.ViewHolder{
        public TextView nombre_hijo;
        public TextView edad_hijo;
        public TextView fecha_ultimoRegistro;

        public HijosView(@NonNull TextView datos) {
            super(datos);

            nombre_hijo = datos;
            edad_hijo = datos;
            fecha_ultimoRegistro = datos;

            /*nombre_hijo             = (TextView) itemView.findViewById(R.id.nombre_hijo);
            edad_hijo               = (TextView) itemView.findViewById(R.id.edad_hijo);
            fecha_ultimoRegistro    = (TextView) itemView.findViewById(R.id.fecha_ultimoRegistro);*/
       /* }
    }

    public HijosAdapterRecyclerView(String[] hijosDatos) {
        this.hijosDatos = hijosDatos;
    }

    @Override
    public HijosAdapterRecyclerView.HijosView onCreateViewHolder(ViewGroup parent, int viewType){

        TextView datos = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        HijosView vista = new HijosView(datos);

        return vista;
    }

    @Override
    public void onBindViewHolder(@NonNull HijosAdapterRecyclerView.HijosView holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.nombre_hijo.setText(hijosDatos[0]);
        holder.edad_hijo.setText(hijosDatos[1]);
        holder.fecha_ultimoRegistro.setText(hijosDatos[2]);
    }

    @Override
    public int getItemCount() {
        return hijosDatos.length;
    }
}

    /*class ElementosHijo{

    private String nombreHijo;
    private String edadHijo;
    private String ultimoRegistro = "-- / -- / --";

    public ElementosHijo(String nombreHijo, String edadHijo, String ultimoRegistro) {
        this.nombreHijo = nombreHijo;
        this.edadHijo = edadHijo;
        this.ultimoRegistro = ultimoRegistro;
    }

    public String getNombreHijo() {
        return nombreHijo;
    }

    public void setNombreHijo(String nombreHijo) {
        this.nombreHijo = nombreHijo;
    }

    public String getEdadHijo() {
        return edadHijo;
    }

    public void setEdadHijo(String edadHijo) {
        this.edadHijo = edadHijo;
    }

    public String getUltimoRegistro() {
        return ultimoRegistro;
    }

    public void setUltimoRegistro(String ultimoRegistro) {
        this.ultimoRegistro = ultimoRegistro;
    }
}*/
