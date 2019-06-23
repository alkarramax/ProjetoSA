package com.example.alkar.projetosa.Fragmentos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.alkar.projetosa.R;

public class SobreFragment extends Fragment {


    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sobre, container, false);

        button = view.findViewById(R.id.button_link);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.softplan.com.br/blog/noticia/campanha-do-agasalho-solidario-2016-e-um-sucesso/"));
                startActivity(intent);
            }
        });
        return view;
    }

}
