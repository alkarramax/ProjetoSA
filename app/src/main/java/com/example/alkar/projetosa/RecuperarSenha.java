package com.example.alkar.projetosa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class RecuperarSenha extends AppCompatActivity {

    private TextInputLayout textEmail;
    private Button resetarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);


        textEmail = findViewById(R.id.textEmail);
        resetarSenha = findViewById(R.id.resetarSenha);

        resetarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(textEmail.getEditText().getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(RecuperarSenha.this, "Mensagem enviada com sucesso", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(RecuperarSenha.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }

    public void voltarLogin(View view){

        Intent intent = new Intent(this, Doacao.class);
        startActivity(intent);
    }
}
