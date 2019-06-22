package com.example.alkar.projetosa.TelaAdmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alkar.projetosa.Firebase.Entidade;
import com.example.alkar.projetosa.Firebase.Softplayer;
import com.example.alkar.projetosa.R;
import com.example.alkar.projetosa.cadastroSoftplayer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class Cadastro_Entidade extends AppCompatActivity {

    private TextInputLayout textInputNomeEntidade;
    private TextInputLayout textInputDescricao;
    private Button cadastrar;

    private ImageButton btn_select_photo_entidade;
    private ImageView img_photo_Entidade;
    private Uri selectedUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro__entidade);

        textInputNomeEntidade = findViewById(R.id.textInputNomeEntidade);
        textInputDescricao = findViewById(R.id.textInputDescricao);
        cadastrar = findViewById(R.id.btn_CadastrarEntidade);
        btn_select_photo_entidade = findViewById(R.id.btn_select_photo_Entidade);
        img_photo_Entidade = findViewById(R.id.img_photo_Entidade);


        btn_select_photo_entidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        saveEntidade();
            }
        });
    }


    private void saveEntidade() {
        final String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);
        ref.putFile(selectedUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String uuid = UUID.randomUUID().toString();
                                String descricao = textInputDescricao.getEditText().getText().toString().trim();
                                String nome = textInputNomeEntidade.getEditText().getText().toString().trim();
                                String entidadeUrl = uri.toString();

                                Entidade entidade = new Entidade(uuid, nome, descricao, entidadeUrl);

                                FirebaseFirestore.getInstance().collection("entidade")
                                        .document(nome).set(entidade)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    Toast.makeText(Cadastro_Entidade.this, "Entidade Cadastrada", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(Cadastro_Entidade.this, " :( ", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });
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
                img_photo_Entidade.setImageDrawable(new BitmapDrawable(bitmap));
            } catch (IOException e) {
            }
        }

    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);

    }

    public void voltarTelaAdmin(View view){

        Intent intent = new Intent(this, TelaAdmin.class);
        startActivity(intent);
    }

}
