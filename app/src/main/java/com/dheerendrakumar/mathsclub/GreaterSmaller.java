package com.dheerendrakumar.mathsclub;

import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Random;

public class GreaterSmaller extends AppCompatActivity {

    String[] signs = {"+","-","*"};

    Button goButton;
    Button playAgainButton;
    int score = 0;
    int numberOfQuestions = 0;
    TextView scoreTextView;
    TextView timerTextView;
    LinearLayout gameLayout;

    ImageView greaterthan;
    ImageView lessthan;
    TextView exp1;
    TextView exp2;

    String answer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greater_smaller);

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        gameLayout = findViewById(R.id.gameLayout);
        goButton = findViewById(R.id.goButton);

        greaterthan = findViewById(R.id.greaterthan);
        lessthan = findViewById(R.id.lessthan);
        exp1 = findViewById(R.id.exp1);
        exp2 = findViewById(R.id.exp2);
        playAgainButton = findViewById(R.id.playAgainButton);

        goButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(goButton);
            }
        });

    }

    public void playAgain(View view) {
        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        playAgainButton.setVisibility(View.INVISIBLE);
        greaterthan.setEnabled(true);
        lessthan.setEnabled(true);
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        newQuestion();

        new CountDownTimer(30100,1000) {

            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                exp1.setText("well");
                exp2.setText("done!");
                greaterthan.setEnabled(false);
                lessthan.setEnabled(false);
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void chooseAnswer(View view) {
        if (answer.equals(view.getTag().toString())) {
            score++;
        } else {

        }
        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        newQuestion();
    }

    public void start(View view) {
        goButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.timerTextView));
    }

    public void newQuestion() {
        Random rand = new Random();

        int a = rand.nextInt(50);
        int b = rand.nextInt(50);
        int m = rand.nextInt(50);
        int n = rand.nextInt(50);
        int c = rand.nextInt(3);

        String randSign = signs[c];

        exp1.setText(Integer.toString(a) + " "+randSign+" " + Integer.toString(b));
        exp2.setText(Integer.toString(m) + " "+randSign+" " + Integer.toString(n));


        int ans1=0;
        int ans2=0;
        answer = "";

        if(randSign.equals("+")) {
            ans1 = a+b;
            ans2 = m+n;

        } else if(randSign.equals("*")) {
            ans1 = a*b;
            ans2 = m*n;

        } else {
            ans1 = a-b;
            ans2 = m-n;

        }


        if(ans1<ans2) {
            answer = "lessthan";
        } else {
            answer = "greaterthan";
        }
    }

}