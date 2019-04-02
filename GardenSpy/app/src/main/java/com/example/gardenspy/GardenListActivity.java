package com.example.gardenspy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class GardenListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden_list);
    }


    public void openDetailsPage(View view) {
        Intent newActivity = new Intent(this, DetailsActivity.class);
        startActivity(newActivity);
    }

    public void signOut(View view) {
        try {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getApplicationContext(), "Signed Out", Toast.LENGTH_SHORT).show();
            Intent newActivity = new Intent(this, LoginActivity.class);
            startActivity(newActivity);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Sign Out Error", Toast.LENGTH_SHORT).show();
        }

    }
}
