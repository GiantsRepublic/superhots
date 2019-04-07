package com.example.gardenspy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private static final int INT = 24;
    private static final String TAG = SettingsActivity.class.getSimpleName();
    private static boolean powerState;

    private static void getMoisture(@NonNull DataSnapshot dataSnapshot, TextView moistLabel) {
        if (dataSnapshot.child("moisture").child("threshold").exists()) {
            String moisture = Objects.requireNonNull(dataSnapshot.child("moisture").child("threshold").getValue()).toString();
            moistLabel.setText(String.format("%s %%", moisture));
        }
    }

    private static void getTemperature(@NonNull DataSnapshot dataSnapshot, TextView tempLabel) {
        if (dataSnapshot.child("temp").child("threshold").exists()) {
            String temp = Objects.requireNonNull(dataSnapshot.child("temp").child("threshold").getValue()).toString();
            tempLabel.setText(String.format("%s F", temp));
        }
    }

    private static void getOnTime(@NonNull DataSnapshot dataSnapshot, TextView onLabel) {
        if (dataSnapshot.child("timer").child("light_on").exists()) {
            String on = Objects.requireNonNull(dataSnapshot.child("timer").child("light_on").getValue()).toString();
            onLabel.setText(on);
        }
    }

    private static void getOffTime(@NonNull DataSnapshot dataSnapshot, TextView offLabel) {
        if (dataSnapshot.child("timer").child("light_off").exists()) {
            String off = Objects.requireNonNull(dataSnapshot.child("timer").child("light_off").getValue()).toString();
            offLabel.setText(off);
        }
    }

    private static void getHumidity(@NonNull DataSnapshot dataSnapshot, TextView humidityLabel) {
        if (dataSnapshot.child("humid").child("threshold").exists()) {
            String humidity = Objects.requireNonNull(dataSnapshot.child("humid").child("threshold").getValue()).toString();
            humidityLabel.setText(String.format("%s %%", humidity));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button saveEdits = findViewById(R.id.saveEdits);
        final TextView moistLabel = findViewById(R.id.moistThreshLabel);
        final TextView tempLabel = findViewById(R.id.tempThreshLabel);
        final TextView onLabel = findViewById(R.id.onThreshLabel);
        final TextView offLabel = findViewById(R.id.offThreshLabel);
        final TextView humidityLabel = findViewById(R.id.humidityThreshLabel);

        //creating database instance
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("user/key/plants/reaper");

        ToggleButton toggle = findViewById(R.id.PowerButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                powerState = isChecked;
            }
        });

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getMoisture(dataSnapshot, moistLabel);
                getTemperature(dataSnapshot, tempLabel);
                getOnTime(dataSnapshot, onLabel);
                getOffTime(dataSnapshot, offLabel);
                getHumidity(dataSnapshot, humidityLabel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); // Add database error message to the log if Data import fails
                Toast.makeText(getApplicationContext(), "Unable to retrieve data", Toast.LENGTH_SHORT).show(); //Error message in app if Data import fails.
            }
        });

        saveEdits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMoisture(rootRef, moistLabel);
                setTemperature(rootRef, tempLabel);
                setLightOn(rootRef, onLabel);
                setLightOff(rootRef, offLabel);
                setHumidity(rootRef, humidityLabel);
            }
        });
    }

    private void setMoisture(DatabaseReference rootRef, TextView moistLabel) {
        EditText moistureEdit = findViewById(R.id.moistureEdit);
        if (!moistureEdit.getText().toString().isEmpty()) {
            String moistEdit = moistureEdit.getText().toString();
            moistLabel.setText(moistEdit);
            rootRef.child("moisture").child("threshold").setValue(Integer.parseInt(moistEdit));
        }
    }

    private void setTemperature(DatabaseReference rootRef, TextView tempLabel) {
        EditText temperatureEdit = findViewById(R.id.tempEdit);
        if (!temperatureEdit.getText().toString().isEmpty()) {
            String tempEdit = temperatureEdit.getText().toString();
            tempLabel.setText(tempEdit);
            rootRef.child("temp").child("threshold").setValue(Integer.parseInt(tempEdit));
        }
    }

    private void setLightOn(DatabaseReference rootRef, TextView onLabel) {
        EditText lightOnEdit = findViewById(R.id.light_onEdit);
        if (!lightOnEdit.getText().toString().isEmpty()) {
            String onEdit = lightOnEdit.getText().toString();
            int value = Integer.parseInt(onEdit);
            if (value > INT) {
                value = INT;
                onEdit = String.valueOf(value);
            }
            onLabel.setText(onEdit);
            rootRef.child("timer").child("light_on").setValue(value);
        }
    }

    private void setLightOff(DatabaseReference rootRef, TextView offLabel) {
        EditText lightOffEdit = findViewById(R.id.light_offEdit);
        if (!lightOffEdit.getText().toString().isEmpty()) {
            String offEdit = lightOffEdit.getText().toString();
            int value = Integer.parseInt(offEdit);
            if (value > INT) {
                value = INT;
                offEdit = String.valueOf(value);
            }
            offLabel.setText(offEdit);
            rootRef.child("timer").child("light_off").setValue(value);
        }
    }

    private void setHumidity(DatabaseReference rootRef, TextView humidityLabel) {
        EditText humidityEdit = findViewById(R.id.humidityEdit);
        if (!humidityEdit.getText().toString().isEmpty()) {
            String humidEdit = humidityEdit.getText().toString();
            humidityLabel.setText(humidEdit);
            rootRef.child("humid").child("threshold").setValue(Integer.parseInt(humidEdit));
        }
    }

    //method for settings button
    public void openDetailsPage(View view) {
        Intent newActivity = new Intent(this, DetailsActivity.class); //opens the details page
        startActivity(newActivity);
    }

    //method for graphs button
    public void openGraphsPage(View view) {
        Intent newActivity = new Intent(this, GraphsActivity.class); //opens the graphs page
        startActivity(newActivity);
    }

    @Override
    public void onBackPressed() {
        Intent newActivity = new Intent(this, DetailsActivity.class);
        startActivity(newActivity);
    }
}
