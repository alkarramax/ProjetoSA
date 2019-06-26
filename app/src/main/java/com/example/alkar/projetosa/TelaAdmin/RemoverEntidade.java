package com.example.alkar.projetosa.TelaAdmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alkar.projetosa.Firebase.Entidade;
import com.example.alkar.projetosa.ListaEntidade;
import com.example.alkar.projetosa.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class RemoverEntidade extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AdapterEntidadeRemove adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover_entidade);


        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        CollectionReference entidadeRef = db.collection("entidade");

        FirestoreRecyclerOptions<Entidade> options = new FirestoreRecyclerOptions.Builder<Entidade>()
                .setQuery(entidadeRef, Entidade.class)
                .build();

        adapter = new AdapterEntidadeRemove(options);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_remover_entidade);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1 ));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void voltarMain(View view) {
        Intent intent = new Intent(getApplicationContext(), TelaAdmin.class);
        startActivity(intent);

    }
}
