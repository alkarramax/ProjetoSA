package com.example.alkar.projetosa.Fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.alkar.projetosa.HomeConfig.Book;
import com.example.alkar.projetosa.HomeConfig.RecyclerViewAdapter;
import com.example.alkar.projetosa.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    List<Book> lstBook;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        lstBook = new ArrayList<>();
        lstBook.add(new Book("Mãos Solidarias","Entidade","Descrição Entidade",R.drawable.maossolidarias));
        lstBook.add(new Book("Pascoa Solidaria","Entidade","Descrição Entidade",R.drawable.pascoasolidaria));
        lstBook.add(new Book("Mundo Soladario","Entidade","Descrição Entidade",R.drawable.mundosolidario));
        lstBook.add(new Book("Grupo Solidario","Entidade","Descrição Entidade",R.drawable.solidariomundo));
        lstBook.add(new Book("Geração Solidaria","Entidade","Descrição Entidade",R.drawable.geracaosolidaria));
        lstBook.add(new Book("Solidariedade","Entidade","Descrição Entidade",R.drawable.solidario));

        RecyclerView myrv = view.findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity(),lstBook);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        myrv.setAdapter(myAdapter);



        return view;
    }

}
