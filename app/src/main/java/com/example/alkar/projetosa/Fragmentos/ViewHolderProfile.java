package com.example.alkar.projetosa.Fragmentos;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alkar.projetosa.R;

public class ViewHolderProfile extends RecyclerView.ViewHolder {

    public TextView textViewNome, textViewCargo, textViewUnidade, textViewEmail;
    public ImageView imgPhoto;

    public ViewHolderProfile(@NonNull View itemView) {
        super(itemView);

        textViewNome = itemView.findViewById(R.id.textViewNome);
        textViewCargo = itemView.findViewById(R.id.textViewCargo);
        textViewUnidade = itemView.findViewById(R.id.textViewUnidade);
        imgPhoto = itemView.findViewById(R.id.imagemViewPerfil);





    }
}
