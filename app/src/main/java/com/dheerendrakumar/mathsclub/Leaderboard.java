package com.dheerendrakumar.mathsclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Leaderboard extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    ArrayList<LearderboardObject> lo = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.leaderboardRecyclerAdapter);


        ProgressDialog progress = new ProgressDialog(Leaderboard.this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

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

                                        lo.add(new LearderboardObject(name,username,Integer.parseInt(score),imageUrl));
                                        //Log.d("", document.getId() + " => " + document.getData());
                                    }


                                    Collections.sort(lo, new Comparator<LearderboardObject>() {
                                        public int compare(LearderboardObject l1, LearderboardObject l2) {
                                            return Integer.valueOf(l1.getScore()).compareTo(l2.getScore());
                                        }
                                    });


                                    for(int i=0;i<lo.size();i++) {
                                        LearderboardObject l = lo.get(i);
                                        System.out.println(l.getScore());
                                    }

                                    recyclerView.setNestedScrollingEnabled(false);
                                    Collections.reverse(lo);
                                    recyclerView.setAdapter(new LeaderboardRecyclerAdapter(Leaderboard.this,lo));
                                    recyclerView.setLayoutManager(new LinearLayoutManager(Leaderboard.this));
                                    progress.dismiss();


                                } else {
                                    //Log.d("", "Error getting documents: ", task.getException());
                                    Toast.makeText(Leaderboard.this, "error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }) ;




            }
        },2000);




    }
}