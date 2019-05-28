package com.example.alkar.projetosa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Home_Imagens_Detalhe extends AppCompatActivity {

    private TextView nomeEntidade, descricao;
    private ImageView entidadeURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__imagens__detalhe);

        nomeEntidade = findViewById(R.id.txtTitle);
        entidadeURL = findViewById(R.id.bookthumbnail);

        Intent intent = getIntent();
        String Entidade = intent.getExtras().getString("nomeEntidade");
        int imagem = intent.getExtras().getInt("image");


        nomeEntidade.setText(Entidade);
        entidadeURL.setImageResource(imagem);

    }
    public void voltarTela(View view){

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void proximaTelaDoar(View view){

        Intent intent = new Intent(this, TelaDoacao.class);
        startActivity(intent);
    }
}