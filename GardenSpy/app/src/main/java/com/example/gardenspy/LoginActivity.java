/*
  LoginActivity will handle all of the overall login process.

  Author: Leonel Jerez
 */

package com.example.gardenspy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth; //connect to Firebase
    private EditText emailEdit, passwordEdit; //EditText variables in order to strings later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //Grabs the current user in Firebase the app is signed in on. If there isn't any it returns null
        super.onCreate(savedInstanceState);

        if(user != null){ //if there is a user signed in, it skips the login activity and goes into Garden List
            Intent newActivity = new Intent(this, gardenListActivity.class);
            startActivity(newActivity);
        }
        else { //when there is no user logged in
            setContentView(R.layout.activity_login);

            //put the input text into EditText variables to convert into strings.
            emailEdit = findViewById(R.id.email);
            passwordEdit = findViewById(R.id.password);
            Button signin = findViewById(R.id.email_sign_in_button);
            mAuth = FirebaseAuth.getInstance();

            //calling the TextInputLayout allows you to show an error at the bottom of the text field instead of having it pop up on the side
            final TextInputLayout errorEmail = findViewById(R.id.text_input_email); //For proper Error Message in Email field
            final TextInputLayout errorPass = findViewById(R.id.text_input_password); //For proper Error Message in Password field

            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //variables for validation checks
                    String email = emailEdit.getText().toString().trim(); //gets the email and converts it into a string and trims any extra spaces
                    String password = passwordEdit.getText().toString().trim(); //gets the password and converts it into a string and trims any extra spaces

                    //Start Validations
                    if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        errorEmail.setError("Invalid Email Address"); //error message
                        emailEdit.requestFocus(); //Will focus the email field when an error is thrown so that the user can change it
                        return; //Return so the app does not crash when there is more than one error
                    } else {
                        errorEmail.setError(null); //Must have else and set error to null in order to remove a previous error message
                    }

                    if (TextUtils.isEmpty(password) || password.length() < 6) {
                        errorPass.setError("Invalid Password");
                        passwordEdit.requestFocus();
                        return;
                    } else {
                        errorPass.setError(null);
                    }
                    //End Validations

                    mAuth.signInWithEmailAndPassword(email, password) //signs in if validations pass
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(), gardenListActivity.class)); //goes to Garden List if sign in is successful
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Authentication Error", Toast.LENGTH_SHORT).show(); //error message if sign in fails
                                    }
                                }
                            });
                }
            });
        }
    }

    //method for sign up button
    public void openSignUpPage(View view){
        Intent newActivity = new Intent(this, SignupActivity.class); //opens the sign up page
        startActivity(newActivity);
    }
}

