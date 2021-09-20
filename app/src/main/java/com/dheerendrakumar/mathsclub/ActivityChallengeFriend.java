package com.dheerendrakumar.mathsclub;

import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ActivityChallengeFriend extends AppCompatActivity {

    String[] sign = {"*","-","+"};
    Button goButton;
    ArrayList<Integer> answers = new ArrayList<>();
    int score = 0;
    int numberOfQuestions = 10;
    TextView scoreTextView;
    TextView sumTextView;
    TextView timerTextView;
    LinearLayout gameLayout;
    int correctAnswer=0;
    Button playAgainButton;
    EditText nextSequenceNumber;
    int quesCount = 1;
    Button skip,submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_friend);

        sumTextView = findViewById(R.id.questiontxt);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        gameLayout = findViewById(R.id.gameLayout);
        goButton = findViewById(R.id.goButton);
        nextSequenceNumber = findViewById(R.id.nextSequenceNumber);
        playAgainButton = findViewById(R.id.playAgainButton);
        skip = findViewById(R.id.skip);
        submit = findViewById(R.id.submit);

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
       // numberOfQuestions = 0;
        quesCount=1;
        skip.setEnabled(true);
        submit.setEnabled(true);

        playAgainButton.setVisibility(View.INVISIBLE);
        timerTextView.setText("30s");
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        newQuestion();

        new CountDownTimer(30100,1000) {

            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                //playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void chooseAnswer(View view) {

        Button button = (Button)view;

        if(button.getText().toString().equals("play again")) {
            playAgain(button);
        }

        if(button.getText().toString().equals("skip")) {

            if(quesCount==10) {
                playAgainButton.setVisibility(View.VISIBLE);
                skip.setEnabled(false);
                submit.setEnabled(false);
            } else {
                newQuestion();
            }

        } else {

            if(quesCount==10) {
                playAgainButton.setVisibility(View.VISIBLE);
                skip.setEnabled(false);
                skip.setEnabled(false);
            } else {

                if(nextSequenceNumber.getText().toString().equals("")) {
                    Toast.makeText(this, "Write your answer!", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.toString(correctAnswer).equals(nextSequenceNumber.getText().toString())) {
                    score++;
                    scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
                    newQuestion();
                }
            }
        }

        quesCount++;

    }

    public void start(View view) {
        goButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton));
    }

    public void newQuestion() {
        Random rand = new Random();

        skip.setEnabled(true);
        submit.setEnabled(true);
        answers.clear();
        correctAnswer=0;
        nextSequenceNumber.setText("");

        int rndm = rand.nextInt(3);
        int myRand = rand.nextInt(4)+2;

        int a = rand.nextInt(30);
        String usedSign = sign[rndm];

        if(usedSign.equals("*")) {

            answers.add(a);
            for(int i=0;i<4;i++) {
                answers.add(a*myRand);
                a = a*myRand;
            }
        } else if(usedSign.equals("-")) {
            answers.add(a);
            for(int i=0;i<5;i++) {
                answers.add(a-myRand);
                a = a-myRand;
            }
        } else {
            answers.add(a);
            for(int i=0;i<5;i++) {
                answers.add(a+myRand);
                a = a+myRand;
            }
        }

        correctAnswer = answers.get(4);


        sumTextView.setText(answers.get(0)+", "+
                            answers.get(1)+", "+
                            answers.get(2)+", "+
                            answers.get(3)+", ?");

    }

}