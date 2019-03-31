package com.example.gardenspy;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //setup text labels
        final TextView timeLabel = findViewById(R.id.timeLabel);
        final TextView vaporLabel = findViewById(R.id.vaporLabel);
        final TextView dewLabel = findViewById(R.id.dewLabel);
        final TextView moistLabel = findViewById(R.id.moistLabel);
        final TextView humLabel = findViewById(R.id.humLabel);
        final TextView satVapLabel = findViewById(R.id.satVapLabel);
        final TextView tempLabel = findViewById(R.id.tempLabel);
        final TextView dateLabel = findViewById(R.id.dateLabel);

//        timeLabel.setText("hello");

        //creating database instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = database.getReference("user/key/plants/reaper");
//        FirebaseDatabase data = new FirebaseDatabase("test-5487a/user/key/plants/reaper");
//        mRef = new FirebaseDatabase("https://test-5487a.firebaseio.com/");
//
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String temp = Objects.requireNonNull(Objects.requireNonNull(dataSnapshot.child("temp").child("current").getValue())).toString();
                tempLabel.setText(String.format("%s F", temp));

                String time = Objects.requireNonNull(dataSnapshot.child("time").child("current").getValue()).toString();
                timeLabel.setText(time);

                String vapor = Objects.requireNonNull(dataSnapshot.child("actualvapor").child("current").getValue()).toString();
                vaporLabel.setText(vapor);

                String dew = Objects.requireNonNull(dataSnapshot.child("dewpoint").child("current").getValue()).toString();
                dewLabel.setText(String.format("%s F", dew));

                String moisture = Objects.requireNonNull(dataSnapshot.child("moisture").child("current").getValue()).toString();
                moistLabel.setText(moisture);

                String humidity = Objects.requireNonNull(dataSnapshot.child("humid").child("current").getValue()).toString();
                humLabel.setText(String.format("%s %%", humidity));

                String satVap = Objects.requireNonNull(dataSnapshot.child("saturatedvapor").child("current").getValue()).toString();
                satVapLabel.setText(satVap);

                String date = Objects.requireNonNull(dataSnapshot.child("date").child("current").getValue()).toString();
                dateLabel.setText(date);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("error");
            }
        });

    }
}
