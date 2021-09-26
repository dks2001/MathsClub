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

public class InterchangeTheSign extends AppCompatActivity {

    String[] sign = {"-","+","*"};
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
    Button val1;
    Button val2;
    Button val3;
    Button val4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interchange_the_sign);

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
        // numberOfQuestions = 0;
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
            if(String.valueOf(correctAnswer).equals(button.getText().toString())) {
                score++;
                scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));

            }
            playAgainButton.setVisibility(View.VISIBLE);
            val1.setEnabled(false);
            val2.setEnabled(false);
            val3.setEnabled(false);
            val4.setEnabled(false);
        } else {

            if(String.valueOf(correctAnswer).equals(button.getText().toString())) {
                score++;
                scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));

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

        int rndm1 = rand.nextInt(3);
        int rndm2 = rand.nextInt(3);
        while(rndm2==rndm1) {
            rndm2 = rand.nextInt(3);
        }

        int a = rand.nextInt(20);
        int b = rand.nextInt(20);
        int c = rand.nextInt(20);

        String s1 = sign[rndm1];
        String s2 = sign[rndm2];

        if(s1.equals("-") && s2.equals("+")) {

            sumTextView.setText("Interchange the signs.\n"+a+"-"+b+"+"+c);
            correctAnswer = a+b-c;

        } else if(s1.equals("+") && s2.equals("*")) {

            sumTextView.setText("Interchange the signs.\n"+a+"*"+b+"+"+c);
            correctAnswer = a*b+c;

        } else if(s1.equals("-") && s2.equals("*")){
            sumTextView.setText("Interchange the signs.\n"+a+"-"+b+"*"+c);
            correctAnswer = a*b-c;

        } else if(s1.equals("+") && s2.equals("-")) {

            sumTextView.setText("Interchange the signs.\n"+a+"+"+b+"-"+c);
            correctAnswer = a-b+c;

        } else if(s1.equals("*") && s2.equals("+")) {

            sumTextView.setText("Interchange the signs.\n"+a+"*"+b+"+"+c);
            correctAnswer = a+b*c;

        } else if(s1.equals("*") && s2.equals("-")) {
            sumTextView.setText("Interchange the signs.\n"+a+"*"+b+"-"+c);
            correctAnswer = a-b*c;
        }

        answers.add(correctAnswer);
        answers.add(correctAnswer+1);
        answers.add(correctAnswer-1);
        answers.add(correctAnswer+5);

        Log.i("ca",correctAnswer+"");

        Collections.shuffle(answers);


        val1.setText(answers.get(0)+"");
        val2.setText(answers.get(1)+"");
        val3.setText(answers.get(2)+"");
        val4.setText(answers.get(3)+"");


    }
}