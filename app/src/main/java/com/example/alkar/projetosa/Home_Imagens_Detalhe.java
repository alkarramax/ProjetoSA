package com.example.alkar.projetosa;

import android.annotation.SuppressLint;
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
import com.example.alkar.projetosa.Firebase.Softplayer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import static android.graphics.BitmapFactory.decodeStream;

public class Home_Imagens_Detalhe extends AppCompatActivity {

    private GroupAdapter adapter;

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionEntidades = db.collection("entidade");

        Intent intent = getIntent();
        String nomeEntidade = intent.getStringExtra("nome");

        Query query = collectionEntidades.whereEqualTo("nome", nomeEntidade);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
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
        public void bind(@NonNull final ViewHolder viewHolder, int position) {

            final TextView nomeEntidade = viewHolder.itemView.findViewById(R.id.txtTitle);
            TextView descricao = viewHolder.itemView.findViewById(R.id.txtDescri);
            TextView descricaoObj = viewHolder.itemView.findViewById(R.id.txtObj);
            TextView data = viewHolder.itemView.findViewById(R.id.txtData);
            TextView local = viewHolder.itemView.findViewById(R.id.textLocal);

            ImageView imagemEntidade = viewHolder.itemView.findViewById(R.id.bookthumbnail);



            Button imageButtonDoar = viewHolder.itemView.findViewById(R.id.imageButtonDoar);

            nomeEntidade.setText(entidade.getNome());
            descricao.setText(entidade.getDescricao());
            Picasso.get().load(entidade.getEntidadeUrl()).into(imagemEntidade);
            descricaoObj.setText(entidade.getObjetivo());
            data.setText(entidade.getData());
            local.setText(entidade.getLocal());

            imageButtonDoar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    final String nomeEntidade = intent.getStringExtra("nome");

                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    final CollectionReference collectionReference = db.collection("softplayers");
                    final CollectionReference collectionReference1 = db.collection("entidade");

                    Query querySoftplayers = collectionReference.whereEqualTo("uuid", FirebaseAuth.getInstance().getUid());
                    querySoftplayers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Softplayer softplayer = document.toObject(Softplayer.class);

                                    int contador = softplayer.getContador();
                                    contador += 1;

                                    Map<String, Object> Contador = new HashMap<>();
                                    Contador.put("contador", contador);


                                    collectionReference.document(FirebaseAuth.getInstance().getUid()).update(Contador)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(Home_Imagens_Detalhe.this, "Doação feita", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    String uuid = FirebaseAuth.getInstance().getUid();
                                    String nome = softplayer.getNome();
                                    String url = softplayer.getProfileUrl();

                                    Softplayer softplayerDoacao = new Softplayer(uuid, nome, url);

                                     collectionReference1.document(nomeEntidade).collection("Doações")
                                             .document(softplayer.getNome()).set(softplayerDoacao);
                                }
                            }
                        }
                    });
                }
            });


            Intent intent = getIntent();
            String nomeEntidadeTe = intent.getStringExtra("nome");

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference collectionDoacoes = db.collection("entidade").document(nomeEntidadeTe).collection("Doações");
            collectionDoacoes.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        Softplayer softplayer = queryDocumentSnapshot.toObject(Softplayer.class);

                        final ImageView fotoPerfil = viewHolder.itemView.findViewById(R.id.imagePerfil);
                        final ImageView fotoPerfil2 = viewHolder.itemView.findViewById(R.id.imagePerfil2);

                        Picasso.get().load(softplayer.getProfileUrl()).into(fotoPerfil);
                        Picasso.get().load(softplayer.getProfileUrl()).into(fotoPerfil2);

                    }
                }
            });
        }

        @Override
        public int getLayout() {
            return R.layout.cardview_home_detalhes;
        }

    }

    public void voltarTela (View view) {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
    }
}