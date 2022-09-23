package com.dheerendrakumar.mathsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WarmUp extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SoundPool soundPool;
    int soundId;
    TextView score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warm_up);

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(WarmUp.this, R.raw.click, 1);


        Button addition  = findViewById(R.id.addition);
        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundId, 1, 1, 0, 0, 1);
                Intent intent = new Intent(WarmUp.this,ActivityAddition.class);
                startActivity(intent);
            }
        });

        Button multiply = findViewById(R.id.multiplication);
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundId, 1, 1, 0, 0, 1);
                Intent intent = new Intent(WarmUp.this,ActivityMultiply.class);
                startActivity(intent);
            }
        });

        Button subtract = findViewById(R.id.subtraction);
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundId, 1, 1, 0, 0, 1);
                Intent intent = new Intent(WarmUp.this,ActivitySubtract.class);
                startActivity(intent);
            }
        });

        Button ams = findViewById(R.id.amd);
        ams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundId, 1, 1, 0, 0, 1);
                Intent intent = new Intent(WarmUp.this,ActivityASM.class);
                startActivity(intent);
            }
        });

        Button table = findViewById(R.id.table);
        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundId, 1, 1, 0, 0, 1);
                Intent intent = new Intent(WarmUp.this,ActivityTable.class);
                startActivity(intent);
            }
        });

        Button Useformula = findViewById(R.id.formulaUsed);
        Useformula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundId, 1, 1, 0, 0, 1);
                Intent intent = new Intent(WarmUp.this,UseFormula.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}