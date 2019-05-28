package com.example.alkar.projetosa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class TelaDoacao extends AppCompatActivity {

    private TextView textView;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao);
        seebar();
    }

    public void seebar() {

        seekBar = (SeekBar)findViewById(R.id.seekQtd);
        textView = (TextView)findViewById(R.id.txtQtd);
        textView.setText(":): " +seekBar.getProgress() + "/" + seekBar.getMax());

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressV;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressV = progress;
                        textView.setText("Quantidade: " + progress + "/" + seekBar.getMax());
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textView.setText(":): " + progressV + "/" + seekBar.getMax());
                    }
                }
        );
    }
    public void voltarT(View view){

        Intent intent = new Intent(this, Home_Imagens_Detalhe.class);
        startActivity(intent);
    }
}