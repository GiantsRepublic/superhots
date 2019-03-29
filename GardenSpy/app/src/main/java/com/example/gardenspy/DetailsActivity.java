package com.example.gardenspy;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import com.google.firebase.auth.Firebase

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";
    private FirebaseDatabase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //setup text labels
        TextView timeLabel = (TextView) findViewById(R.id.timeLabel);
        TextView vaporLabel = (TextView) findViewById(R.id.vaporLabel);
        TextView dewLabel = (TextView) findViewById(R.id.dewLabel);
        TextView moistLabel = (TextView) findViewById(R.id.moistLabel);
        TextView humLabel = (TextView) findViewById(R.id.humLabel);
        TextView satVapLabel = (TextView) findViewById(R.id.satVapLabel);
        TextView tempLabel = (TextView) findViewById(R.id.timeLabel);

        timeLabel.setText("hello");

        //creating database instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = database.getReference("test-5487a/user/key/plants/reaper");
        mRef = new Firebase("https://test-5487a.firebaseio.com/");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String vapor = dataSnapshot.getValue(String.class);
                System.out.println(vapor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("error");
            }
        });

    }
}
