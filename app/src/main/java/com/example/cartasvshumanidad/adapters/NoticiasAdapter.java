package com.example.cartasvshumanidad.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartasvshumanidad.R;
import com.example.cartasvshumanidad.models.Noticias;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoticiasAdapter extends FirestoreRecyclerAdapter<Noticias, NoticiasAdapter.ViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NoticiasAdapter(@NonNull FirestoreRecyclerOptions<Noticias> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Noticias Noticias) {
    viewHolder.descripcion.setText(Noticias.getDescripcion());
    viewHolder.fechaPublicacion.setText(Noticias.getFechaPublicacion());
    viewHolder.titulo.setText(Noticias.getTitulo());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_noticia_single, parent,false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView descripcion, fechaPublicacion, titulo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            descripcion = itemView.findViewById(R.id.textDescripcion);
            fechaPublicacion = itemView.findViewById(R.id.textFechaPublicacion);
            titulo = itemView.findViewById(R.id.textTituloCard);
        }
    }
}
