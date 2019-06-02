package com.example.alkar.projetosa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static android.graphics.BitmapFactory.decodeStream;

public class Home_Imagens_Detalhe extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__imagens_detalhe);


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