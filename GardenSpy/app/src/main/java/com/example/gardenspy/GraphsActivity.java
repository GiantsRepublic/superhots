package com.example.gardenspy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GraphsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

    }

    //method for settings button
    public void openDetailsPage(View view) {
        Intent newActivity = new Intent(this, DetailsActivity.class); //opens the details page
        startActivity(newActivity);
    }

    //method for graphs button
    public void openSettingsPage(View view) {
        Intent newActivity = new Intent(this, SettingsActivity.class); //opens the graphs page
        startActivity(newActivity);
    }

    @Override
    public void onBackPressed() {
        Intent newActivity = new Intent(this, DetailsActivity.class);
        startActivity(newActivity);
    }
}
