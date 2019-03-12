package com.example.gardenspy;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }

    public void openLoginPage(View view){
        Intent newActivity = new Intent(this, LoginActivity.class);
        startActivity(newActivity);
    }

    public void openSignupPage(View view){
        Intent newActivity = new Intent(this, SignupActivity.class);
        startActivity(newActivity);
    }
}
