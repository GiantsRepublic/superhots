package com.example.gardenspy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class gardenListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden_list);
    }


    public void openDetailsPage(View view){
        Intent newActivity = new Intent(this, DetailsActivity.class);
        startActivity(newActivity);
    }
}
