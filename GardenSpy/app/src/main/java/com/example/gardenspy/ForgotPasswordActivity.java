package com.example.gardenspy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
    private static final String TAG = ForgotPasswordActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private EditText emailEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEdit = findViewById(R.id.email);

        mAuth = FirebaseAuth.getInstance();
        Button sendEmail = findViewById(R.id.email_button);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        final String email = emailEdit.getText().toString().trim();
        final TextInputLayout errorEmail = findViewById(R.id.text_input_email);

        //start validations
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorEmail.setError("Invalid Email Address"); //error message
            emailEdit.requestFocus(); //Will focus the email field when an error is thrown so that the user can change it
            return;
        } else {
            errorEmail.setError(null); //Must have else and set error to null in order to remove a previous error message
        }

        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getSignInMethods()).isEmpty()) {
                    errorEmail.setError("Email does not exists");
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Password Reset Email Sent.");
                                Toast.makeText(getApplicationContext(), "Email has been sent.", Toast.LENGTH_SHORT).show();
                                openLoginPage();
                            } else {
                                Toast.makeText(getApplicationContext(), "Email could not be sent.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    //method for opening login page
    private void openLoginPage() {
        Intent newActivity = new Intent(this, LoginActivity.class); //opens the sign up page
        startActivity(newActivity);
    }

    @Override
    public void onBackPressed() {
        Intent newActivity = new Intent(this, LoginActivity.class);
        startActivity(newActivity);
    }
}
