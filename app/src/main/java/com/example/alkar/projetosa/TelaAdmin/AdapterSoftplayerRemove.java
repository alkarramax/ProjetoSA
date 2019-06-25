package com.example.alkar.projetosa.TelaAdmin;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alkar.projetosa.Firebase.Softplayer;
import com.example.alkar.projetosa.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class AdapterSoftplayerRemove extends FirestoreRecyclerAdapter<Softplayer, AdapterSoftplayerRemove.SoftplayerHolder> {

    public AdapterSoftplayerRemove(@NonNull FirestoreRecyclerOptions<Softplayer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SoftplayerHolder holder, int position, @NonNull Softplayer model) {
        holder.nome.setText(model.getNome());
        Picasso.get().load(model.getProfileUrl()).into(holder.imagem);
    }

    @NonNull
    @Override
    public SoftplayerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_lista_participantes, null);
        return new SoftplayerHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class SoftplayerHolder extends RecyclerView.ViewHolder {
        TextView nome;
        ImageView imagem;


        public SoftplayerHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textParticipanteNome);
            imagem = itemView.findViewById(R.id.imageParticipante);
        }
    }
}
