package com.example.gardenspy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = DetailsActivity.class.getSimpleName();

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

        //creating database instance
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = database.getReference("user/key/plants/reaper");


        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("date").child("current").getValue() != null) {
                    String date = Objects.requireNonNull(dataSnapshot.child("date").child("current").getValue()).toString();
                    dateLabel.setText(date);
                }

                if (dataSnapshot.child("time").child("current").getValue() != null) {
                    String time = Objects.requireNonNull(dataSnapshot.child("time").child("current").getValue()).toString();
                    timeLabel.setText(time);
                }

                if (dataSnapshot.child("humid").child("current").getValue() != null) {
                    String humidity = Objects.requireNonNull(dataSnapshot.child("humid").child("current").getValue()).toString();
                    humLabel.setText(String.format("%s %%", humidity));
                }

                if (dataSnapshot.child("actualvapor").child("current").getValue() != null) {
                    String vapor = Objects.requireNonNull(dataSnapshot.child("actualvapor").child("current").getValue()).toString();
                    vaporLabel.setText(String.format("%s mb", vapor));
                }

                if (dataSnapshot.child("dewpoint").child("current").getValue() != null) {
                    String dew = Objects.requireNonNull(dataSnapshot.child("dewpoint").child("current").getValue()).toString();
                    dewLabel.setText(String.format("%s F", dew));
                }

                if (dataSnapshot.child("saturatedvapor").child("current").getValue() != null) {
                    String satVap = Objects.requireNonNull(dataSnapshot.child("saturatedvapor").child("current").getValue()).toString();
                    satVapLabel.setText(String.format("%s mb", satVap));
                }

                if (dataSnapshot.child("moisture").child("current").getValue() != null) {
                    String moisture = Objects.requireNonNull(dataSnapshot.child("moisture").child("current").getValue()).toString();
                    moistLabel.setText(String.format("%s %%", moisture));
                }

                if (dataSnapshot.child("temp").child("current").getValue() != null) {
                    String temp = Objects.requireNonNull(Objects.requireNonNull(dataSnapshot.child("temp").child("current").getValue())).toString();
                    tempLabel.setText(String.format("%s F", temp));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); // Add database error message to the log if Data import fails
                Toast.makeText(getApplicationContext(), "Unable to retrieve data", Toast.LENGTH_SHORT).show(); //Error message in app if Data import fails.
            }
        });

    }

    //method for settings button
    public void openSettingsPage(View view) {
        Intent newActivity = new Intent(this, SettingsActivity.class); //opens the settings page
        startActivity(newActivity);
    }

    /*method for graphs button
    public void openGraphsPage(View view){
        Intent newActivity = new Intent(this, GraphsActivity.class); //opens the graphs page
        startActivity(newActivity);
    }*/

}
