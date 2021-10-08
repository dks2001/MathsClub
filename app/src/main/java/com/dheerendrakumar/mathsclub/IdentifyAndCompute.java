package com.dheerendrakumar.mathsclub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
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

public class IdentifyAndCompute extends AppCompatActivity {

    String[] sign = {"@","$"};
    int[] roots = {2,3};
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
    Button val1,val2,val3,val4;
    SharedPreferences sharedPreferences;
    int s;
    String showHint="";
    TextView answer,hint;
    SoundPool soundPool;
    int soundId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_and_compute);

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(IdentifyAndCompute.this, R.raw.click, 1);

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

        answer = findViewById(R.id.answer);
        hint = findViewById(R.id.hint);

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

        soundPool.play(soundId, 1, 1, 0, 0, 1);
        Button button = (Button)view;

        if(button.getText().toString().equals("play again")) {
            playAgain(button);
        }

        if(quesCount==10) {
            if(Integer.toString(correctAnswer).equals(button.getText().toString())) {
                score++;
                scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            }
            //playAgainButton.setVisibility(View.VISIBLE);
            val1.setEnabled(false);
            val2.setEnabled(false);
            val3.setEnabled(false);
            val4.setEnabled(false);

            sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
            sharedPreferences.edit().putInt("rs",score+s).apply();
            popup(button);

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

        int r = rand.nextInt(2);
        int rndm = 0;
        int myRand = 0;

        if(r==0) {
            rndm = rand.nextInt(19)+1;
            myRand = rand.nextInt(19)+1;

            int sin = rand.nextInt(2);

            if(sin==0) {
                sumTextView.setText(rndm+" @ "+myRand);
                correctAnswer = rndm*rndm-myRand*myRand;
                showHint = rndm+" x "+rndm+" - "+myRand+" x "+myRand;
            } else {

                sumTextView.setText(rndm+" $ "+myRand);
                correctAnswer = rndm*rndm+myRand*myRand;
                showHint = rndm+" x "+rndm+" + "+myRand+" x "+myRand;
            }

        } else {
            rndm = rand.nextInt(8)+1;
            myRand = rand.nextInt(8)+1;

            int sin = rand.nextInt(2);

            if(sin==0) {
                sumTextView.setText(rndm+" @ "+myRand+" = ?");
                correctAnswer = rndm*rndm*rndm-myRand*myRand*myRand;
                showHint = rndm+" x "+rndm+" x "+rndm+" - "+myRand+" x "+myRand+" x "+myRand;
            } else {

                sumTextView.setText(rndm+" $ "+myRand+" = ?");
                correctAnswer = rndm*rndm*rndm+myRand*myRand*myRand;
                showHint = rndm+" x "+rndm+" x "+rndm+" + "+myRand+" x "+myRand+" x "+myRand;
            }
        }

        Log.i("correctAnswer",correctAnswer+"");

        answers.add(correctAnswer);
        answers.add(rndm*rndm);
        answers.add(myRand*myRand);
        answers.add(rndm-myRand);

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

            TextView showHintt = popupView.findViewById(R.id.answerHint);
            showHintt.setText("Evaluate : \n"+showHint);

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