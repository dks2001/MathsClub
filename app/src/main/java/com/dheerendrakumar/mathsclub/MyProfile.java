package com.dheerendrakumar.mathsclub;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.UUID;

public class MyProfile extends AppCompatActivity {

    TextView name, email, username, changeprofile;
    Bitmap receivedImageBitmap;
    ImageView profileImageView;
    String imageIdentifier;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String imageDownloadLink;
    ImageView profile;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        progress = new ProgressDialog(MyProfile.this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        changeprofile = findViewById(R.id.changeprofile);
        profileImageView = findViewById(R.id.profilepic);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        profile = findViewById(R.id.profilepic);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        username.setText(intent.getStringExtra("username"));
        email.setText(intent.getStringExtra("email"));
        if(intent.getStringExtra("imageUrl") == null) {
            profile.setImageResource(R.drawable.profile_gray);
        } else {
            Picasso.with(MyProfile.this).load(intent.getStringExtra("imageUrl")).into(profile);
        }



        changeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(android.os.Build.VERSION.SDK_INT>=23 && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);

                } else {
                    getChosenImage();
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getChosenImage();
            }
        }
    }



    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        try {
                            progress.show();

                            Intent selectedImage = result.getData();
                            Uri imageUri = selectedImage.getData();



                            String[] filePathcolumn = {MediaStore.Images.Media.DATA};
                            Cursor cursor = getApplicationContext().getContentResolver().query(imageUri,filePathcolumn,null,null,null);
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathcolumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            cursor.close();

                            receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                            profileImageView.setImageBitmap(receivedImageBitmap);
                            profileImageView.setImageBitmap(receivedImageBitmap);
                            uploadImageToServer(receivedImageBitmap);


                            //Toast.makeText(MainActivity.this, new URL(picturePath.toString())+"", Toast.LENGTH_SHORT).show();
                        } catch(Exception e) {


                        }

                    }
                }
            });



    public void getChosenImage() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        someActivityResultLauncher.launch(intent);
    }


    private void uploadImageToServer(Bitmap receivedImageBitmap) {

        if(receivedImageBitmap !=null) {

            imageIdentifier = UUID.randomUUID() + ".png";

            // Get the data from an ImageView as bytes
            profileImageView.setDrawingCacheEnabled(true);
            profileImageView.buildDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            receivedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = FirebaseStorage.getInstance().getReference().child(mAuth.getUid()).child(imageIdentifier).putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(MyProfile.this, "error", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...

                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            imageDownloadLink = task.getResult().toString();
                            //Log.i("downloadlink",imageDownloadLink);
                            addImageDownLoadLink();
                        }

                    });
                }
            });
        }
    }


    public  void addImageDownLoadLink() {

        DocumentReference docRef = db.collection("users").document(mAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        HashMap<String,Object> res = (HashMap<String, Object>) document.getData();

                        res.put("imageUrl",imageDownloadLink);

                        db.collection("users").document(mAuth.getUid())
                                .set(res).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progress.dismiss();
                            }
                        });


                    } else {
                        // Log.d("", "No such document");
                        Toast.makeText(MyProfile.this, "not available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Log.d("", "get failed with ", task.getException());
                    Toast.makeText(MyProfile.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}