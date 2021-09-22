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
import java.util.Collections;
import java.util.Random;

public class ActivityEquation extends AppCompatActivity {

    String[] signs = {"+","-","*"};

    ArrayList<Integer> answers = new ArrayList<>();
    Button goButton;
    Button playAgainButton;
    int score = 0;
    int numberOfQuestions = 0;
    TextView scoreTextView;
    TextView timerTextView;
    LinearLayout gameLayout;

    Button val1;
    Button val2;
    Button val3;
    Button val4;
    TextView valueOfx;
    TextView expression;

    String answer = "";
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation);

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        gameLayout = findViewById(R.id.gameLayout);
        goButton = findViewById(R.id.goButton);
        val1 = findViewById(R.id.val1);
        val2 = findViewById(R.id.val2);
        val3 = findViewById(R.id.val3);
        val4 = findViewById(R.id.val4);
        valueOfx = findViewById(R.id.valueOfx);
        expression = findViewById(R.id.expresion);
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
        val1.setEnabled(true);
        val2.setEnabled(true);
        val3.setEnabled(true);
        val4.setEnabled(true);
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        newQuestion();

        new CountDownTimer(30100,1000) {

            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                valueOfx.setText("well");
                expression.setText("done!");
                val1.setEnabled(false);
                val2.setEnabled(false);
                val3.setEnabled(false);
                val4.setEnabled(false);
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void chooseAnswer(View view) {
        if (Integer.toString(pos).equals(view.getTag().toString())) {
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

        int ans1=0;
        answer = "";
        int exp=0;
        answers.clear();
        pos=0;

        Random rand = new Random();

        int a = rand.nextInt(11);
        int b = rand.nextInt(11);
        int x = rand.nextInt(11);
        int c1 = rand.nextInt(3);

        String randSign = signs[c1];



        valueOfx.setText("then x = ?");





        exp = a*x;

        if(randSign.equals("*")) {
            ans1 = b*exp;
        } else if(randSign.equals("+")) {
            ans1 = exp+b;
        } else {
            ans1 = exp-b;
        }

        expression.setText("If "+a+"x"+randSign+b+" ="+ ans1);

        answer = Integer.toString(x);
        answers.add(x);

        for(int i=0;i<3;i++) {
            int m = rand.nextInt(11);
            answers.add(m);
        }

        Collections.shuffle(answers);
        pos = answers.indexOf(x);


        val1.setText(Integer.toString(answers.get(0)));
        val2.setText(Integer.toString(answers.get(1)));
        val3.setText(Integer.toString(answers.get(2)));
        val4.setText(Integer.toString(answers.get(3)));


    }

}