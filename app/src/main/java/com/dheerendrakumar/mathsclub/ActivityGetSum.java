package com.dheerendrakumar.mathsclub;

import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ActivityGetSum extends AppCompatActivity {

    Button goButton;
    ArrayList<Integer> answers = new ArrayList<>();
    int score = 0;
    int numberOfQuestions = 0;
    TextView scoreTextView;
    Button button0;
    Button button1;
    int ans=0;
    int userAnswer=0;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button10;
    Button button11;
    TextView sumTextView;
    TextView timerTextView;
    LinearLayout gameLayout;
    int idx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sum);

        sumTextView = findViewById(R.id.questiontxt);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button10 = findViewById(R.id.button10);
        button11 = findViewById(R.id.button11);
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
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(true);
        button9.setEnabled(true);
        button10.setEnabled(true);
        button11.setEnabled(true);

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
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
                button10.setEnabled(false);
                button11.setEnabled(false);
            }
        }.start();
    }

    public void chooseAnswer(View view) {

        Button button = (Button)view;

        if(button.getTag().toString().equals("1")) {
            button.setBackgroundColor(getResources().getColor(R.color.red));
            button.setTag("0");
            userAnswer -= Integer.parseInt(button.getText().toString());

        } else {
            userAnswer += Integer.parseInt(button.getText().toString());
            button.setTag("1");
            button.setBackgroundColor(getResources().getColor(R.color.disable));
            if(ans==userAnswer) {
                score++;
                numberOfQuestions++;
                scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
                newQuestion();
            } else if(userAnswer>ans){
                button0.setTag("0");
                button1.setTag("0");
                button2.setTag("0");
                button3.setTag("0");
                button4.setTag("0");
                button5.setTag("0");
                button6.setTag("0");
                button7.setTag("0");
                button8.setTag("0");
                button9.setTag("0");
                button10.setTag("0");
                button11.setTag("0");
                button0.setBackgroundColor(getResources().getColor(R.color.red));
                button1.setBackgroundColor(getResources().getColor(R.color.red));
                button2.setBackgroundColor(getResources().getColor(R.color.red));
                button3.setBackgroundColor(getResources().getColor(R.color.red));
                button4.setBackgroundColor(getResources().getColor(R.color.red));
                button5.setBackgroundColor(getResources().getColor(R.color.red));
                button6.setBackgroundColor(getResources().getColor(R.color.red));
                button7.setBackgroundColor(getResources().getColor(R.color.red));
                button8.setBackgroundColor(getResources().getColor(R.color.red));
                button9.setBackgroundColor(getResources().getColor(R.color.red));
                button10.setBackgroundColor(getResources().getColor(R.color.red));
                button11.setBackgroundColor(getResources().getColor(R.color.red));

                userAnswer=0;
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
        button6.setTag("0");
        button7.setTag("0");
        button8.setTag("0");
        button9.setTag("0");
        button10.setTag("0");
        button11.setTag("0");
        button0.setBackgroundColor(getResources().getColor(R.color.red));
        button1.setBackgroundColor(getResources().getColor(R.color.red));
        button2.setBackgroundColor(getResources().getColor(R.color.red));
        button3.setBackgroundColor(getResources().getColor(R.color.red));
        button4.setBackgroundColor(getResources().getColor(R.color.red));
        button5.setBackgroundColor(getResources().getColor(R.color.red));
        button6.setBackgroundColor(getResources().getColor(R.color.red));
        button7.setBackgroundColor(getResources().getColor(R.color.red));
        button8.setBackgroundColor(getResources().getColor(R.color.red));
        button9.setBackgroundColor(getResources().getColor(R.color.red));
        button10.setBackgroundColor(getResources().getColor(R.color.red));
        button11.setBackgroundColor(getResources().getColor(R.color.red));

        Random rand = new Random();
        ans=0;
        userAnswer=0;

        int r1 = rand.nextInt(3)+1;
        ArrayList<Integer> random = new ArrayList<>();

        for(int i=0;i<r1;i++) {
            int num = rand.nextInt(11);
            while(random.contains(num)) {
                num = rand.nextInt(11);
            }
            random.add(num);
        }

        int a = rand.nextInt(10);
        int b = rand.nextInt(10);



        answers.clear();
        idx=0;


        for(int i=0;i<6;i++) {
            answers.add(a+i);
        }
        for(int j=0;j<6;j++) {
            answers.add(b+j);
        }

        Collections.shuffle(answers);

        for(int k=0;k<random.size();k++) {
            ans += answers.get(random.get(k));
        }

        sumTextView.setText(ans+"");

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
        button4.setText(Integer.toString(answers.get(4)));
        button5.setText(Integer.toString(answers.get(5)));
        button6.setText(Integer.toString(answers.get(6)));
        button7.setText(Integer.toString(answers.get(7)));
        button8.setText(Integer.toString(answers.get(8)));
        button9.setText(Integer.toString(answers.get(9)));
        button10.setText(Integer.toString(answers.get(10)));
        button11.setText(Integer.toString(answers.get(11)));


    }

}