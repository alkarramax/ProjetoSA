package com.example.alkar.projetosa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alkar.projetosa.Firebase.Softplayer;
import com.example.alkar.projetosa.Fragmentos.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
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

public class TelaPerfil extends AppCompatActivity {

    private GroupAdapter adapter;
    ImageButton voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);

        RecyclerView rv = findViewById(R.id.recyclerview_entidade_perfil);
        adapter = new GroupAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        dados();
    }

    private void dados() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("softplayers");

        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");

        Query querySoftplayers = collectionReference.whereEqualTo("uuid", uuid);

        querySoftplayers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Softplayer softplayer = document.toObject(Softplayer.class);
                        adapter.add(new SoftplayerItem(softplayer));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
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

            TextView nome           = viewHolder.itemView.findViewById(R.id.textEntidadePerfilNome);
            TextView cargo          = viewHolder.itemView.findViewById(R.id.textEntidadePerfilCargo);
            TextView unidade        = viewHolder.itemView.findViewById(R.id.textEntidadePerfilUnidade);
            TextView email          = viewHolder.itemView.findViewById(R.id.textEntidadePerfilEmail);
            ImageView img_photo     = viewHolder.itemView.findViewById(R.id.imagemEntidadePerfil);
            TextView contador       = viewHolder.itemView.findViewById(R.id.contadorDoacaoPerfil);
            ImageButton arrowBack   = viewHolder.itemView.findViewById(R.id.imageVoltar);

            arrowBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                }
            });

            nome.setText(softplayer.getNome());
            cargo.setText(softplayer.getCargo());
            unidade.setText(softplayer.getUnidade());
            email.setText(softplayer.getEmail());
            contador.setText(String.valueOf(softplayer.getContador()));

            Picasso.get()
                    .load(softplayer.getProfileUrl())
                    .into(img_photo);
        }

        @Override
        public int getLayout() {
            return R.layout.cardview_entidade_perfil;
        }
    }
}
