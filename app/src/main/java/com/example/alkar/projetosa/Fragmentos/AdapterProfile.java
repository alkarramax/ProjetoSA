package com.example.alkar.projetosa.Fragmentos;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alkar.projetosa.Firebase.Softplayer;
import com.example.alkar.projetosa.R;

import java.util.ArrayList;

public class AdapterProfile extends RecyclerView.Adapter<ViewHolderProfile> {

    ProfileFragment profileFragment;
    ArrayList<Softplayer> softplayerArrayList;

    public AdapterProfile(ProfileFragment profileFragment, ArrayList<Softplayer> softplayerArrayList) {
        this.profileFragment = profileFragment;
        this.softplayerArrayList = softplayerArrayList;
    }

    @NonNull
    @Override
    public ViewHolderProfile onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(profileFragment.getContext());
        View view = layoutInflater.inflate(R.layout.item_profile, viewGroup, false);

        return new ViewHolderProfile(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProfile viewHolderProfile, int i) {

        viewHolderProfile.textViewNome.setText(softplayerArrayList.get(i).getNome());
        viewHolderProfile.textViewCargo.setText(softplayerArrayList.get(i).getCargo());
        viewHolderProfile.textViewUnidade.setText(softplayerArrayList.get(i).getUnidade());

    }

    @Override
    public int getItemCount() {
        return softplayerArrayList.size();
    }
}
