package com.example.gardenspy;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailEdit, passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEdit = findViewById(R.id.editTextEmail);
        passwordEdit = findViewById(R.id.editTextPassword);
        Button signin = findViewById(R.id.buttonSignin);
        mAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();

                //Start Validations
                if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEdit.setError("Invalid Email Address");
                    emailEdit.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password) || password.length() < 6) {
                    passwordEdit.setError("Invalid Password");
                    passwordEdit.requestFocus();
                    return;
                }
                //End Validations

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    startActivity(new Intent(getApplicationContext(),gardenListActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(),"Authentication Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
