package com.example.pstuinfomanageforhall;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SeeStuDetails extends AppCompatActivity {

    EditText mStuDetailsStuId;
    Button mSearchStuDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_stu_details);

        mStuDetailsStuId=findViewById(R.id.stuDetailsStuId);
        mSearchStuDetailsButton=findViewById(R.id.searchStuDetailsButton);

        mSearchStuDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),ShowStuDetails.class);
                i.putExtra("vStudentId",mStuDetailsStuId.getText().toString());
                startActivity(i);
            }
        });
    }
}