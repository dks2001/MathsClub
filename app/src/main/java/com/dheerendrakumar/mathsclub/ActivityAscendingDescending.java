package com.dheerendrakumar.mathsclub;

import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ActivityAscendingDescending extends AppCompatActivity {


    String[] arr = {"ascending","descending"};
    Button goButton;
    ArrayList<Integer> answers = new ArrayList<>();
    ArrayList<Integer> userAnswer = new ArrayList<>();
    int score = 0;
    int numberOfQuestions = 0;
    TextView scoreTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    TextView sumTextView;
    TextView timerTextView;
    LinearLayout gameLayout;
    Button playAgainButton;
    int idx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ascending_descending);

        sumTextView = findViewById(R.id.questiontxt);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        playAgainButton = findViewById(R.id.playAgainButton);

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
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
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);


        new CountDownTimer(30100,1000) {

            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                playAgainButton.setVisibility(View.VISIBLE);

            }
        }.start();
    }

    public void chooseAnswer(View view) {

        Button button = (Button)view;

        if(button.getTag().toString().equals("1")) {
            button.setBackgroundColor(getResources().getColor(R.color.red));
           button.setTag("0");
           userAnswer.remove(new Integer(Integer.parseInt(button.getText().toString())));
        } else {
            button.setTag("1");
            button.setBackgroundColor(getResources().getColor(R.color.disable));
            userAnswer.add(Integer.parseInt(button.getText().toString()));
            if (answers.size()==userAnswer.size()) {
                if(answers.equals(userAnswer)) {
                    score++;
                    numberOfQuestions++;
                    scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
                    newQuestion();
                } else {
                    button0.setTag("0");
                    button1.setTag("0");
                    button2.setTag("0");
                    button3.setTag("0");
                    button4.setTag("0");
                    button5.setTag("0");
                    button0.setBackgroundColor(getResources().getColor(R.color.red));
                    button1.setBackgroundColor(getResources().getColor(R.color.red));
                    button2.setBackgroundColor(getResources().getColor(R.color.red));
                    button3.setBackgroundColor(getResources().getColor(R.color.red));
                    button4.setBackgroundColor(getResources().getColor(R.color.red));
                    button5.setBackgroundColor(getResources().getColor(R.color.red));

                    userAnswer.clear();
                }
            }
        }

    }

    public void start(View view) {
        goButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.timerTextView));
    }

    public void newQuestion() {

        button0.setTag("0");
        button1.setTag("0");
        button2.setTag("0");
        button3.setTag("0");
        button4.setTag("0");
        button5.setTag("0");
        button0.setBackgroundColor(getResources().getColor(R.color.red));
        button1.setBackgroundColor(getResources().getColor(R.color.red));
        button2.setBackgroundColor(getResources().getColor(R.color.red));
        button3.setBackgroundColor(getResources().getColor(R.color.red));
        button4.setBackgroundColor(getResources().getColor(R.color.red));
        button5.setBackgroundColor(getResources().getColor(R.color.red));

        Random rand = new Random();

        int rndm = rand.nextInt(2);
        int a = rand.nextInt(100);
        int b = rand.nextInt(100);
        String maxOrmin = arr[rndm];

        sumTextView.setText("Find the "+ maxOrmin+":");

        answers.clear();
        userAnswer.clear();
        idx=0;


        for(int i=0;i<3;i++) {
            int m = a+i;
            while(answers.contains(m)) {
                m = rand.nextInt(100);
            }
            answers.add(m);
        }

        for(int j=0;j<3;j++) {
            int m = b+j;
            while(answers.contains(m)) {
                m = rand.nextInt(100);
            }
            answers.add(m);
        }

        Collections.shuffle(answers);

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
        button4.setText(Integer.toString(answers.get(4)));
        button5.setText(Integer.toString(answers.get(5)));

        if(maxOrmin.equals("ascending")) {
            Collections.sort(answers);
        } else {
            Collections.sort(answers,Collections.reverseOrder());
        }

    }

}