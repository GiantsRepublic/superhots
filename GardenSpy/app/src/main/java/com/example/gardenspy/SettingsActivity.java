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
    private static final String TAG = SettingsActivity.class.getSimpleName();
    private static boolean powerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button saveEdits = findViewById(R.id.saveEdits);
        final TextView onLabel = findViewById(R.id.onThreshLabel);
        final TextView moistLabel = findViewById(R.id.moistThreshLabel);
        final TextView humidityLabel = findViewById(R.id.humidityThreshLabel);
        final TextView tempLabel = findViewById(R.id.tempThreshLabel);
        final TextView offLabel = findViewById(R.id.offThreshLabel);

        //creating database instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference rootRef = database.getReference("user/key/plants/reaper");


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
                getValues(dataSnapshot, tempLabel, moistLabel, humidityLabel, onLabel, offLabel);
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
                setValues(rootRef, moistLabel, humidityLabel, tempLabel, onLabel, offLabel);
            }
        });
    }

    private void setValues(DatabaseReference rootRef, TextView moistLabel, TextView humidityLabel, TextView tempLabel, TextView onLabel, TextView offLabel) {

        EditText moistureEdit = findViewById(R.id.moistureEdit);
        if (!moistureEdit.getText().toString().isEmpty()) {
            String moistEdit = moistureEdit.getText().toString();
            moistLabel.setText(moistEdit);
            int moistValue = Integer.parseInt(moistEdit);
            rootRef.child("moisture").child("threshold").setValue(moistValue);
        }

        EditText humidityEdit = findViewById(R.id.humidityEdit);
        if (!humidityEdit.getText().toString().isEmpty()) {
            String humidEdit = humidityEdit.getText().toString();
            humidityLabel.setText(humidEdit);
            int humidValue = Integer.parseInt(humidEdit);
            rootRef.child("humid").child("threshold").setValue(humidValue);
        }

        EditText temperatureEdit = findViewById(R.id.tempEdit);
        if (!temperatureEdit.getText().toString().isEmpty()) {
            String tempEdit = temperatureEdit.getText().toString();
            tempLabel.setText(tempEdit);
            int tempValue = Integer.parseInt(tempEdit);
            rootRef.child("temp").child("threshold").setValue(tempValue);
        }

        EditText light_onEdit = findViewById(R.id.light_onEdit);
        if (!light_onEdit.getText().toString().isEmpty()) {
            String onEdit = light_onEdit.getText().toString();
            onLabel.setText(onEdit);
            rootRef.child("timer").child("light_on").setValue(onEdit);
        }

        EditText light_offEdit = findViewById(R.id.light_offEdit);
        if (!light_offEdit.getText().toString().isEmpty()) {
            String offEdit = light_offEdit.getText().toString();
            offLabel.setText(offEdit);
            rootRef.child("timer").child("light_off").setValue(offEdit);
        }

    }

    private void getValues(@NonNull DataSnapshot dataSnapshot, TextView tempLabel, TextView moistLabel, TextView humidityLabel, TextView onLabel, TextView offLabel) {
        if (dataSnapshot.child("temp").child("threshold").getValue() != null) {
            String temp = Objects.requireNonNull(dataSnapshot.child("temp").child("threshold").getValue()).toString();
            tempLabel.setText(String.format("%s F", temp));
        }

        if (dataSnapshot.child("moisture").child("threshold").getValue() != null) {
            String moisture = Objects.requireNonNull(dataSnapshot.child("moisture").child("threshold").getValue()).toString();
            moistLabel.setText(String.format("%s %%", moisture));
        }

        if (dataSnapshot.child("humid").child("threshold").getValue() != null) {
            String humidity = Objects.requireNonNull(dataSnapshot.child("humid").child("threshold").getValue()).toString();
            humidityLabel.setText(String.format("%s %%", humidity));
        }

        if (dataSnapshot.child("timer").child("light_on").getValue() != null) {
            String on = Objects.requireNonNull(dataSnapshot.child("timer").child("light_on").getValue()).toString();
            onLabel.setText(on);
        }

        if (dataSnapshot.child("timer").child("light_off").getValue() != null) {
            String off = Objects.requireNonNull(dataSnapshot.child("timer").child("light_off").getValue()).toString();
            offLabel.setText(off);
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
}
