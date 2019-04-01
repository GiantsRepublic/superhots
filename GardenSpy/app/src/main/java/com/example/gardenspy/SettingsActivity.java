package com.example.gardenspy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class SettingsActivity extends AppCompatActivity {

    
    public static boolean powerState;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button saveEdits = findViewById(R.id.saveEdits);



        final TextView onLabel = (TextView) findViewById(R.id.onThreshLabel);
        final TextView moistLabel = (TextView) findViewById(R.id.moistThreshLabel);
        final TextView humidityLabel = (TextView) findViewById(R.id.humidityThreshLabel);
        final TextView tempLabel = (TextView) findViewById(R.id.tempThreshLabel);
        final TextView offLabel = (TextView) findViewById(R.id.offThreshLabel);

        //creating database instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference rootRef = database.getReference("user/key/plants/reaper");
        //FirebaseDatabase data = new FirebaseDatabase("test-5487a/user/key/plants/reaper");
        //mRef = new FirebaseDatabase("https://test-5487a.firebaseio.com/");
//



        ToggleButton toggle = (ToggleButton) findViewById(R.id.PowerButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    powerState=true;
                } else {
                    powerState=false;
                }
            }
        });







        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String temp = dataSnapshot.child("temp").child("current").getValue().toString();
                tempLabel.setText(temp + " F");

                String moisture = dataSnapshot.child("moisture").child("current").getValue().toString();
                moistLabel.setText(moisture);

                String humidity = dataSnapshot.child("humid").child("current").getValue().toString();
                humidityLabel.setText(humidity);

                String on = dataSnapshot.child("on").child("current").getValue().toString();
                onLabel.setText(on);

                String off = dataSnapshot.child("off").child("current").getValue().toString();
                offLabel.setText(off);


            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("error");
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

                EditText tempEdit = findViewById(R.id.tempEdit);
                String temppEdit =  tempEdit.getText().toString();
                tempLabel.setText(temppEdit);
                rootRef.setValue(temppEdit);

                EditText light_onEdit = findViewById(R.id.light_onEdit);
                String onEdit =  light_onEdit.getText().toString();
                onLabel.setText(onEdit);
                rootRef.setValue(onEdit);

                EditText light_offEdit = findViewById(R.id.light_offEdit);
                String offEdit =  light_offEdit.getText().toString();
                offLabel.setText(offEdit);
                rootRef.setValue(offEdit);



            }








        }
    }

}
