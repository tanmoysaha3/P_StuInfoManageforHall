package com.example.pstuinfomanageforhall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword, mPhone, mStudentID;
    Button mRegisterButton;
    TextView mLoginTextButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar mProgressBar;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName=findViewById(R.id.fullName);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mPhone=findViewById(R.id.phone);
        mStudentID=findViewById(R.id.StudentID);

        mRegisterButton=findViewById(R.id.registerButton);
        mLoginTextButton=findViewById(R.id.loginText);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        mProgressBar=findViewById(R.id.registerProgressBar);

        if(fAuth.getCurrentUser() !=null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String studentID=mStudentID.getText().toString().trim();
                String fullName = mFullName.getText().toString();
                String phone = mPhone.getText().toString();

                //String split_second = email.substring(email.indexOf("@")+1);

                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }

//                if((!"student.just.edu.bd".equals(split_second)) && (!"just.edu.bd".equals(split_second))) {
//                    mEmail.setError("Enter university email");
//                    return;
//                }

                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }

                if(password.length()<8) {
                    mPassword.setError("Password length at least 8");
                    return;
                }

                if(TextUtils.isEmpty(studentID)) {
                    mStudentID.setError("Student ID is required");
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                //register the user in Firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Register.this, "Account created", Toast.LENGTH_SHORT).show();

                        //send verification email
                        FirebaseUser fuser = fAuth.getCurrentUser();
                        fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Register.this, "Verifivation email has been sent", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", "onFailure: Email not sent."+e.getMessage());
                            }
                        });

                        userID = fAuth.getCurrentUser().getUid();

                        DocumentReference documentReference = fStore.collection("users").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("fullName",fullName);
                        user.put("email",email);
                        user.put("studentID",studentID);
                        user.put("phone",phone);
                        //Specify if  the user is admin
                        user.put("isUser","1");
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG","onSuccess : user profile is created for " + userID);
                            }
                        });

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility((View.GONE));
                    }
                });

                /*fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            //send verification email
                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this, "Verifivation email has been sent", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: Email not sent."+e.getMessage());
                                }
                            });
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();

                            userID = fAuth.getCurrentUser().getUid();

                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fullName",fullName);
                            user.put("email",email);
                            user.put("studentID",studentID);
                            user.put("phone",phone);
                            //Specify if  the user is admin
                            user.put("isUser","1");
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG","onSuccess : user profile is created for " + userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(Register.this, "Error!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility((View.GONE));
                        }
                    }
                });*/
            }
        });

        mLoginTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}