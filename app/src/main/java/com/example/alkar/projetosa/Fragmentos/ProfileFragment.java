package com.example.alkar.projetosa.Fragmentos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alkar.projetosa.Firebase.Softplayer;
import com.example.alkar.projetosa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import java.util.List;

public class ProfileFragment extends Fragment {

    private GroupAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        RecyclerView rv = view.findViewById(R.id.recyclerview_profile);
        adapter = new GroupAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        fetchUsers();
        return view;
    }


    private void fetchUsers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("softplayers");

        Query querySoftplayers = collectionReference.whereEqualTo("uuid", FirebaseAuth.getInstance().getUid());

        querySoftplayers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Softplayer softplayer = document.toObject(Softplayer.class);
                        adapter.add(new SoftplayerItem(softplayer));
                    }
                } else {
                    Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
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

            TextView nome = viewHolder.itemView.findViewById(R.id.textViewNome);
            TextView cargo = viewHolder.itemView.findViewById(R.id.textViewCargo);
            TextView unidade = viewHolder.itemView.findViewById(R.id.textViewUnidade);
            TextView email = viewHolder.itemView.findViewById(R.id.textViewEmail);
            ImageView img_photo = viewHolder.itemView.findViewById(R.id.imagemViewPerfil);
            TextView contador = viewHolder.itemView.findViewById(R.id.contadorDoacao);

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
            return R.layout.cardview_profile;
        }
    }
}