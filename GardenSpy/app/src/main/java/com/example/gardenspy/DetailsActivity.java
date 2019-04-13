package com.example.gardenspy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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

    private static void getDate(@NonNull DataSnapshot dataSnapshot, TextView dateLabel) {
        if (dataSnapshot.child("date").child("current").exists()) {
            String date = Objects.requireNonNull(dataSnapshot.child("date").child("current").getValue()).toString();
            dateLabel.setText(date);
        }
    }

    private static void getTime(@NonNull DataSnapshot dataSnapshot, TextView timeLabel) {
        if (dataSnapshot.child("time").child("current").exists()) {
            String time = Objects.requireNonNull(dataSnapshot.child("time").child("current").getValue()).toString();
            timeLabel.setText(time);
        }
    }

    private static void getHumidity(@NonNull DataSnapshot dataSnapshot, TextView humLabel) {
        if (dataSnapshot.child("humid").child("current").exists()) {
            String humidity = Objects.requireNonNull(dataSnapshot.child("humid").child("current").getValue()).toString();

            if (dataSnapshot.child("humid").child("threshold").exists()) {
                String threshold = Objects.requireNonNull(dataSnapshot.child("humid").child("threshold").getValue()).toString();

                int defaultColor = humLabel.getTextColors().getDefaultColor();
                if (Integer.parseInt(humidity) > Integer.parseInt(threshold)) {
                    humLabel.setTextColor(Color.RED);
                } else {
                    humLabel.setTextColor(defaultColor);
                }
            }

            humLabel.setText(String.format("%s %%", humidity));
        }
    }

    private static void getVapor(@NonNull DataSnapshot dataSnapshot, TextView vaporLabel) {
        if (dataSnapshot.child("actualvapor").child("current").exists()) {
            String vapor = Objects.requireNonNull(dataSnapshot.child("actualvapor").child("current").getValue()).toString();
            vaporLabel.setText(String.format("%s mb", vapor));
        }
    }

    private static void getDewPoint(@NonNull DataSnapshot dataSnapshot, TextView dewLabel) {
        if (dataSnapshot.child("dewpoint").child("current").exists()) {
            String dew = Objects.requireNonNull(dataSnapshot.child("dewpoint").child("current").getValue()).toString();
            dewLabel.setText(String.format("%s F", dew));
        }
    }

    private static void getSaturatedVapor(@NonNull DataSnapshot dataSnapshot, TextView satVapLabel) {
        if (dataSnapshot.child("saturatedvapor").child("current").exists()) {
            String satVap = Objects.requireNonNull(dataSnapshot.child("saturatedvapor").child("current").getValue()).toString();
            satVapLabel.setText(String.format("%s mb", satVap));
        }
    }

    private static void getMoisture(@NonNull DataSnapshot dataSnapshot, TextView moistLabel) {
        if (dataSnapshot.child("moisture").child("current").exists()) {
            String moisture = Objects.requireNonNull(dataSnapshot.child("moisture").child("current").getValue()).toString();

            if (dataSnapshot.child("moisture").child("threshold").exists()) {
                String threshold = Objects.requireNonNull(dataSnapshot.child("moisture").child("threshold").getValue()).toString();

                int defaultColor = moistLabel.getTextColors().getDefaultColor();
                if (Integer.parseInt(moisture) < Integer.parseInt(threshold)) {
                    moistLabel.setTextColor(Color.RED);
                } else {
                    moistLabel.setTextColor(defaultColor);
                }
            }

            moistLabel.setText(String.format("%s %%", moisture));
        }
    }

    private static void getTemperature(@NonNull DataSnapshot dataSnapshot, TextView tempLabel) {
        if (dataSnapshot.child("temp").child("current").exists()) {
            String temperature = Objects.requireNonNull(Objects.requireNonNull(dataSnapshot.child("temp").child("current").getValue())).toString();

            if (dataSnapshot.child("temp").child("threshold").exists()) {
                String threshold = Objects.requireNonNull(dataSnapshot.child("temp").child("threshold").getValue()).toString();

                int defaultColor = tempLabel.getTextColors().getDefaultColor();
                if (Integer.parseInt(temperature) > Integer.parseInt(threshold)) {
                    tempLabel.setTextColor(Color.RED);
                } else {
                    tempLabel.setTextColor(defaultColor);
                }
            }

            tempLabel.setText(String.format("%s F", temperature));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //setup text labels
        final TextView dateLabel = findViewById(R.id.dateLabel);
        final TextView timeLabel = findViewById(R.id.timeLabel);
        final TextView humLabel = findViewById(R.id.humLabel);
        final TextView vaporLabel = findViewById(R.id.vaporLabel);
        final TextView dewLabel = findViewById(R.id.dewLabel);
        final TextView satVapLabel = findViewById(R.id.satVapLabel);
        final TextView moistLabel = findViewById(R.id.moistLabel);
        final TextView tempLabel = findViewById(R.id.tempLabel);


        //creating database instance
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String userid = user.getUid();
        //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("user");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper");

        //rootRef.child(userid).addListenerForSingleValueEvent(new ValueEventListener() { }
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //
                getDate(dataSnapshot, dateLabel);
                getTime(dataSnapshot, timeLabel);
                getHumidity(dataSnapshot, humLabel);
                getVapor(dataSnapshot, vaporLabel);
                getDewPoint(dataSnapshot, dewLabel);
                getSaturatedVapor(dataSnapshot, satVapLabel);
                getMoisture(dataSnapshot, moistLabel);
                getTemperature(dataSnapshot, tempLabel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); // Add database error message to the log if Data import fails
                Toast.makeText(getApplicationContext(), "Unable to retrieve data", Toast.LENGTH_SHORT).show(); //Error message in app if Data import fails.
            }
        });

    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.common_google_play_services_notification_channel_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NotificationChannel.DEFAULT_CHANNEL_ID, name, importance);
            channel.setDescription("");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
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

    @Override
    public void onBackPressed() {
        Intent newActivity = new Intent(this, GardenListActivity.class);
        startActivity(newActivity);
    }

    public void openImageDialog(View view) {
        Intent newActivity = new Intent(this, ImageViewActivity.class);
        startActivity(newActivity);
    }
}
