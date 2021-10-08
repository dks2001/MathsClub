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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
        setContentView(R.layout.activity_ascending_descending);

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(ActivityAscendingDescending.this, R.raw.click, 1);

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
                        Toast.makeText(ActivityAscendingDescending.this, "Maximum time 3 minutes", Toast.LENGTH_SHORT).show();
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
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);


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
                        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
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
        timeEdt.setVisibility(View.INVISIBLE);
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

        sumTextView.setText("Arrange in "+ maxOrmin+":");

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(i==1) {
            countDownTimer.cancel();
        }
    }
}