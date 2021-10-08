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

         ImageView share = findViewById(R.id.share);
         scores = findViewById(R.id.totalScore);

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(MainActivity.this, R.raw.click, 1);

        ImageView install = findViewById(R.id.install);

        install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i=1;

                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.install_inflator, null);
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = false; // lets taps outside the popup also dismiss it
                popupWindoww = new PopupWindow(popupView, width, height, focusable);

                TextView installQuizBook = (TextView) popupView.findViewById(R.id.installQuizBook);
                installQuizBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.dheerendrakumar.quiz")); /// here "yourpackegName" from your app packeg Name
                        startActivity(intent);
                    }
                });

                ImageView close = (ImageView) popupView.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindoww.dismiss();
                    }
                });

                popupWindoww.showAtLocation(findViewById(R.id.ml), Gravity.CENTER, 0, 0);

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Math Club : Train the Brain");
                    String shareMessage= "\nEnhance your knowledge and make new friends here only on QuizBOOk\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

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
    }

    @Override
    protected void onStart() {
        super.onStart();

        sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
        int m = sharedPreferences.getInt("wus",0);
        int n = sharedPreferences.getInt("rs",0);
        int o = sharedPreferences.getInt("gzs",0);
        scores.setText(m+n+o+"");
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