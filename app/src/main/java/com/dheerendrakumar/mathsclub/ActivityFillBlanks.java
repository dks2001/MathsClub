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

public class ActivityFillBlanks extends AppCompatActivity {

    ArrayList<Integer> answers = new ArrayList<>();
    ArrayList<String> exp = new ArrayList<>();
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
    TextView expression, sign;

    String answer = "";
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_blanks);

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        gameLayout = findViewById(R.id.gameLayout);
        goButton = findViewById(R.id.goButton);
        val1 = findViewById(R.id.val1);
        val2 = findViewById(R.id.val2);
        val3 = findViewById(R.id.val3);
        val4 = findViewById(R.id.val4);
        expression = findViewById(R.id.answerValue);
        playAgainButton = findViewById(R.id.playAgainButton);
        sign = findViewById(R.id.sign);

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
                val1.setEnabled(false);
                val2.setEnabled(false);
                val3.setEnabled(false);
                val4.setEnabled(false);
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void chooseAnswer(View view) {

        Button button = (Button) view;
        if (answer.equals(button.getText().toString())) {
            score++;
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

        answers.clear();
        exp.clear();
        pos=0;

        Random rand = new Random();

        int r1 = rand.nextInt(19)+1;
        int r2 = rand.nextInt(10)+1;

        answers.add(r1*r2);
        answer = r1+"x"+r2;
        exp.add(answer);


        for(int i=0;i<3;i++) {
            int newRand = rand.nextInt(20);
            answers.add(r1*r2+i);
            exp.add(newRand+"x"+r2);
        }

        expression.setText(answers.get(0)+"");

        Collections.shuffle(exp);

        val1.setText(exp.get(0));
        val2.setText(exp.get(1));
        val3.setText(exp.get(2));
        val4.setText(exp.get(3));


    }

}