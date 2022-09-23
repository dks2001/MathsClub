package com.dheerendrakumar.mathsclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAccount extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String name;
    String email;
    String username;
    TextView namet;
    TextView usernamet;
    String imageUrl;
    EditText emailContact,contentContact;
    ImageView profilePic;
    LinearLayout sl,hl;

    HashMap<String,String> co;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        namet = findViewById(R.id.name);
        usernamet = findViewById(R.id.username);
        profilePic = findViewById(R.id.accountProfilePic);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String id = mAuth.getUid();

        DocumentReference docRef = db.collection("users").document(mAuth.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        name = document.getString("name");
                        username = document.getString("username");
                        email = document.getString("email");
                        imageUrl = document.getString("imageUrl");

                        if (imageUrl == null) {

                            profilePic.setImageResource(R.drawable.profile_gray);

                        } else {
                            Picasso.with(MyAccount.this).load(imageUrl).into(profilePic);

                        }

                        namet.setText(name);
                        usernamet.setText(username);


                    } else {
                        //Log.d("", "No such document");
                        Toast.makeText(MyAccount.this, "user not exixt", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Log.d("", "get failed with ", task.getException());
                    Toast.makeText(MyAccount.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });



        TextView profile = findViewById(R.id.myprofile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccount.this,MyProfile.class);
                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("username",username);
                intent.putExtra("imageUrl",imageUrl);

                startActivity(intent);
            }
        });


        TextView share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Maths Club");
                    String shareMessage= "\nEnhance your knowledge of calculations and reasoning only on Maths Club.\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Send Via..."));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        TextView logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(MyAccount.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        TextView dashboard = findViewById(R.id.dashboard);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccount.this,Dashboard.class);
                startActivity(intent);
            }
        });

        TextView leaderboard = findViewById(R.id.leaderboard);
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccount.this,Leaderboard.class);
                startActivity(intent);
            }
        });



        TextView changePassword = findViewById(R.id.changepwd);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true;

                LayoutInflater changePasswordinflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View changePasswordView = changePasswordinflator.inflate(R.layout.change_password,null);

                PopupWindow changePasswordPopup = new PopupWindow(changePasswordView,width,height,focusable);

                EditText oldPassword = (EditText) changePasswordView.findViewById(R.id.oldPassword);
                EditText newPassword = (EditText) changePasswordView.findViewById(R.id.newPassword);
                EditText confirmNewPassword = (EditText) changePasswordView.findViewById(R.id.confirmNewPassword);

                Button confirmPasswordChange = (Button) changePasswordView.findViewById(R.id.confirmPasswordChange);





                changePasswordPopup.showAtLocation(changePasswordView, Gravity.CENTER,0,0);

                

                confirmPasswordChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(oldPassword.getText().toString().equals("")) {
                            Toast.makeText(MyAccount.this, "Old Password cannot be empty.", Toast.LENGTH_SHORT).show();
                        } else if(newPassword.getText().toString().equals("")) {
                            Toast.makeText(MyAccount.this, "New password cannot be empty", Toast.LENGTH_SHORT).show();
                        } else if(confirmNewPassword.getText().toString().equals("")) {
                            Toast.makeText(MyAccount.this, "confirm new password", Toast.LENGTH_SHORT).show();
                        } else if(!newPassword.getText().toString().equals(confirmNewPassword.getText().toString())) {
                            Toast.makeText(MyAccount.this, "Re enter new password", Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            AuthCredential credential = EmailAuthProvider.getCredential(mAuth.getCurrentUser().getEmail(),oldPassword.getText().toString());

                            firebaseUser.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()) {
                                                firebaseUser.updatePassword(confirmNewPassword.getText().toString())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()) {
                                                                    Toast.makeText(MyAccount.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                                                    changePasswordPopup.dismiss();
                                                                } else {
                                                                    Toast.makeText(MyAccount.this, "Error...Try Again Later", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            } else {
                                                Toast.makeText(MyAccount.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });

            }
        });


        TextView contact = findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                co = new HashMap<>();

                db.collection("contact").document("contactUs")
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            co =  (HashMap<String,String>)documentSnapshot.get("feedback");
                            if(co == null) {
                                co = new HashMap<>();
                            }
                        }

                    }
                });




                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true;

                LayoutInflater contactInflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View changePasswordView = contactInflator.inflate(R.layout.contact,null);

                PopupWindow changePasswordPopup = new PopupWindow(changePasswordView,width,height,focusable);

                emailContact = (EditText) changePasswordView.findViewById(R.id.contact_email);
                contentContact = (EditText) changePasswordView.findViewById(R.id.contact_content);
                Button submitQuery = (Button) changePasswordView.findViewById(R.id.submitQuery);
                sl = (LinearLayout) changePasswordView.findViewById(R.id.showContactLayout);
                hl = (LinearLayout) changePasswordView.findViewById(R.id.hideContactLayout);

                changePasswordPopup.showAtLocation(changePasswordView, Gravity.CENTER,0,0);


                submitQuery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String emaill = emailContact.getText().toString();
                        String content = contentContact.getText().toString();

                        if(emaill.equals("")) {
                            Toast.makeText(MyAccount.this, "Enter email id", Toast.LENGTH_SHORT).show();
                        } else if(contact.equals("")) {
                            Toast.makeText(MyAccount.this, "Please write your query", Toast.LENGTH_SHORT).show();
                        } else {
                            co.put(email+"",content);
                            HashMap<String,HashMap<String,String>> hm = new HashMap<>();
                            hm.put("feedback",co);



                            db.collection("contact").document("contactUs")
                                    .set(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    sl.setVisibility(View.GONE);
                                    hl.setVisibility(View.VISIBLE);
                                }
                            });
                        }



                    }
                });


            }
        });
    }
}