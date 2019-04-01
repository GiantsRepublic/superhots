package com.example.gardenspy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = SettingsActivity.class.getSimpleName();
    private static boolean powerState;

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
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                powerState = isChecked;
            }
        });


        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String temp = Objects.requireNonNull(dataSnapshot.child("temp").child("current").getValue()).toString();
                tempLabel.setText(temp);

                String moisture = Objects.requireNonNull(dataSnapshot.child("moisture").child("current").getValue()).toString();
                moistLabel.setText(moisture);

                String humidity = Objects.requireNonNull(dataSnapshot.child("humid").child("current").getValue()).toString();
                humidityLabel.setText(humidity);

                String on = Objects.requireNonNull(dataSnapshot.child("on").child("current").getValue()).toString();
                onLabel.setText(on);

                String off = Objects.requireNonNull(dataSnapshot.child("off").child("current").getValue()).toString();
                offLabel.setText(off);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); // Add database error message to the log if Data import fails
                Toast.makeText(getApplicationContext(), "Unable to retrieve data",Toast.LENGTH_SHORT).show(); //Error message in app if Data import fails.
            }
        });

        saveEdits.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                EditText moistureEdit = findViewById(R.id.moistureEdit);
                String moistEdit =  moistureEdit.getText().toString();
                moistLabel.setText(moistEdit);
                rootRef.setValue(moistEdit);

                EditText humidityEdit = findViewById(R.id.humidityEdit);
                String humidEdit =  humidityEdit.getText().toString();
                humidityLabel.setText(humidEdit);
                rootRef.setValue(humidEdit);

                EditText temperatureEdit = findViewById(R.id.tempEdit);
                String tempEdit =  temperatureEdit.getText().toString();
                tempLabel.setText(tempEdit);
                rootRef.setValue(tempEdit);

                EditText light_onEdit = findViewById(R.id.light_onEdit);
                String onEdit =  light_onEdit.getText().toString();
                onLabel.setText(onEdit);
                rootRef.setValue(onEdit);

                EditText light_offEdit = findViewById(R.id.light_offEdit);
                String offEdit =  light_offEdit.getText().toString();
                offLabel.setText(offEdit);
                rootRef.setValue(offEdit);
            }
        });
    }
}
