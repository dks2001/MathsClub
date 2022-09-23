package com.dheerendrakumar.mathsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView scores;
    SoundPool soundPool;
    int soundId;
    int i=0;

    PopupWindow popupWindoww;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView maths = findViewById(R.id.maths);

        maths.setY(-1000);
        maths.animate().translationY(0).setDuration(1000).alpha(1);



        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(MainActivity.this, R.raw.click, 1);


        Button table = findViewById(R.id.reasoning);
        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundId, 1, 1, 0, 0, 1);
                Intent intent = new Intent(MainActivity.this,ActivityReasoning.class);
                startActivity(intent);
            }
        });

        Button game = findViewById(R.id.gameZone);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundId, 1, 1, 0, 0, 1);
                Intent intent = new Intent(MainActivity.this,ActivityGameZone.class);
                startActivity(intent);
            }
        });

        Button warmUp = findViewById(R.id.warmUp);
        warmUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundId, 1, 1, 0, 0, 1);
                Intent intent = new Intent(MainActivity.this,WarmUp.class);
                startActivity(intent);
            }
        });

        ImageView myAccount = findViewById(R.id.profile);
        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MyAccount.class);
                startActivity(intent);
            }
        });

        ImageView dashboard = findViewById(R.id.dashboard);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Dashboard.class);
                startActivity(intent);
            }
        });

        ImageView leaderboard = findViewById(R.id.leaderboard);
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Leaderboard.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

//        sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
//        int m = sharedPreferences.getInt("wus",0);
//        int n = sharedPreferences.getInt("rs",0);
//        int o = sharedPreferences.getInt("gzs",0);
//
    }

    @Override
    public void onBackPressed() {

        if(i==1) {
            popupWindoww.dismiss();
            i=0;
        } else {
            super.onBackPressed();
        }
    }
}