package com.example.alkar.projetosa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alkar.projetosa.Firebase.Entidade;
import com.example.alkar.projetosa.TelaAdmin.UpdateDoacao;
import com.example.alkar.projetosa.TelaAdmin.UpdateEntidade;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class ListaEntidadeDoacao extends AppCompatActivity {

    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_entidade_doacao);


        RecyclerView rv = findViewById(R.id.recyclerview_lista_entidade_doacao);
        adapter = new GroupAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        dados();
    }

    private void dados() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionEntidade = db.collection("entidade");

        collectionEntidade.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot document: queryDocumentSnapshots) {
                    Entidade entidade = document.toObject(Entidade.class);
                    adapter.add(new EntidadeItem(entidade));
                }
            }
        });

    }

    public class EntidadeItem extends Item<ViewHolder> {

        private final Entidade entidade;

        public EntidadeItem(Entidade entidade) {
            this.entidade = entidade;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, final int position) {
            TextView nomeEntidadeUpdate  = viewHolder.itemView.findViewById(R.id.textNomeUpdateEntidade);
            ImageView img_photoUpdate    = viewHolder.itemView.findViewById(R.id.imageUpdateEntidade);
            CardView cardViewLista       = viewHolder.itemView.findViewById(R.id.cardViewEntidadeUpdate);

            cardViewLista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), UpdateDoacao.class);
                    intent.putExtra("nomeEntidade", entidade.getNome());
                    startActivity(intent);
                }
            });


            nomeEntidadeUpdate.setText(entidade.getNome());
            Picasso.get().load(entidade.getEntidadeUrl()).into(img_photoUpdate);
        }

        @Override
        public int getLayout() {
            return R.layout.cardview_lista_entidades;
        }
    }
}
