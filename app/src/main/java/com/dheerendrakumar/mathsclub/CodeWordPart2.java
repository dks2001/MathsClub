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

public class CodeWordPart2 extends AppCompatActivity {

    char[] alphabets = {' ','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    String[] sign = {"-","+"};
    Button goButton;
    ArrayList<String> answers = new ArrayList<>();
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
    String userAnswer="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_word_part2);

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
            if(userAnswer.equals(button.getText().toString())) {
                score++;
                scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));

            }
            playAgainButton.setVisibility(View.VISIBLE);
            val1.setEnabled(false);
            val2.setEnabled(false);
            val3.setEnabled(false);
            val4.setEnabled(false);
        } else {

            if(userAnswer.equals(button.getText().toString())) {
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
        String ques1 ="";
        String ans1="";
        String ques2 ="";
        String ans2 = "";
        userAnswer ="";

        int rndm = rand.nextInt(8)+1;
        int myRand = rand.nextInt(2);

        Log.i("logggg",myRand+"");

        String usedSign = sign[myRand];

        if(usedSign.equals("-")) {
            for(int i=0;i<5;i++) {
                int newRand = rand.nextInt(26)+1;
                ques1 += alphabets[newRand];
                int newChar = newRand-rndm;
                if(newChar<=0) {
                    newChar = 26+newChar;
                }
                ans1 += newChar;
            }

            for(int i=0;i<5;i++) {
                int newRand = rand.nextInt(26)+1;
                ques2 += alphabets[newRand];
                int newChar = newRand-rndm;
                if(newChar<=0) {
                    newChar = 26+newChar;
                }
                ans2 += newChar;
            }

        } else {
            for(int i=0;i<5;i++) {
                int newRand = rand.nextInt(26)+1;
                ques1 += alphabets[newRand];
                int newChar = newRand+rndm;
                if(newChar>26) {
                    newChar = rndm-(26-newRand);
                }
                ans1 += newChar;
            }

            for(int i=0;i<5;i++) {
                int newRand = rand.nextInt(26)+1;
                ques2 += alphabets[newRand];
                int newChar = newRand+rndm;
                if(newChar>26) {
                    newChar = rndm-(26-newRand);
                }
                ans2 += newChar;
            }
        }





        for(int i=0;i<3;i++) {
            String s ="";
            for(int j=0;j<5;j++) {
                int m = rand.nextInt(26)+1;
                s += m;
            }
            answers.add(s);
        }
        answers.add(ans2);
        userAnswer = ans2;

        Collections.shuffle(answers);
        val1.setText(answers.get(0));
        val2.setText(answers.get(1));
        val3.setText(answers.get(2));
        val4.setText(answers.get(3));

        Log.i("ans2",ans2);

        sumTextView.setText("if "+ ques1+" is coded as '"+ans1+"'. How can '"+ques2+"' be coded?");

    }

}