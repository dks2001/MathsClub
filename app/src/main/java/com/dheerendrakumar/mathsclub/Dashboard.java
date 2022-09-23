package com.dheerendrakumar.mathsclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Dashboard extends AppCompatActivity {

    ImageView pic;
    TextView name, rank, totalScore;
    TextView username, warmup, gameZone, reasoning;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    ProgressDialog progress;
    String usernameSet = "";
    ArrayList<LearderboardObject> lo = new ArrayList<>();
    LinearLayout warmupHistory, gamezoneHistory, reasoningHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        warmupHistory = findViewById(R.id.warmupHistory);
        gamezoneHistory = findViewById(R.id.gamezoneHistory);
        reasoningHistory = findViewById(R.id.reasoningHistory);
        warmup = findViewById(R.id.warmupScoreShow);
        reasoning = findViewById(R.id.reasoningScoreShow);
        gameZone = findViewById(R.id.gameZoneScoreShow);
        rank = findViewById(R.id.rank);
        totalScore = findViewById(R.id.totalScore);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        pic = findViewById(R.id.profiledash);
        name = findViewById(R.id.namedash);
        username = findViewById(R.id.usernamedash);

        progress = new ProgressDialog(Dashboard.this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        DocumentReference docRef = db.collection("users").document(mAuth.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String imageUrl = document.getString("imageUrl");

                        if (imageUrl == null) {

                            pic.setImageResource(R.drawable.profile_gray);

                        } else {
                            Picasso.with(Dashboard.this).load(imageUrl).into(pic);

                        }

                        name.setText(document.getString("name"));
                        usernameSet = document.getString("username");
                        username.setText(usernameSet);
                        String wu = document.getString("warmUpScore");
                        String gz = document.getString("gameZoneScore");
                        String rs = document.getString("ReasoningScore");


                        if (wu != null) {
                            warmup.setText(wu);
                        }
                        if (gz != null) {
                            gameZone.setText(gz);
                        }
                        if (rs != null) {
                            reasoning.setText(rs);
                        }

                    } else {
                        //Log.d("", "No such document");
                        Toast.makeText(Dashboard.this, "user not exixt", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Log.d("", "get failed with ", task.getException());
                    Toast.makeText(Dashboard.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        warmupHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, ActivityHistory.class);
                intent.putExtra("category", "warmUp");
                startActivity(intent);
            }
        });

        gamezoneHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, ActivityHistory.class);
                intent.putExtra("category", "gameZone");
                startActivity(intent);
            }
        });

        reasoningHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, ActivityHistory.class);
                intent.putExtra("category", "Reasoning");
                startActivity(intent);
            }
        });

    }

    public void getRank() {


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                Collections.sort(lo, new Comparator<LearderboardObject>() {
                    public int compare(LearderboardObject l1, LearderboardObject l2) {
                        return Integer.valueOf(l1.getScore()).compareTo(l2.getScore());
                    }
                });

                Collections.reverse(lo);


                for (int i = 0; i < lo.size(); i++) {
                    LearderboardObject l = lo.get(i);
                    System.out.println(l.getUsername() + "....." + usernameSet);
                    if (usernameSet.equals(l.getUsername())) {
                        int Rank = i + 1;
                        rank.setText("Rank  " + Rank + "");
                        totalScore.setText("Total Score  " + l.getScore());
                        progress.dismiss();
                        break;
                    }
                }

            }
        },2000);



        System.out.println("llllllllllllllllllllll"+lo.size());


    }

    @Override
    protected void onStart() {
        super.onStart();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String name = document.getString("name");
                                String username = document.getString("username");
                                String score = document.getString("totalScore");
                                String imageUrl = document.getString("imageUrl");

                                lo.add(new LearderboardObject(name, username, Integer.parseInt(score), imageUrl));
                            }

                            getRank();

                        } else {
                            progress.dismiss();
                            Toast.makeText(Dashboard.this, "Cannot retrieve score at the moment", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}