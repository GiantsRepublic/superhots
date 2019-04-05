package com.example.gardenspy;

import android.content.Intent;
import android.graphics.Color;
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
                getValues(dataSnapshot, dateLabel, timeLabel, humLabel, vaporLabel, dewLabel, satVapLabel, moistLabel, tempLabel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); // Add database error message to the log if Data import fails
                Toast.makeText(getApplicationContext(), "Unable to retrieve data", Toast.LENGTH_SHORT).show(); //Error message in app if Data import fails.
            }
        });

    }

    private void getValues(@NonNull DataSnapshot dataSnapshot, TextView dateLabel, TextView timeLabel, TextView humLabel, TextView vaporLabel, TextView dewLabel, TextView satVapLabel, TextView moistLabel, TextView tempLabel) {
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

            if (dataSnapshot.child("humid").child("threshold").getValue() != null) {
                String threshold = Objects.requireNonNull(dataSnapshot.child("humid").child("threshold").getValue()).toString();
                int humid = Integer.parseInt(humidity);
                int thresh = Integer.parseInt(threshold);
                int defaultColor = humLabel.getTextColors().getDefaultColor();
                if (humid > thresh) {
                    humLabel.setTextColor(Color.RED);
                } else {
                    humLabel.setTextColor(defaultColor);
                }
            }

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

            if (dataSnapshot.child("moisture").child("threshold").getValue() != null) {
                String threshold = Objects.requireNonNull(dataSnapshot.child("moisture").child("threshold").getValue()).toString();
                int moist = Integer.parseInt(moisture);
                int thresh = Integer.parseInt(threshold);
                int defaultColor = moistLabel.getTextColors().getDefaultColor();
                if (moist < thresh) {
                    moistLabel.setTextColor(Color.RED);
                } else {
                    moistLabel.setTextColor(defaultColor);
                }
            }

            moistLabel.setText(String.format("%s %%", moisture));
        }

        if (dataSnapshot.child("temp").child("current").getValue() != null) {
            String temperature = Objects.requireNonNull(Objects.requireNonNull(dataSnapshot.child("temp").child("current").getValue())).toString();

            if (dataSnapshot.child("temp").child("threshold").getValue() != null) {
                String threshold = Objects.requireNonNull(dataSnapshot.child("temp").child("threshold").getValue()).toString();
                int temp = Integer.parseInt(temperature);
                int thresh = Integer.parseInt(threshold);
                int defaultColor = tempLabel.getTextColors().getDefaultColor();
                if (temp > thresh) {
                    tempLabel.setTextColor(Color.RED);
                } else {
                    tempLabel.setTextColor(defaultColor);
                }
            }

            tempLabel.setText(String.format("%s F", temperature));
        }
    }

    //method for settings button
    public void openSettingsPage(View view) {
        Intent newActivity = new Intent(this, SettingsActivity.class); //opens the settings page
        startActivity(newActivity);
    }

    //method for graphs button
    public void openGraphsPage(View view) {
        Intent newActivity = new Intent(this, GraphsActivity.class); //opens the graphs page
        startActivity(newActivity);
    }

}
