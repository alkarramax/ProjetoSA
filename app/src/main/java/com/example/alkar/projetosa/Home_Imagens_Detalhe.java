package com.example.alkar.projetosa;

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
import com.google.android.gms.tasks.Tasks;
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
import java.util.List;
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
        final String uuidUser = intent.getStringExtra("uuid");

        final CollectionReference collectionDoacoes = db.collection("entidade").document(nomeEntidade)
                .collection("Doações");

        Task queryEntidade = collectionEntidades.whereEqualTo("nome", nomeEntidade).get();
        Task queryDoacoes = collectionDoacoes.whereEqualTo("uuid", uuidUser).get();


        Task<List<QuerySnapshot>> allTask = Tasks.whenAllSuccess(queryEntidade, queryDoacoes);
        allTask.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
            @Override
            public void onSuccess(List<QuerySnapshot> querySnapshots) {
                for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Entidade entidade = document.toObject(Entidade.class);
                        Softplayer softplayer = document.toObject(Softplayer.class);
                        adapter.add(new EntidadeItem(entidade, softplayer));
                    }
                }
            }
        });


        /*
        Query query = collectionEntidades.whereEqualTo("nome", nomeEntidade);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Entidade entidade     = document.toObject(Entidade.class);
                    Softplayer softplayer = document.toObject(Softplayer.class);
                    adapter.add(new EntidadeItem(entidade, softplayer));
                }
            }
        });
        */

    }

    public class EntidadeItem extends Item<ViewHolder> {

        private final Entidade entidade;
        private final Softplayer softplayer;


        public EntidadeItem(Entidade entidade, Softplayer softplayer) {
            this.entidade = entidade;
            this.softplayer = softplayer;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {

            final TextView nomeEntidade = viewHolder.itemView.findViewById(R.id.txtTitle);
            TextView descricao = viewHolder.itemView.findViewById(R.id.txtDescri);
            TextView descricaoObj = viewHolder.itemView.findViewById(R.id.txtObj);
            TextView data = viewHolder.itemView.findViewById(R.id.txtData);
            TextView local = viewHolder.itemView.findViewById(R.id.textLocal);
            ImageView imageView = viewHolder.itemView.findViewById(R.id.bookthumbnail);
            Button imageButtonDoar = viewHolder.itemView.findViewById(R.id.imageButtonDoar);

            final CardView perfilSoft = viewHolder.itemView.findViewById(R.id.perfilSoftplayer);
            CardView perfilSoft2 = viewHolder.itemView.findViewById(R.id.perfilSoftplayer2);

            ImageView imagemPlayer = viewHolder.itemView.findViewById(R.id.imagemPlayer);
            ImageView imagemPlayer2 = viewHolder.itemView.findViewById(R.id.imagemPlayer2);

            nomeEntidade.setText(entidade.getNome());
            descricao.setText(entidade.getDescricao());
            Picasso.get().load(entidade.getEntidadeUrl()).into(imageView);
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
                            if(task.isSuccessful()) {
                                for(QueryDocumentSnapshot document : task.getResult()) {
                                    Softplayer softplayer   = document.toObject(Softplayer.class);

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

                                    String uuid     = FirebaseAuth.getInstance().getUid();
                                    String nome     = softplayer.getNome();
                                    String url      = softplayer.getProfileUrl();

                                    Softplayer softplayerDoacao = new Softplayer(uuid, nome, url);

                                    collectionReference1.document(nomeEntidade).collection("Doações")
                                            .document(FirebaseAuth.getInstance().getUid()).set(softplayerDoacao);

                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });


            Picasso.get().load(softplayer.getProfileUrl()).into(imagemPlayer);

            perfilSoft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), TelaPerfil.class);
                    intent.putExtra("uuid", FirebaseAuth.getInstance().getUid());
                    startActivity(intent);
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