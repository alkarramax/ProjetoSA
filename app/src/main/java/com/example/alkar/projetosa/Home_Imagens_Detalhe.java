package com.example.alkar.projetosa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import org.w3c.dom.Text;

public class Home_Imagens_Detalhe extends AppCompatActivity {

    private TextView tvtitle, tvdescricao;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__imagens__detalhe);

        tvtitle = findViewById(R.id.txtTitle);
        tvdescricao = findViewById(R.id.txtDescri);
        img = findViewById(R.id.bookthumbnail);

        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Descricao = intent.getExtras().getString("Descrição");
        int image = intent.getExtras().getInt("Thumbnail");

        tvtitle.setText(Title);
        tvdescricao.setText(Descricao);
        img.setImageResource(image);

    }
    public void voltarTela(View view){

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void proximaTelaDoar(View view){

        Intent intent = new Intent(this,Doacao.class);
        startActivity(intent);
    }
}