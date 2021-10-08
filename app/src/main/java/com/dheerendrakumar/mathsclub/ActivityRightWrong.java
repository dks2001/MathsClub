package com.dheerendrakumar.mathsclub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.drm.DrmStore;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
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

public class ActivityRightWrong extends AppCompatActivity {

    String[] signs = {"+","-","*"};

    Button goButton;
    int r;
    ArrayList<Integer> answers = new ArrayList<>();
    int locationOfCorrectAnswer;
    int answerShowed;
    int score = 0;
    int numberOfQuestions = 0;
    TextView scoreTextView;
    ImageView right;
    ImageView wrong;
    TextView sumTextView;
    TextView timerTextView;
    Button playAgainButton;
    LinearLayout gameLayout;
    EditText timeEdt;
    String t = "60100";
    CountDownTimer countDownTimer;
    int i=0;
    SharedPreferences sharedPreferences;
    int s;
    SoundPool soundPool;
    int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_wrong);

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(ActivityRightWrong.this, R.raw.click, 1);

        sumTextView = findViewById(R.id.expressionRightWrong);
        right = findViewById(R.id.rightImageView);
        wrong = findViewById(R.id.wrongImageView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        gameLayout = findViewById(R.id.gameLayout);
        goButton = findViewById(R.id.goButton);
        timeEdt = findViewById(R.id.timeEdt);
        timeEdt.setVisibility(View.VISIBLE);

        goButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);

        sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
        s = sharedPreferences.getInt("gzs",0);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!timeEdt.getText().toString().equals("")) {

                    if(Integer.parseInt(timeEdt.getText().toString()) > 200) {
                        Toast.makeText(ActivityRightWrong.this, "Maximum time 3 minutes", Toast.LENGTH_SHORT).show();
                    } else {
                        t = timeEdt.getText().toString()+"100";
                        start(goButton);
                    }


                } else {
                    start(goButton);
                }
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
        right.setEnabled(true);
        wrong.setEnabled(true);

        countDownTimer = new CountDownTimer(Integer.parseInt(t),1000) {

            @Override
            public void onTick(long l) {
                i=1;
                timerTextView.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {

                sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
                sharedPreferences.edit().putInt("gzs",score+s).apply();

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
                        time.setText("Time: "+ t.substring(0,t.length()-3)+"s");
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
        }.start();
    }

    public void chooseAnswer(View view) {
        soundPool.play(soundId, 1, 1, 0, 0, 1);

        if (r==1 && view.getTag().toString().equals("true")) {
            score++;
        } else if(r==0 && view.getTag().toString().equals("false")){
            score++;
        }
        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        newQuestion();
    }

    public void start(View view) {
        goButton.setVisibility(View.INVISIBLE);
        timeEdt.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.timerTextView));
    }

    public void newQuestion() {
        Random rand = new Random();

        int a = rand.nextInt(50);
        int b = rand.nextInt(50);
        int c = rand.nextInt(3);

        String radSign = signs[c];
        r=0;


        locationOfCorrectAnswer = rand.nextInt(2);
        answerShowed = rand.nextInt(2);
        int ans=0;

        answers.clear();

        for (int i=0; i<2; i++) {
            if (i == locationOfCorrectAnswer) {
                if(radSign.equals("+")) {
                    ans = a+b;
                } else if(radSign.equals("*")) {
                    ans = a*b;
                } else {
                    ans = a-b;
                }
                answers.add(ans);
            } else {
                int wrongAnswer = rand.nextInt(41);

                while (wrongAnswer == ans) {
                    wrongAnswer = rand.nextInt(41);
                }

                answers.add(wrongAnswer);
            }

        }

        Collections.shuffle(answers);
        if(answers.get(answerShowed)==ans) {
            r=1;
        }

        sumTextView.setText(Integer.toString(a) + " "+radSign+" " + Integer.toString(b) +" = " +answers.get(answerShowed));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(i==1) {
            countDownTimer.cancel();
        }
    }
}