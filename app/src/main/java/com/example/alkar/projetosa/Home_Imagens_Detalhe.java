package com.example.alkar.projetosa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alkar.projetosa.Firebase.Doacao;
import com.example.alkar.projetosa.Firebase.Entidade;
import com.example.alkar.projetosa.Fragmentos.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import org.w3c.dom.Text;

import javax.annotation.Nullable;

import static android.graphics.BitmapFactory.decodeStream;

public class Home_Imagens_Detalhe extends AppCompatActivity {

    private GroupAdapter adapter;
    private Entidade entidade;

    FirebaseFirestore db;
    CollectionReference entidadeRef = db.collection("entidade");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__imagens_detalhe);

        RecyclerView rv = findViewById(R.id.recyclerview_home_detalhes);
        adapter = new GroupAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        dados();

    }

    private void dados() {
        Query queryEntidades = entidadeRef.whereEqualTo("nome", entidade.getNome());

        queryEntidades.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot document : task.getResult()) {

                        Entidade entidade = document.toObject(Entidade.class);
                        Doacao doacao = document.toObject(Doacao.class);

                        adapter.add(new EntidadeItem(entidade));
                        adapter.add(new DoacaoItem(doacao));
                    }
                } else  {
                    Toast.makeText(Home_Imagens_Detalhe.this, "Error: " + task.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public class DoacaoItem extends Item<ViewHolder> {

        private final Doacao doacao;

        public DoacaoItem(Doacao doacao) {
            this.doacao = doacao;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView descricao = viewHolder.itemView.findViewById(R.id.txtDescri);
            TextView data = viewHolder.itemView.findViewById(R.id.txtData);
            TextView local = viewHolder.itemView.findViewById(R.id.textLocal);

            descricao.setText(doacao.getObjetivo());
            data.setText(doacao.getData());
            local.setText(doacao.getLocal());

        }

        @Override
        public int getLayout() {
            return R.layout.cardview_home_detalhes;
        }
    }

    public class EntidadeItem extends Item<ViewHolder> {

        private final Entidade entidade;

        public EntidadeItem(Entidade entidade) {
            this.entidade = entidade;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView nomeEntidade = viewHolder.itemView.findViewById(R.id.txtTitle);
            TextView descricao = viewHolder.itemView.findViewById(R.id.txtDescri);
            ImageView imageView = viewHolder.itemView.findViewById(R.id.bookthumbnail);

            nomeEntidade.setText(entidade.getNome());
            descricao.setText(entidade.getDescricao());

            Picasso.get()
                    .load(entidade.getEntidadeUrl())
                    .into(imageView);
        }

        @Override
        public int getLayout() {
            return R.layout.cardview_home_detalhes;
        }
    }

    public void voltarTela(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}