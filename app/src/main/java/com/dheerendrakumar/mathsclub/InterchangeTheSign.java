package com.dheerendrakumar.mathsclub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
    LinearLayout gameLayout;
    int correctAnswer=0;
    Button playAgainButton;
    int quesCount = 1;
    Button val1;
    Button val2;
    Button val3;
    Button val4;
    SharedPreferences sharedPreferences;
    int s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interchange_the_sign);

        sumTextView = findViewById(R.id.questiontxt);
        scoreTextView = findViewById(R.id.scoreTextView);
        gameLayout = findViewById(R.id.gameLayout);
        goButton = findViewById(R.id.goButton);
        playAgainButton = findViewById(R.id.playAgainButton);
        val1 = findViewById(R.id.val1);
        val2 = findViewById(R.id.val2);
        val3 = findViewById(R.id.val3);
        val4 = findViewById(R.id.val4);

        goButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);

        sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
        s = sharedPreferences.getInt("rs",0);


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
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        newQuestion();
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

            sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
            sharedPreferences.edit().putInt("rs",score+s).apply();
            popup(button);

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

    public  void popup(View view) {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.inflator, null);
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                TextView time = (TextView) popupView.findViewById(R.id.time);
                time.setVisibility(View.GONE);
                TextView question = (TextView) popupView.findViewById(R.id.totalQues);
                question.setText("Total Questions: "+numberOfQuestions+"");
                TextView correct = (TextView) popupView.findViewById(R.id.corrrect);
                correct.setText("Correct: "+score+"");
                TextView incorrect = (TextView) popupView.findViewById(R.id.incorrect);
                int ic = numberOfQuestions-score;
                incorrect.setText("Incorrect: "+ic+"");

                popupWindow.showAtLocation(findViewById(R.id.ml), Gravity.CENTER, 0, 0);

                Button finish = (Button) popupView.findViewById(R.id.finish);
                finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        finish();
                        Intent intent = new Intent(InterchangeTheSign.this,ActivityReasoning.class);
                        startActivity(intent);
                    }
                });


            }
        },1000);

    }

}