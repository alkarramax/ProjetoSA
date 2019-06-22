package com.example.alkar.projetosa.TelaAdmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alkar.projetosa.Firebase.Entidade;
import com.example.alkar.projetosa.ListaEntidade;
import com.example.alkar.projetosa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class RemoverDoacao extends AppCompatActivity {

    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover_doacao);

        RecyclerView rv = findViewById(R.id.recyclerview_doacao_remove);
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
            TextView nomeEntidadeUpdate  = viewHolder.itemView.findViewById(R.id.textNomeDoacaoRemove);
            ImageView img_photoUpdate    = viewHolder.itemView.findViewById(R.id.imageDoacaoRemove);
            Button removeDoacao          = viewHolder.itemView.findViewById(R.id.button_remover_Doacao);

            nomeEntidadeUpdate.setText(entidade.getNome());
            Picasso.get().load(entidade.getEntidadeUrl()).into(img_photoUpdate);

            removeDoacao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, Object> doacaoRemove = new HashMap<>();
                    doacaoRemove.put("tipo1", FieldValue.delete());
                    doacaoRemove.put("tipo2",  FieldValue.delete());
                    doacaoRemove.put("tipo3",  FieldValue.delete());
                    doacaoRemove.put("tipo4",  FieldValue.delete());
                    doacaoRemove.put("objetivo",  FieldValue.delete());
                    doacaoRemove.put("local",  FieldValue.delete());
                    doacaoRemove.put("hora",  FieldValue.delete());
                    doacaoRemove.put("data",  FieldValue.delete());

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("entidade").document(entidade.getNome())
                            .update(doacaoRemove)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(RemoverDoacao.this, "Doação removida", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });


        }

        @Override
        public int getLayout() {
            return R.layout.cardview_lista_doacoes;
        }
    }
}
