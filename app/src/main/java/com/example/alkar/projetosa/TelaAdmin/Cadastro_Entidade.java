package com.example.alkar.projetosa.TelaAdmin;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.alkar.projetosa.R;

public class Cadastro_Entidade extends AppCompatActivity {

    private TextInputLayout textInputNomeEntidade;
    private TextInputLayout textInputObjetivoEntidade;
    private TextInputLayout textInputDescricao;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro__entidade);

        textInputNomeEntidade = findViewById(R.id.textInputNomeEntidade);
        textInputObjetivoEntidade = findViewById(R.id.textInputObjetivoEntidade);
        textInputDescricao = findViewById(R.id.textInputDescricao);

    }



}
