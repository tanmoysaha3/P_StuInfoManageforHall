package com.example.pstuinfomanageforhall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
    TextView mName, mStudentId, mEmail, mPhone, mAddress;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    Button mChangePassButton, mChangeProfileButton;
    FirebaseUser user;
    ImageView mProfileImage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mName=findViewById(R.id.profileName);
        mStudentId=findViewById(R.id.profileStudentId);
        mEmail=findViewById(R.id.profileEmail);
        mPhone=findViewById(R.id.profilePhone);
        mAddress=findViewById(R.id.profileAddress);
        mChangePassButton=findViewById(R.id.changePassButton);
        mProfileImage=findViewById(R.id.profileImage);
        mChangeProfileButton=findViewById(R.id.changeProfileButton);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profileRef=storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mProfileImage);
            }
        });

        userID=fAuth.getCurrentUser().getUid();
        user=fAuth.getCurrentUser();

        DocumentReference documentReference=fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                mName.setText(documentSnapshot.getString("fullName"));
                mStudentId.setText(documentSnapshot.getString("studentID"));
                mEmail.setText(documentSnapshot.getString("email"));
                mPhone.setText(documentSnapshot.getString("phone"));
                mAddress.setText(documentSnapshot.getString("address"));
            }
        });

        mChangePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText changePass = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Change Password");
                passwordResetDialog.setMessage("Enter new password");
                passwordResetDialog.setView(changePass);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPass=changePass.getText().toString();
                        user.updatePassword(newPass).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Profile.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Profile.this, "Password change failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordResetDialog.create().show();
            }
        });

        mChangeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),EditProfile.class);
                i.putExtra("fullName",mName.getText().toString());
                i.putExtra("phone",mPhone.getText().toString());
                i.putExtra("address",mAddress.getText().toString());
                startActivity(i);
            }
        });
    }




}