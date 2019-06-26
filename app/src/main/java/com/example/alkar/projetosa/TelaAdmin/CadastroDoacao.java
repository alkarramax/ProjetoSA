package com.example.alkar.projetosa.TelaAdmin;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alkar.projetosa.Firebase.Doacao;
import com.example.alkar.projetosa.Firebase.Entidade;
import com.example.alkar.projetosa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

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

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


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
                String nome = textInputNomeEntidade.getEditText().getText().toString().trim();
                String tipo1 = textInputTipo1.getEditText().getText().toString().trim();
                String tipo2 = textInputTipo2.getEditText().getText().toString().trim();
                String tipo3 = textInputTipo3.getEditText().getText().toString().trim();
                String tipo4 = textInputTipo4.getEditText().getText().toString().trim();
                String objetivo = textObjetivo.getEditText().getText().toString().trim();
                String local = textlocal.getEditText().getText().toString().trim();
                String hora = textHora.getEditText().getText().toString().trim();
                String data = textData.getEditText().getText().toString().trim();

                Map<String, Object> Doacao = new HashMap<>();
                Doacao.put("tipo1", tipo1);
                Doacao.put("tipo2", tipo2);
                Doacao.put("tipo3", tipo3);
                Doacao.put("tipo4", tipo4);
                Doacao.put("objetivo", objetivo);
                Doacao.put("local", local);
                Doacao.put("hora", hora);
                Doacao.put("data", data);

                db.collection("entidade").document(nome)
                        .update(Doacao)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(getApplicationContext(), TelaAdmin.class);
                                    Toast.makeText(CadastroDoacao.this, "Doação Cadastrada", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                            }
                        });
            }
        });

    }

    public void voltarTelaAdm(View view){
        Intent intent = new Intent(this, TelaAdmin.class);
        startActivity(intent);
    }
}
