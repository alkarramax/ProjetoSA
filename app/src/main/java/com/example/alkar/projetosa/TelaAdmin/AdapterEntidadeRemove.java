package com.example.alkar.projetosa.TelaAdmin;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alkar.projetosa.Firebase.Entidade;
import com.example.alkar.projetosa.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class AdapterEntidadeRemove extends FirestoreRecyclerAdapter<Entidade, AdapterEntidadeRemove.EntidadeHolder> {

    public AdapterEntidadeRemove(@NonNull FirestoreRecyclerOptions<Entidade> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EntidadeHolder holder, int position, @NonNull Entidade model) {
        holder.textNomeRemoveEntidade.setText(String.valueOf(model.getNome()));
        Picasso.get().load(model.getEntidadeUrl()).into(holder.imageRemoveEntidade);
    }

    @NonNull
    @Override
    public EntidadeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_lista_entidade_remove, null);
        return new EntidadeHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class EntidadeHolder extends RecyclerView.ViewHolder {

        TextView textNomeRemoveEntidade;
        ImageView imageRemoveEntidade;

        public EntidadeHolder(@NonNull View itemView) {
            super(itemView);

            textNomeRemoveEntidade = itemView.findViewById(R.id.textNomeRemoveEntidade);
            imageRemoveEntidade = itemView.findViewById(R.id.imageRemoveEntidade);
        }
    }
}
