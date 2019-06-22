package com.example.alkar.projetosa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alkar.projetosa.Firebase.Softplayer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Participantes extends AppCompatDialogFragment {

    private GroupAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragparticipantes, null);

        RecyclerView rv = view.findViewById(R.id.mRecyclerParticipantes);
        adapter = new GroupAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        builder.setView(view)
                .setTitle("Participantes")
                .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        dados();

        return builder.create();
    }

    private void dados() {

        Intent intent = getActivity().getIntent();
        String nomeEntidade = intent.getStringExtra("nome");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionEntidades = db.collection("entidade");

        CollectionReference collectionDoacoes = collectionEntidades.document(nomeEntidade)
                .collection("Doações");


        collectionDoacoes.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Softplayer softplayer = document.toObject(Softplayer.class);
                    adapter.add(new SoftplayerItem(softplayer));
                }
            }
        });

    }


    public class SoftplayerItem extends Item<ViewHolder> {

        private final Softplayer softplayer;

        public SoftplayerItem(Softplayer softplayer) {
            this.softplayer = softplayer;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {

            TextView nome = viewHolder.itemView.findViewById(R.id.textParticipanteNome);
            ImageView img_photo = viewHolder.itemView.findViewById(R.id.imageParticipante);
            CardView cardViewParticipantes = viewHolder.itemView.findViewById(R.id.cardView_Participantes);

            cardViewParticipantes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TelaPerfil.class);
                    intent.putExtra("uuid", softplayer.getUuid());
                    startActivity(intent);
                }
            });


            nome.setText(softplayer.getNome());
            Picasso.get().load(softplayer.getProfileUrl()).into(img_photo);
        }

        @Override
        public int getLayout() {
            return R.layout.cardview_lista_participantes;
        }
    }

}
