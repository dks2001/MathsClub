package com.dheerendrakumar.mathsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WarmUp extends AppCompatActivity {

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warm_up);

        sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
        int m = sharedPreferences.getInt("wus",0);

        TextView score = findViewById(R.id.wus);
        score.setText("Score : "+m);

        TextView reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score.setText("Score : 0");
                sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
                sharedPreferences.edit().putInt("wus",0).apply();

            }
        });

        Button addition  = findViewById(R.id.addition);
        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarmUp.this,ActivityAddition.class);
                startActivity(intent);
                finish();
            }
        });

        Button multiply = findViewById(R.id.multiplication);
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarmUp.this,ActivityMultiply.class);
                startActivity(intent);
                finish();
            }
        });

        Button subtract = findViewById(R.id.subtraction);
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarmUp.this,ActivitySubtract.class);
                startActivity(intent);
                finish();
            }
        });

        Button ams = findViewById(R.id.amd);
        ams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarmUp.this,ActivityASM.class);
                startActivity(intent);
                finish();
            }
        });

        Button table = findViewById(R.id.table);
        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarmUp.this,ActivityTable.class);
                startActivity(intent);
            }
        });

        Button Useformula = findViewById(R.id.formulaUsed);
        Useformula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarmUp.this,UseFormula.class);
                startActivity(intent);
                finish();
            }
        });
    }
}