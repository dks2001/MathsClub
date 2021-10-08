package com.dheerendrakumar.mathsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityGameZone extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_zone);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.click);

        sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
        int m = sharedPreferences.getInt("gzs",0);

        TextView score = findViewById(R.id.gzs);
        TextView reset = findViewById(R.id.reset);

        score.setText("Score : "+m);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                score.setText("Score : 0");
                sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
                sharedPreferences.edit().putInt("gzs",0).apply();
            }
        });




        ImageView greaterOrLess = findViewById(R.id.greaterOrLess);
        greaterOrLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                Intent intent = new Intent(ActivityGameZone.this,GreaterSmaller.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView maxmin = findViewById(R.id.maxmin);
        maxmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                Intent intent = new Intent(ActivityGameZone.this,ActivityMaxMin.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView findx = findViewById(R.id.findx);
        findx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                Intent intent = new Intent(ActivityGameZone.this,ActivityFindx.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView rightWrong = findViewById(R.id.rightWrong);
        rightWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                Intent intent = new Intent(ActivityGameZone.this,ActivityRightWrong.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView ascDes = findViewById(R.id.acsendingDesc);
        ascDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                Intent intent = new Intent(ActivityGameZone.this,ActivityAscendingDescending.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView getsum = findViewById(R.id.getSum);
        getsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                Intent intent = new Intent(ActivityGameZone.this,ActivityGetSum.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView chalengeFriend = findViewById(R.id.challengeFriend);
        chalengeFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                Intent intent = new Intent(ActivityGameZone.this,ActivityChallengeFriend.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView fillBlank = findViewById(R.id.fillblanks);
        fillBlank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                Intent intent = new Intent(ActivityGameZone.this,ActivityFillBlanks.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView equation = findViewById(R.id.equation);
        equation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                Intent intent = new Intent(ActivityGameZone.this,ActivityEquation.class);
                startActivity(intent);
                finish();
            }
        });
    }
}