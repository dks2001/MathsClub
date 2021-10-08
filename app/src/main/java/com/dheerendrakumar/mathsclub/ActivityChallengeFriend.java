package com.dheerendrakumar.mathsclub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
    int myRand;
    String usedSign;

    SharedPreferences sharedPreferences;
    int s;
    MediaPlayer mp = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_friend);

        mp = MediaPlayer.create(this, R.raw.click);

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

        sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
        s = sharedPreferences.getInt("gzs",0);


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
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        newQuestion();
    }

    public void chooseAnswer(View view) {

        mp.start();

        Button button = (Button)view;

        if(button.getText().toString().equals("skip")) {

            if(quesCount==10) {
                sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
                sharedPreferences.edit().putInt("gzs",score+s).apply();

                popup(button);
                skip.setEnabled(false);
                submit.setEnabled(false);
            } else {
                newQuestion();
            }

        } else {

            if(quesCount==10) {
                popup(button);
                sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
                sharedPreferences.edit().putInt("gzs",score+s).apply();
                //playAgainButton.setVisibility(View.VISIBLE);
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
        myRand = rand.nextInt(4)+2;

        int a = rand.nextInt(30);
        usedSign = sign[rndm];

        if(usedSign.equals("*")) {

            usedSign = "x";
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

    public void popup(View view) {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.inflator, null);
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = false; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                TextView time = (TextView) popupView.findViewById(R.id.time);
                time.setText("Time: "+"60s");
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
                        Intent intent = new Intent(ActivityChallengeFriend.this,ActivityGameZone.class);
                        startActivity(intent);
                    }
                });

            }
        },500);



    }

    public void showHintAndAnswer(View view) {

        TextView textView = (TextView) view;
        int id = textView.getId();

        if(id == R.id.answer) {

            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.hint_and_answer, null);
            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.MATCH_PARENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            TextView AOH = popupView.findViewById(R.id.AOH);
            AOH.setText("Answer");

            TextView showAnswer = popupView.findViewById(R.id.answerHint);
            showAnswer.setText(correctAnswer+"");

            ImageView close = (ImageView) popupView.findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

            popupWindow.showAtLocation(findViewById(R.id.ml), Gravity.CENTER, 0, 0);

        } else {

            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.hint_and_answer, null);
            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.MATCH_PARENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            TextView AOH = popupView.findViewById(R.id.AOH);
            AOH.setText("Hint");

            TextView showHint = popupView.findViewById(R.id.answerHint);
            showHint.setText("Try "+" "+usedSign+" "+myRand+" with all the letters of the series.");

            ImageView close = (ImageView) popupView.findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

            popupWindow.showAtLocation(findViewById(R.id.ml), Gravity.CENTER, 0, 0);

        }

    }

}