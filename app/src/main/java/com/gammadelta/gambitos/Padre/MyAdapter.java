package com.gammadelta.gambitos.Padre;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gammadelta.gambitos.R;
import com.gammadelta.gambitos.model.Hijos;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Hijos> myDataset;
    private ListItemClickListener listItemClickListener;

    public MyAdapter(ArrayList<Hijos> dataArrayList){
        myDataset = dataArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        return new ViewHolder(view, listItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.nombre.setText(myDataset.get(i).getNombre());
        viewHolder.fecha_nacimiento.setText(myDataset.get(i).getFecha_nacimiento());
        viewHolder.ultimaActualizacion.setText(myDataset.get(i).getUltimaActualizacion());
    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }

    public void setOnItemCliclListener(ListItemClickListener onItemCliclListener){
        this.listItemClickListener = onItemCliclListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ListItemClickListener listItemClickListener;
        private TextView nombre, fecha_nacimiento, ultimaActualizacion;

        public ViewHolder(@NonNull View itemView, ListItemClickListener listItemClickListener) {
            super(itemView);
            this.listItemClickListener = listItemClickListener;
            nombre = itemView.findViewById(R.id.nombre_hijo);
            fecha_nacimiento = itemView.findViewById(R.id.edad_hijo);
            ultimaActualizacion = itemView.findViewById(R.id.fecha_ultimoRegistro);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listItemClickListener!=null){
                listItemClickListener.onItemClick(getLayoutPosition(),v);
            }
        }
    }

    public interface ListItemClickListener{
        void onItemClick(int position, View v);
    }
}
