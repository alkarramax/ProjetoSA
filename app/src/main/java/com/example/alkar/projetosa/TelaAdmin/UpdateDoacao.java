package com.example.alkar.projetosa.TelaAdmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alkar.projetosa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateDoacao extends AppCompatActivity {

    private TextInputLayout textInputTipo1Update;
    private TextInputLayout textInputTipo2Update;
    private TextInputLayout textInputTipo3Update;
    private TextInputLayout textInputTipo4Update;
    private TextInputLayout textObjetivoUpdate;
    private TextInputLayout textlocalUpdate;
    private TextInputLayout textHoraUpdate;
    private TextInputLayout textDataUpdate;
    private Button Update;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doacao);

        textInputTipo1Update = findViewById(R.id.txt_Tipo1_Update);
        textInputTipo2Update = findViewById(R.id.txt_Tipo2_Update);
        textInputTipo3Update = findViewById(R.id.txt_Tipo3_Update);
        textInputTipo4Update = findViewById(R.id.txt_Tipo4_Update);
        textlocalUpdate = findViewById(R.id.txt_Local_Doa_Update);
        textObjetivoUpdate = findViewById(R.id.txt_Obetivo_Doa_Update);
        textHoraUpdate = findViewById(R.id.txt_Hora_Doa_Update);
        textDataUpdate = findViewById(R.id.txt_Data_Doa_Update);
        Update = findViewById(R.id.button_Cadastro_Doa_Update);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDoacao();
            }
        });

    }

    private void updateDoacao() {

        Intent intent = getIntent();
        String nome = intent.getStringExtra("nomeEntidade");

        String tipo1 = textInputTipo1Update.getEditText().getText().toString().trim();
        String tipo2 = textInputTipo2Update.getEditText().getText().toString().trim();
        String tipo3 = textInputTipo3Update.getEditText().getText().toString().trim();
        String tipo4 = textInputTipo4Update.getEditText().getText().toString().trim();
        String objetivo = textObjetivoUpdate.getEditText().getText().toString().trim();
        String local = textlocalUpdate.getEditText().getText().toString().trim();
        String hora = textHoraUpdate.getEditText().getText().toString().trim();
        String data = textDataUpdate.getEditText().getText().toString().trim();

        Map<String, Object> DoacaoUpdate = new HashMap<>();
        DoacaoUpdate.put("tipo1", tipo1);
        DoacaoUpdate.put("tipo2", tipo2);
        DoacaoUpdate.put("tipo3", tipo3);
        DoacaoUpdate.put("tipo4", tipo4);
        DoacaoUpdate.put("objetivo", objetivo);
        DoacaoUpdate.put("local", local);
        DoacaoUpdate.put("hora", hora);
        DoacaoUpdate.put("data", data);

        db.collection("entidade").document(nome)
                .update(DoacaoUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(UpdateDoacao.this, "Doacao alterada", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), TelaAdmin.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(UpdateDoacao.this, " :( ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void voltarTelaAdm(View view) {
        Intent intent = new Intent(getApplicationContext(), TelaAdmin.class);
        startActivity(intent);
    }
}
