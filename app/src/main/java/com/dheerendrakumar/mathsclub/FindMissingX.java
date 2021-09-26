package com.dheerendrakumar.mathsclub;

import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
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

public class FindMissingX extends AppCompatActivity {

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
    int quesCount = 1;
    Button val1,val2,val3,val4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_missing_x);

        sumTextView = findViewById(R.id.questiontxt);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        gameLayout = findViewById(R.id.gameLayout);
        goButton = findViewById(R.id.goButton);
        playAgainButton = findViewById(R.id.playAgainButton);
        val1 = findViewById(R.id.val1);
        val2 = findViewById(R.id.val2);
        val3 = findViewById(R.id.val3);
        val4 = findViewById(R.id.val4);

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
        quesCount=1;
        val1.setEnabled(true);
        val2.setEnabled(true);
        val3.setEnabled(true);
        val4.setEnabled(true);

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

            if(quesCount==10) {
                if(Integer.toString(correctAnswer).equals(button.getText().toString())) {
                    score++;
                    scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
                }
                playAgainButton.setVisibility(View.VISIBLE);
                val1.setEnabled(false);
                val2.setEnabled(false);
                val3.setEnabled(false);
                val4.setEnabled(false);
            } else {
                if (Integer.toString(correctAnswer).equals(button.getText().toString())) {
                    score++;
                    scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));

                }
                newQuestion();
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

        val1.setEnabled(true);
        val2.setEnabled(true);
        val3.setEnabled(true);
        val4.setEnabled(true);
        answers.clear();
        correctAnswer=0;

        int rndm = rand.nextInt(3);
        int myRand = rand.nextInt(4)+2;

        int a = rand.nextInt(30);
        String usedSign = sign[rndm];

        if(usedSign.equals("*")) {

            answers.add(a);
            for(int i=0;i<5;i++) {
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

        int ca = rand.nextInt(5);
        String ans ="";

        for(int i=0;i<5;i++) {
            if(ca==i) {
                ans += " __ ";
            } else {
                ans += answers.get(i)+" ";
            }
        }

        correctAnswer = answers.get(ca);
        sumTextView.setText(ans);

        Log.i("correct",correctAnswer+"");

        answers.clear();

        for(int i=0;i<3;i++) {
            int m = rand.nextInt(60)+20;
            while(answers.contains(m)) {
                m = rand.nextInt(60) + 10;
            }
            answers.add(m);
        }

        answers.add(correctAnswer);

        Collections.shuffle(answers);

        val1.setText(answers.get(0)+"");
        val2.setText(answers.get(1)+"");
        val3.setText(answers.get(2)+"");
        val4.setText(answers.get(3)+"");

    }

}