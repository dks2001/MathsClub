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

public class UseFormula extends AppCompatActivity {

    String[] formulas = {"lb+bh+hl","lxbxh","a^2+b^2","lx(b+h)"};

    ArrayList<Integer> answers = new ArrayList<>();
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
    TextView values;
    TextView formula;
    CountDownTimer countDownTimer;
    String answer = "";
    EditText timeEdt;
    String t = "60100";
    int i=0;
    SharedPreferences sharedPreferences;
    int s;
    SoundPool soundPool;
    int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_formula);

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(UseFormula.this, R.raw.click, 1);

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        gameLayout = findViewById(R.id.gameLayout);
        goButton = findViewById(R.id.goButton);
        val1 = findViewById(R.id.val1);
        val2 = findViewById(R.id.val2);
        val3 = findViewById(R.id.val3);
        val4 = findViewById(R.id.val4);
        values = findViewById(R.id.values);
        formula = findViewById(R.id.formula);
        playAgainButton = findViewById(R.id.playAgainButton);
        timeEdt = findViewById(R.id.timeEdt);
        timeEdt.setVisibility(View.VISIBLE);
        sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
        s = sharedPreferences.getInt("wus",0);

        goButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!timeEdt.getText().toString().equals("")) {

                    if(Integer.parseInt(timeEdt.getText().toString()) > 200) {
                        Toast.makeText(UseFormula.this, "Maximum time 3 minutes", Toast.LENGTH_SHORT).show();
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
        playAgainButton.setVisibility(View.INVISIBLE);
        val1.setEnabled(true);
        val2.setEnabled(true);
        val3.setEnabled(true);
        val4.setEnabled(true);
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        newQuestion();

        countDownTimer = new CountDownTimer(Integer.parseInt(t),1000) {

            @Override
            public void onTick(long l) {
                i=1;
                timerTextView.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {

                sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
                sharedPreferences.edit().putInt("wus",score+s).apply();

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

        Button button = (Button)view;

        if (answer.equals(button.getText().toString())) {
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

        answer = "";
        answers.clear();
        int ans=0;

        Random rand = new Random();
        int a = rand.nextInt(4);

        if(a==0) {

            int l = rand.nextInt(10)+1;
            int b = rand.nextInt(10)+1;
            int h = rand.nextInt(10)+1;

            formula.setText("Answer = "+formulas[0]+"");
            values.setText("l = "+l+"\n"+"b = "+b+"\n"+"h = "+h);
            ans = l*b+b*h+h*l;
            answer = ans+"";
            answers.add(ans);

        } else if(a==1) {

            int l = rand.nextInt(10)+1;
            int b = rand.nextInt(10)+1;
            int h = rand.nextInt(10)+1;

            formula.setText("Answer = "+formulas[1]+"");
            values.setText("l = "+l+"\n"+"b = "+b+"\n"+"h = "+h);

            ans = l*b*h;
            answer = ans+"";
            answers.add(ans);

        } else if(a==2) {

            int c = rand.nextInt(10)+1;
            int b = rand.nextInt(10)+1;

            formula.setText("Answer = "+formulas[2]+"");
            values.setText("a = "+c+"\n"+"b = "+b);
            ans = c*c + b*b;
            answer = ans+"";
            answers.add(ans);

        } else if(a==3) {

            int l = rand.nextInt(10)+1;
            int b = rand.nextInt(10)+1;
            int h = rand.nextInt(10)+1;

            formula.setText("Answer = "+formulas[3]+"");
            values.setText("l = "+l+"\n"+"b = "+b+"\n"+"h = "+h);

            ans = l*(b+h);
            answer = ans+"";
            answers.add(ans);


        }



        for(int i=0;i<3;i++) {
            int m = rand.nextInt(ans)+20;
            while(answers.contains(m)) {
                m = rand.nextInt(ans)+20;
            }
            answers.add(m);
        }

        Collections.shuffle(answers);


        val1.setText(Integer.toString(answers.get(0)));
        val2.setText(Integer.toString(answers.get(1)));
        val3.setText(Integer.toString(answers.get(2)));
        val4.setText(Integer.toString(answers.get(3)));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(i==1) {
            countDownTimer.cancel();
        }
    }
}