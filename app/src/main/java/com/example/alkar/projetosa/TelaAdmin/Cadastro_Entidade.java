package com.example.alkar.projetosa.TelaAdmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.alkar.projetosa.R;

import java.io.IOException;

public class Cadastro_Entidade extends AppCompatActivity {

    private TextInputLayout textInputNomeEntidade;
    private TextInputLayout textInputObjetivoEntidade;
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
        btn_select_photo_entidade = findViewById(R.id.btn_select_photo_Entidade);
        img_photo_Entidade = findViewById(R.id.img_photo_Entidade);

        btn_select_photo_entidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
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


}
