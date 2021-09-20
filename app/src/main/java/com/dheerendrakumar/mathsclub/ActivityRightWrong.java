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

public class ActivityRightWrong extends AppCompatActivity {

    String[] signs = {"+","-","*"};

    Button goButton;
    int r;
    ArrayList<Integer> answers = new ArrayList<>();
    int locationOfCorrectAnswer;
    int answerShowed;
    int score = 0;
    int numberOfQuestions = 0;
    TextView scoreTextView;
    ImageView right;
    ImageView wrong;
    TextView sumTextView;
    TextView timerTextView;
    Button playAgainButton;
    LinearLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_wrong);

        sumTextView = findViewById(R.id.expressionRightWrong);
        right = findViewById(R.id.rightImageView);
        wrong = findViewById(R.id.wrongImageView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        gameLayout = findViewById(R.id.gameLayout);
        goButton = findViewById(R.id.goButton);

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
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        newQuestion();
        playAgainButton.setVisibility(View.INVISIBLE);
        right.setEnabled(true);
        wrong.setEnabled(true);

        new CountDownTimer(30100,1000) {

            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                right.setEnabled(false);
                wrong.setEnabled(false);
            }
        }.start();
    }

    public void chooseAnswer(View view) {
        if (r==1 && view.getTag().toString().equals("true")) {
            score++;
        } else if(r==0 && view.getTag().toString().equals("false")){
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
        int c = rand.nextInt(3);

        String radSign = signs[c];
        r=0;


        locationOfCorrectAnswer = rand.nextInt(2);
        answerShowed = rand.nextInt(2);
        int ans=0;

        answers.clear();

        for (int i=0; i<2; i++) {
            if (i == locationOfCorrectAnswer) {
                if(radSign.equals("+")) {
                    ans = a+b;
                } else if(radSign.equals("*")) {
                    ans = a*b;
                } else {
                    ans = a-b;
                }
                answers.add(ans);
            } else {
                int wrongAnswer = rand.nextInt(41);

                while (wrongAnswer == ans) {
                    wrongAnswer = rand.nextInt(41);
                }

                answers.add(wrongAnswer);
            }

        }

        Collections.shuffle(answers);
        if(answers.get(answerShowed)==ans) {
            r=1;
        }

        sumTextView.setText(Integer.toString(a) + " "+radSign+" " + Integer.toString(b) +" = " +answers.get(answerShowed));


    }

}