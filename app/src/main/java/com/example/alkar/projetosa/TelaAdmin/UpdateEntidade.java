package com.example.alkar.projetosa.TelaAdmin;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.alkar.projetosa.Firebase.Entidade;
import com.example.alkar.projetosa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdateEntidade extends AppCompatActivity {

    private ImageButton selectPhoto;
    private Button alterarEntidade;
    private ImageView img_photo_update_entidade;
    private Uri selectedUri;

    private TextInputLayout textInputUpdateDescricao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_entidade);

        textInputUpdateDescricao = findViewById(R.id.textInputUpdateDescricao);

        selectPhoto = findViewById(R.id.btn_select_photo_update_Entidade);
        alterarEntidade = findViewById(R.id.btn_UpdateEntidade);
        img_photo_update_entidade = findViewById(R.id.img_photo_update_Entidade);

        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo();
            }
        });

        alterarEntidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEntidade();
            }
        });

    }

    private void updateEntidade() {
        final String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);
        ref.putFile(selectedUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Intent intent = getIntent();
                                String nome = intent.getStringExtra("nomeEntidade");

                                String descricao = textInputUpdateDescricao.getEditText().getText().toString().trim();
                                String entidadeUrl = uri.toString();

                                Map<String, Object> updateEntidade = new HashMap<>();
                                updateEntidade.put("descricao", descricao);
                                updateEntidade.put("entidadeUrl", entidadeUrl);

                                FirebaseFirestore.getInstance().collection("entidade")
                                        .document(nome).update(updateEntidade)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    Toast.makeText(UpdateEntidade.this, "Entidade Cadastrada", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(UpdateEntidade.this, " :( ", Toast.LENGTH_SHORT).show();
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
                img_photo_update_entidade.setImageDrawable(new BitmapDrawable(bitmap));
            } catch (IOException e) {
            }
        }

    }


    private void photo() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }


    public void voltarTelaAdmin(View view) {
        Intent intent = new Intent(this, TelaAdmin.class);
        startActivity(intent);
    }
}
