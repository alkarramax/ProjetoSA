package com.example.alkar.projetosa.TelaAdmin;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alkar.projetosa.Firebase.Doacao;
import com.example.alkar.projetosa.Firebase.Entidade;
import com.example.alkar.projetosa.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class CadastroDoacao extends AppCompatActivity {

    private TextInputLayout textInputTipo1;
    private TextInputLayout textInputTipo2;
    private TextInputLayout textInputTipo3;
    private TextInputLayout textInputTipo4;
    private TextInputLayout textInputNomeEntidade;
    private TextInputLayout textObjetivo;
    private TextInputLayout textlocal;
    private TextInputLayout textHora;
    private TextInputLayout textData;
    private Button cadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_doacao);

        textInputNomeEntidade = findViewById(R.id.txt_Nome_Entidade);
        textInputTipo1 = findViewById(R.id.txt_Tipo1);
        textInputTipo2 = findViewById(R.id.txt_Tipo2);
        textInputTipo3 = findViewById(R.id.txt_Tipo3);
        textInputTipo4 = findViewById(R.id.txt_Tipo4);
        textlocal = findViewById(R.id.txt_Local_Doa);
        textObjetivo = findViewById(R.id.txt_Obetivo_Doa);
        textHora = findViewById(R.id.txt_Hora_Doa);
        textData = findViewById(R.id.txt_Data_Doa);
        cadastrar = findViewById(R.id.button_Cadastro_Doa);


        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDoacao();
            }
        });

    }

    private void saveDoacao() {

        String nome = textInputNomeEntidade.getEditText().getText().toString().trim();
        String tipo1 = textInputTipo1.getEditText().getText().toString().trim();
        String tipo2 = textInputTipo2.getEditText().getText().toString().trim();
        String tipo3 = textInputTipo3.getEditText().getText().toString().trim();
        String tipo4 = textInputTipo4.getEditText().getText().toString().trim();
        String objetivo = textObjetivo.getEditText().getText().toString().trim();
        String local = textlocal.getEditText().getText().toString().trim();
        String hora = textHora.getEditText().getText().toString().trim();
        String data = textData.getEditText().getText().toString().trim();

        Doacao doacao = new Doacao(nome, tipo1, tipo2, tipo3, tipo4, objetivo, local, data, hora);

            FirebaseFirestore.getInstance().collection("doação")
                    .add(doacao)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(CadastroDoacao.this, "Deu certo" +documentReference.getId(), Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(CadastroDoacao.this, "Deu errado" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

    }


    public void voltarTelaAdm(View view){

        Intent intent = new Intent(this, TelaAdmin.class);
        startActivity(intent);
    }
}
