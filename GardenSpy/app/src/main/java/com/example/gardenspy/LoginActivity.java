/*
  LoginActivity will handle all of the overall login process.

  Author: Leonel Jerez
 */

package com.example.gardenspy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth; //connect to FireBase
    private EditText emailEdit;
    private EditText passwordEdit; //EditText variables in order to strings later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance(); //Grabs the current user in Firebase the app is signed in on. If there isn't any it returns null

        if (mAuth.getCurrentUser() != null) { //if there is a user signed in, it skips the login activity and goes into Garden List
            Intent newActivity = new Intent(this, GardenListActivity.class);
            startActivity(newActivity);
            finish();
        }
        //when there is no user logged in
            setContentView(R.layout.activity_login);

            //put the input text into EditText variables to convert into strings.
            emailEdit = findViewById(R.id.email);
            passwordEdit = findViewById(R.id.password);
            Button signIn = findViewById(R.id.email_sign_in_button);
            mAuth = FirebaseAuth.getInstance();

            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginUser();
                }
            });

    }

    private void loginUser() {
        //variables for validation checks
        String email = emailEdit.getText().toString().trim(); //gets the email and converts it into a string and trims any extra spaces
        String password = passwordEdit.getText().toString().trim(); //gets the password and converts it into a string and trims any extra spaces

        //calling the TextInputLayout allows you to show an error at the bottom of the text field instead of having it pop up on the side
        TextInputLayout errorEmail = findViewById(R.id.text_input_email); //For proper Error Message in Email field
        TextInputLayout errorPass = findViewById(R.id.text_input_password); //For proper Error Message in Password field

        //start validations
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorEmail.setError("Invalid Email Address"); //error message
            emailEdit.requestFocus(); //Will focus the email field when an error is thrown so that the user can change it
            return;
        } else {
            errorEmail.setError(null); //Must have else and set error to null in order to remove a previous error message
        }

        if (TextUtils.isEmpty(password) || (password.length() < 6)) {
            errorPass.setError("Invalid Password");
            passwordEdit.requestFocus();
            return;
        } else {
            errorPass.setError(null);
        }
        //end validations

        mAuth.signInWithEmailAndPassword(email, password) //signs in if validations pass
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), GardenListActivity.class)); //goes to Garden List if sign in is successful
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show(); //error message if sign in fails
                        }
                    }
                });
    }

    //method for sign up button
    public void openSignUpPage(View view) {
        Intent newActivity = new Intent(this, SignUpActivity.class); //opens the sign up page
        startActivity(newActivity);
    }

    //method for forgot password button
    public void openForgotPasswordPage(View view) {
        Intent newActivity = new Intent(this, ForgotPasswordActivity.class); //opens the sign up page
        startActivity(newActivity);
    }

    @Override
    public void onBackPressed(){
        finish();
        moveTaskToBack(true);
    }
}

