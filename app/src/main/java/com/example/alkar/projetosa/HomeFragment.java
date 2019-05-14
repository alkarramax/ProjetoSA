package com.example.alkar.projetosa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class HomeFragment extends Fragment implements View.OnClickListener {





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton btn_sair = view.findViewById(R.id.btn_sair);
        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sair = new Intent(getActivity(), MainActivity.class);
                startActivity(sair);
            }
        });

        CardView imagem1 = view.findViewById(R.id.image1);
        imagem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teste = new Intent(getActivity(), Home_imagem1.class);
                startActivity(teste);
            }
        });



        return view;
    }

    public void Entrar(View view) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), Home.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
        }
    }
}
