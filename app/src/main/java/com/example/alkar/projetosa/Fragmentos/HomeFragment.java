package com.example.alkar.projetosa.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alkar.projetosa.Firebase.Doacao;
import com.example.alkar.projetosa.Firebase.Entidade;
import com.example.alkar.projetosa.Home_Imagens_Detalhe;
import com.example.alkar.projetosa.R;
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

public class HomeFragment extends Fragment {

    public static final String NOME_TEXT = "nome";

    private Context mContext;
    private GroupAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rv = view.findViewById(R.id.recyclerview_id);
        adapter = new GroupAdapter<>();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        fetchUsers();

        return view;
    }

    private void fetchUsers() {
        FirebaseFirestore.getInstance().collection("entidade")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null) {
                            Log.e("Teste", e.getMessage());
                            return;
                        }

                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot doc : docs){
                            Entidade entidade = doc.toObject(Entidade.class);
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
            final TextView nomeEntidade  = viewHolder.itemView.findViewById(R.id.nome_entidade_id);
            ImageView img_photo    = viewHolder.itemView.findViewById(R.id.entidade_img_id);
            TextView descEntidade = viewHolder.itemView.findViewById(R.id.descricao_entidade);
            CardView cardView      = viewHolder.itemView.findViewById(R.id.cardview_id);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Home_Imagens_Detalhe.class);
                    startActivity(intent);
                }
            });

            nomeEntidade.setText(entidade.getNome());
            descEntidade.setText(entidade.getDescricao());

            Picasso.get()
                    .load(entidade.getEntidadeUrl())
                    .into(img_photo);
        }

        @Override
        public int getLayout() {
            return R.layout.cardview_home;
        }
    }
}
