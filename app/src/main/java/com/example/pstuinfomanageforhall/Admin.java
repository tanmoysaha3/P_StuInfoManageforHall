package com.example.pstuinfomanageforhall;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Admin extends AppCompatActivity {


    Button mAddSAdminButton, mSeeAllUser;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        /*Intent data = getIntent();
        String isSubAdmin = data.getStringExtra("isSubAdmin");

        mAddSAdminButton=findViewById(R.id.addSubAdminButton);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userID=fAuth.getCurrentUser().getUid();
        fUser=fAuth.getCurrentUser();

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