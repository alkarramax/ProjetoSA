package com.example.alkar.projetosa.TelaAdmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.alkar.projetosa.ListaEntidade;
import com.example.alkar.projetosa.ListaEntidadeDoacao;
import com.example.alkar.projetosa.LoginMain;
import com.example.alkar.projetosa.R;

public class TelaAdmin extends AppCompatActivity {

    private Button imgButtonEntidade;
    private Button Button_Atualizar_Entidade;
    private Button Button_Update_Doacao;

    private Button Button_Remover_Entidade;
    private Button Button_Remover_Membro;
    private Button Button_Remover_Doacao;

    private Button Button_Cadastrar_Doacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_admin);

        imgButtonEntidade = findViewById(R.id.imgButton_Cadastrar_Entidade);
        Button_Atualizar_Entidade = findViewById(R.id.Button_Atualizar_Entidade);
        Button_Update_Doacao = findViewById(R.id.Button_Update_Doacao);

        Button_Remover_Entidade = findViewById(R.id.Button_Remover_Entidade);
        Button_Remover_Membro = findViewById(R.id.Button_Remover_Membro);
        Button_Remover_Doacao = findViewById(R.id.Button_Remover_Doacao);

        Button_Cadastrar_Doacao = findViewById(R.id.imgButton_Cadastrar_Doacao);



        Button_Cadastrar_Doacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastroDoacao();
            }
        });
        imgButtonEntidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastroEntidade();
            }
        });
        Button_Update_Doacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDoacao();
            }
        });
        Button_Remover_Entidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerEntidade();
            }
        });
        Button_Atualizar_Entidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEntidade();
            }
        });
        Button_Remover_Membro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeEntidade();
            }
        });
        Button_Remover_Doacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDoacao();
            }
        });

    }

    private void removeDoacao() {
        Intent intent = new Intent(getApplicationContext(), RemoverDoacao.class);
        startActivity(intent);
    }

    private void updateDoacao() {
        Intent intent = new Intent(getApplicationContext(), ListaEntidadeDoacao.class);
        startActivity(intent);
    }

    private void removeEntidade() {
        Intent intent = new Intent(getApplicationContext(), RemoverMembro.class);
        startActivity(intent);
    }

    private void updateEntidade() {
        Intent intent = new Intent(getApplicationContext(), ListaEntidade.class);
        startActivity(intent);
    }

    private void removerEntidade() {
        Intent intent = new Intent(getApplicationContext(), RemoverEntidade.class);
        startActivity(intent);
    }

    private void cadastroEntidade() {
        Intent intent = new Intent(getApplicationContext(), Cadastro_Entidade.class);
        startActivity(intent);

    }
    private void cadastroDoacao() {
        Intent intent = new Intent(getApplicationContext(), CadastroDoacao.class);
        startActivity(intent);

    }

    public void voltarMain(View view) {
        Intent i = new Intent(getApplicationContext(), LoginMain.class);
        startActivity(i);
    }
}
