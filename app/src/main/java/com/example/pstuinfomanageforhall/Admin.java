package com.example.pstuinfomanageforhall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Admin extends AppCompatActivity {

    Button mAddSAdminButton, mSeeAllUser, mCreateHallButton, mCreateFloorButton, mCreateRoomButton,
            mCreateSeatButton;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mCreateHallButton=findViewById(R.id.createHallButton);
        mCreateFloorButton=findViewById(R.id.createFloorButton);
        mCreateRoomButton=findViewById(R.id.createRoomButton);
        mCreateSeatButton=findViewById(R.id.createSeatButton);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userID=fAuth.getCurrentUser().getUid();
        fUser=fAuth.getCurrentUser();

        mCreateHallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddHall.class));
            }
        });

        mCreateFloorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddFloor.class));
            }
        });

        mCreateRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddRoom.class));
            }
        });

        mCreateSeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddSeat.class));
            }
        });

        /*Intent data = getIntent();
        String isSubAdmin = data.getStringExtra("isSubAdmin");

        mAddSAdminButton=findViewById(R.id.addSubAdminButton);


        mAddSAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference=fStore.collection("users").document(userID);
            }
        });

        mSeeAllUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
// See the UserRecord reference doc for the contents of userRecord.
                System.out.println("Successfully fetched user data: " + userRecord.getUid());*//*

            }
        });

        ApiFuture<QuerySnapshot> future = db.collection("cities").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            System.out.println(document.getId() + " => " + document.toObject(City.class));
        }


    }*/
    }

    public void logoutAdmin(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}