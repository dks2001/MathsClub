package com.dheerendrakumar.mathsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ActivityGameZone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_zone);

        ImageView greaterOrLess = findViewById(R.id.greaterOrLess);
        greaterOrLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGameZone.this,GreaterSmaller.class);
                startActivity(intent);
            }
        });

        ImageView maxmin = findViewById(R.id.maxmin);
        maxmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGameZone.this,ActivityMaxMin.class);
                startActivity(intent);
            }
        });

        ImageView findx = findViewById(R.id.findx);
        findx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGameZone.this,ActivityFindx.class);
                startActivity(intent);
            }
        });

        ImageView rightWrong = findViewById(R.id.rightWrong);
        rightWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGameZone.this,ActivityRightWrong.class);
                startActivity(intent);
            }
        });

        ImageView ascDes = findViewById(R.id.acsendingDesc);
        ascDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGameZone.this,ActivityAscendingDescending.class);
                startActivity(intent);
            }
        });

        ImageView getsum = findViewById(R.id.getSum);
        getsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGameZone.this,ActivityGetSum.class);
                startActivity(intent);
            }
        });

        ImageView chalengeFriend = findViewById(R.id.challengeFriend);
        chalengeFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGameZone.this,ActivityChallengeFriend.class);
                startActivity(intent);
            }
        });

        ImageView fillBlank = findViewById(R.id.fillblanks);
        fillBlank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGameZone.this,ActivityFillBlanks.class);
                startActivity(intent);
            }
        });

        ImageView equation = findViewById(R.id.equation);
        equation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGameZone.this,ActivityEquation.class);
                startActivity(intent);
            }
        });
    }
}