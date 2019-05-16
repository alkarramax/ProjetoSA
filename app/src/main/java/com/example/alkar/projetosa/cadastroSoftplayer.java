package com.example.alkar.projetosa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;



public class cadastroSoftplayer extends AppCompatActivity {

    private TextInputLayout textInputNome;
    private TextInputLayout textInputSobrenome;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputUnidade;
    private TextInputLayout textInputCargo;
    private TextInputLayout textInputSenha;
    private Button registrar;
    private ImageView imagem;
    private final int GALERIA_IMAGENS = 1;
    private final int PERMISSAO_REQUEST = 2;



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




        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }

        imagem         = findViewById(R.id.ivImagem);
        ImageButton galeria = findViewById(R.id.btnImagem);
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, GALERIA_IMAGENS);
            }
        });

        addData();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            imagem.setImageBitmap(thumbnail);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSAO_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
            }
            return;
        }
    }

    public void addData() {
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validarNome() | !validarSobrenome() | !validarEmail() | !validarUnidade() | !validarCargo() | !validarSenha()) {
                    return;
                } else {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                      startActivity(i);
                }
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

    public void voltarMain(View v) {
        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent2);
    }

}
