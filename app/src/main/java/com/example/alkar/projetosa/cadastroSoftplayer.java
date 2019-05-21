package com.example.alkar.projetosa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;


public class cadastroSoftplayer extends AppCompatActivity {

    private TextInputLayout textInputNome;
    private TextInputLayout textInputSobrenome;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputUnidade;
    private TextInputLayout textInputCargo;
    private TextInputLayout textInputSenha;
    private Button registrar;
    private ImageButton btn_selected_photo;
    private ImageView img_photo;
    private Uri selectedUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro__softplayer);

        textInputNome = findViewById(R.id.textSoft_input_nome);
        textInputSobrenome = findViewById(R.id.textSoft_input_sobrenome);
        textInputEmail = findViewById(R.id.textSoft_input_email);
        textInputUnidade = findViewById(R.id.textSoft_input_unidade);
        textInputCargo = findViewById(R.id.textSoft_input_cargo);
        textInputSenha = findViewById(R.id.textSoft_input_senha);
        registrar = findViewById(R.id.button_Cadastro_Softplayers);
        btn_selected_photo = findViewById(R.id.btn_select_photo);
        img_photo = findViewById(R.id.img_photo);

        btn_selected_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0) {
            selectedUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedUri);
                img_photo.setImageDrawable(new BitmapDrawable(bitmap));
            } catch (IOException e) {
            }
        }
    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);

    }


    private void createUser() {

        String nome = textInputNome.getEditText().getText().toString().trim();
        String sobrenome = textInputSobrenome.getEditText().getText().toString().trim();
        String email = textInputEmail.getEditText().getText().toString().trim();
        String unidade = textInputUnidade.getEditText().getText().toString().trim();
        String cargo = textInputCargo.getEditText().getText().toString().trim();
        String senha = textInputSenha.getEditText().getText().toString().trim();

        if(nome.isEmpty()) {
            textInputNome.setError("Campo não pode estar vazio.");
            return;
        } else {
            textInputNome.setError(null);
        }

        if(sobrenome.isEmpty()) {
            textInputSobrenome.setError("Campo não pode estar vazio.");
            return;
        } else {
            textInputSobrenome.setError(null);
        }

        if(senha.isEmpty()) {
            textInputSenha.setError("Campo não pode estar vazio.");
            return;
        } else if(senha.length() < 6) {
            textInputSenha.setError("Minimo de 6 caracteres");
            return;
        } else {
            textInputSenha.setError(null);
        }

        if(email.isEmpty()) {
            textInputEmail.setError("Campo não pode estar vazio.");
            return;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textInputEmail.setError("Email inválido ");
            return;
        } else  {
            textInputEmail.setError(null);
        }


        if(cargo.isEmpty()) {
            textInputCargo.setError("Campo não pode estar vazio.");
            return;
        } else {
            textInputCargo.setError(null);
        }

        if(unidade.isEmpty()) {
            textInputUnidade.setError("Campo não pode estar vazio.");
            return;
        } else {
            textInputUnidade.setError(null);
        }



            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(cadastroSoftplayer.this, "Correct!", Toast.LENGTH_SHORT).show();
                        saveUserInFirebase();

                    } else {
                        Toast.makeText(cadastroSoftplayer.this, " :( " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    private void saveUserInFirebase() {
        String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);
        ref.putFile(selectedUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.i("Teste", uri.toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(cadastroSoftplayer.this, "Deu ruim lek!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public boolean validarNome() {
        String nomeInput = textInputNome.getEditText().getText().toString().trim();


        if(nomeInput.isEmpty()) {
            textInputNome.setError("Campo não pode estar vazio.");
            return false;
        } else {
            textInputNome.setError(null);
            return true;
        }


    }

    public boolean validarSobrenome() {
        String sobrenomeInput = textInputSobrenome.getEditText().getText().toString().trim();


        if(sobrenomeInput.isEmpty()) {
            textInputSobrenome.setError("Campo não pode estar vazio.");
            return false;
        } else {
            textInputSobrenome.setError(null);
            return true;
        }
    }

    public boolean validarEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();


        if(emailInput.isEmpty()) {
            textInputEmail.setError("Campo não pode estar vazio.");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            textInputEmail.setError("Email inválido ");
            return false;
        }else  {
            textInputEmail.setError(null);
            return true;
        }
    }

    public boolean validarUnidade() {
        String unidadeInput = textInputUnidade.getEditText().getText().toString().trim();


        if(unidadeInput.isEmpty()) {
            textInputUnidade.setError("Campo não pode estar vazio.");
            return false;
        } else {
            textInputUnidade.setError(null);
            return true;
        }
    }

    public boolean validarCargo() {
        String cargoInput = textInputCargo.getEditText().getText().toString().trim();


        if(cargoInput.isEmpty()) {
            textInputCargo.setError("Campo não pode estar vazio.");
            return false;
        } else {
            textInputCargo.setError(null);
            return true;
        }
    }

    public boolean validarSenha() {
        String senhaInput = textInputSenha.getEditText().getText().toString().trim();


        if(senhaInput.isEmpty()) {
            textInputSenha.setError("Campo não pode estar vazio.");
            return false;
        } else {
            textInputSenha.setError(null);
            return true;
        }
    }


}
