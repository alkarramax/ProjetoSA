package com.example.alkar.projetosa.Fragmentos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private GroupAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        RecyclerView rv = view.findViewById(R.id.rv_profile);
        adapter = new GroupAdapter();
        rv.setAdapter(adapter);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseFirestore.getInstance().collection("softplayers")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null) {
                            Toast.makeText(getActivity(), "Teste" +e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot doc : docs) {
                            Softplayer softplayer = doc.toObject(Softplayer.class);
                            adapter.add(new UserItem(softplayer));
                        }
                    }
                });

    }

    private class UserItem extends Item<ViewHolder> {

        private Softplayer softplayer;

        private UserItem(Softplayer softplayer) {
            this.softplayer = softplayer;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView    txtNome    = viewHolder.itemView.findViewById(R.id.textViewNome);
            TextView    txtCargo   = viewHolder.itemView.findViewById(R.id.textViewCargo);
            TextView    txtUnidade = viewHolder.itemView.findViewById(R.id.textViewUnidade);
            TextView    txtEmail   = viewHolder.itemView.findViewById(R.id.textViewEmail);
            ImageView   imgPhoto   = viewHolder.itemView.findViewById(R.id.imagemViewPerfil);

            txtNome.setText(softplayer.getNome());
            txtCargo.setText(softplayer.getCargo());
            txtUnidade.setText(softplayer.getUnidade());
            txtEmail.setText(softplayer.getEmail());

            Picasso.get()
                    .load(softplayer.getProfileUrl())
                    .into(imgPhoto);

        }

        @Override
        public int getLayout() {
            return R.layout.item_profile;
        }
    }
}