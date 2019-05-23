package com.example.alkar.projetosa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Doacao extends AppCompatActivity {

    private TextView textViewTipo1;
    private SeekBar seekBar1;
    private int value=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao);

        textViewTipo1=findViewById(R.id.txtQtd);
        seekBar1=findViewById(R.id.seekQtd);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                value=i;
                textViewTipo1.setText(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    public void voltarT(View view){

        Intent intent = new Intent(this, Home_Imagens_Detalhe.class);
        startActivity(intent);
    }
}