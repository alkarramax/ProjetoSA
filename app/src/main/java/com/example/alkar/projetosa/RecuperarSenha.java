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


public class RecuperarSenha extends AppCompatActivity {

    private TextInputLayout textEmail;
    private TextInputLayout textSenha;
    private TextInputLayout textConfirmSenha;
    private Button resetarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);


        textEmail = findViewById(R.id.textEmail);
        textSenha = findViewById(R.id.textSenha);
        textConfirmSenha = findViewById(R.id.textConfirmSenha);
        resetarSenha = findViewById(R.id.resetarSenha);

    }

}
