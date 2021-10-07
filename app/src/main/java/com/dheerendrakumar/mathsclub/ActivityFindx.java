package com.dheerendrakumar.mathsclub;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class ActivityFindx extends AppCompatActivity {

    String[] signs = {"+","-","*"};

    ArrayList<Integer> answers = new ArrayList<>();
    Button goButton;
    Button playAgainButton;
    int score = 0;
    EditText timeEdt;
    int numberOfQuestions = 0;
    TextView scoreTextView;
    TextView timerTextView;
    LinearLayout gameLayout;

    Button val1;
    Button val2;
    Button val3;
    Button val4;
    TextView valueOfx;
    TextView expression;
    CountDownTimer countDownTimer;

    String t = "60100";
    String answer = "";
    int pos;
    int i=0;
    SharedPreferences sharedPreferences;
    int s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findx);

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        gameLayout = findViewById(R.id.gameLayout);
        goButton = findViewById(R.id.goButton);
        val1 = findViewById(R.id.val1);
        val2 = findViewById(R.id.val2);
        val3 = findViewById(R.id.val3);
        val4 = findViewById(R.id.val4);
        valueOfx = findViewById(R.id.valueOfx);
        expression = findViewById(R.id.expresion);
        playAgainButton = findViewById(R.id.playAgainButton);
        timeEdt = findViewById(R.id.timeEdt);

        goButton.setVisibility(View.VISIBLE);
        timeEdt.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);

        sharedPreferences = getApplicationContext().getSharedPreferences("Private Mode",MODE_PRIVATE);
        s = sharedPreferences.getInt("gzs",0);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!timeEdt.getText().toString().equals("")) {

                    if(Integer.parseInt(timeEdt.getText().toString()) > 200) {
                        Toast.makeText(ActivityFindx.this, "Maximum time 3 minutes", Toast.LENGTH_SHORT).show();
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
                sharedPreferences.edit().putInt("gzs",score+s).apply();

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
                                Intent intent = new Intent(ActivityFindx.this,ActivityGameZone.class);
                                startActivity(intent);
                            }
                        });


                    }
                },2000);


            }
        }.start();
    }

    public void chooseAnswer(View view) {
        if (Integer.toString(pos).equals(view.getTag().toString())) {
            score++;
        } else {

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

        int ans1=0;
        answer = "";
        int exp=0;
        answers.clear();
        pos=0;

        Random rand = new Random();

        int a = rand.nextInt(11);
        int b = rand.nextInt(11);
        int x = rand.nextInt(11);
        int c1 = rand.nextInt(3);

        String randSign = signs[c1];



        valueOfx.setText("If x ="+x+" then:");
        expression.setText(a+"x"+randSign+b+" =?");




        exp = a*x;

        if(randSign.equals("*")) {
            ans1 = b*exp;
        } else if(randSign.equals("+")) {
            ans1 = exp+b;
        } else {
            ans1 = exp-b;
        }

        answer = Integer.toString(ans1);
        answers.add(ans1);

        for(int i=0;i<3;i++) {
            answers.add(ans1+i+1);
        }

        Collections.shuffle(answers);
        pos = answers.indexOf(ans1);


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