package com.example.alkar.projetosa;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alkar.projetosa.TelaAdmin.TelaAdmin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginMain extends AppCompatActivity {

    private Button buttonSobre;
    private TextInputLayout et_email;
    private TextInputLayout et_senha;
    private Button bt_login;
    private TextView esqueciSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        getWindow().setStatusBarColor(Color.TRANSPARENT);

        et_email = findViewById(R.id.textLogin_Email);
        et_senha = findViewById(R.id.textLogin_Senha);
        bt_login = findViewById(R.id.button_Entrar);

        esqueciSenha = findViewById(R.id.button_EsqueciSenha);


        esqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RecuperarSenha.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getEditText().getText().toString();
                String senha = et_senha.getEditText().getText().toString();

                if (email.equals("admin") && senha.equals("admin")) {
                    telaAdmin();
                } else {

                    if (email.isEmpty() || senha.isEmpty()) {
                        Toast.makeText(LoginMain.this, "Campo não podem estar vazios!", Toast.LENGTH_SHORT).show();
                    }

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(LoginMain.this, "Logado com sucesso!", Toast.LENGTH_SHORT).show();
                                    entrarHome();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginMain.this, "Email ou senha invalido", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }

    private void telaAdmin() {
        Intent intent = new Intent(getApplicationContext(), TelaAdmin.class);
        startActivity(intent);
    }

    public void Cadastro(View view) {
        Intent i = new Intent(getApplicationContext(), cadastroSoftplayer.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public boolean validarEmail() {
        String emailInput = et_email.getEditText().getText().toString().trim();


        if(emailInput.isEmpty()) {
            et_email.setError("Campo Email não pode estar vazio.");
            return false;
        }else  {
            et_email.setError(null);
            return true;
        }
    }

    public boolean validarSenha() {
        String senhaInput = et_senha.getEditText().getText().toString().trim();


        if(senhaInput.isEmpty()) {
            et_senha.setError("Campo Senha não pode estar vazio.");
            return false;
        } else {
            et_senha.setError(null);
            return true;
        }
    }

    public void entrarHome() {
        Intent intent1 = new Intent(getApplicationContext(), Home.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);

    }

}
