package com.example.alkar.projetosa;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alkar.projetosa.database.DatabaseHelper;

public class RecuperarSenha extends AppCompatActivity {

    private TextInputLayout textEmail;
    private TextInputLayout textSenha;
    private TextInputLayout textConfirmSenha;
    private Button resetarSenha;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        db = new DatabaseHelper(this);

        textEmail = findViewById(R.id.textEmail);
        textSenha = findViewById(R.id.textSenha);
        textConfirmSenha = findViewById(R.id.textConfirmSenha);
        resetarSenha = findViewById(R.id.resetarSenha);




        resetarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSenha();
            }
        });
    }

    public void updateSenha() {
        String email = textEmail.getEditText().getText().toString();
        String value1 = textSenha.getEditText().getText().toString().trim();
        String value2 = textConfirmSenha.getEditText().getText().toString().trim();

        if(email.isEmpty() && value1.isEmpty() && value2.isEmpty() ) {
            Toast.makeText(this, "Campos não podem estar vazios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!value1.contentEquals(value2)){
            Toast.makeText(this, "As senhas precisam ser a mesma", Toast.LENGTH_LONG).show();
            return;
        }

        if(db.validarEmail(email)) {
            db.updateSenha(email, value1);

            Toast.makeText(this, "password reset successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Email errado!", Toast.LENGTH_SHORT).show();
        }






    }

}
