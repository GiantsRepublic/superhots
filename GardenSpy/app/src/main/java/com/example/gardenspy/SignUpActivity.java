/*
  SignUpActivity will handle all of the overall sign up process.

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
import com.google.firebase.auth.SignInMethodQueryResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth; //connect to Firebase
    private EditText nameEdit, emailEdit, passwordEdit, passwordMatch; //EditText variables in order to strings later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //put the input text into EditText variables to convert into strings.
        nameEdit = findViewById(R.id.name);
        emailEdit = findViewById(R.id.email);
        passwordEdit = findViewById(R.id.password);
        passwordMatch = findViewById(R.id.password_repeat);
        Button register = findViewById(R.id.sign_up_button);
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    private void signUpUser() {
        //variables for validation checks
        final String name = nameEdit.getText().toString().trim(); //gets the name and converts it into a string and trims any extra spaces
        final String email = emailEdit.getText().toString().trim(); //gets the email and converts it into a string and trims any extra spaces
        final String password = passwordEdit.getText().toString().trim(); //gets the password and converts it into a string and trims any extra spaces
        final String passwordRepeat = passwordMatch.getText().toString().trim(); //gets the password repeat and converts it into a string and trims any extra spaces
        //calling the TextInputLayout allows you to show an error at the bottom of the text field instead of having it pop up on the side
        final TextInputLayout errorName = findViewById(R.id.text_input_name); //For proper Error Message in Name field.
        final TextInputLayout errorEmail = findViewById(R.id.text_input_email); //For proper Error Message in Email field
        final TextInputLayout errorPass = findViewById(R.id.text_input_password); //For proper Error Message in Password field
        final TextInputLayout errorPassRepeat = findViewById(R.id.text_input_password_repeat); //For proper Error Message in Password Repeat field

        //start validations
        if (TextUtils.isEmpty(name)) {
            errorName.setError("Invalid Name"); //error message
            nameEdit.requestFocus(); //Will focus the name field when an error is thrown so that the user can change it
            return; //Return so the app does not crash when there is more than one error
        } else {
            errorName.setError(null); //Must have else and set error to null in order to remove a previous error message
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorEmail.setError("Invalid Email");
            emailEdit.requestFocus();
            return;
        } else {
            errorEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            errorPass.setError("Invalid Password");
            passwordEdit.requestFocus();
            return;
        } else {
            errorPass.setError(null);
        }

        if (password.length() < 6) {
            errorPass.setError("Password must be at least 6 characters long");
            passwordEdit.requestFocus();
            return;
        } else {
            errorPass.setError(null);
        }

        if (!TextUtils.equals(password, passwordRepeat)) {
            errorPassRepeat.setError("Your passwords must match");
            passwordMatch.requestFocus();
            return;
        } else {
            errorPassRepeat.setError(null);
        }

        if (passwordRepeat.length() < 6) {
            errorPassRepeat.setError("Password must be at least 6 characters long");
            passwordMatch.requestFocus();
            return;
        } else {
            errorPassRepeat.setError(null);
        }

        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.getResult().getSignInMethods().size() == 0){
                    mAuth.createUserWithEmailAndPassword(email, password) //creates the user if the validations pass
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class)); //Go to Login if Registration is successful.
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Unable to Register", Toast.LENGTH_SHORT).show(); //Error message if Registration fails.
                                    }
                                }
                            });
                }else{
                    errorEmail.setError("Email already exists");
                }
            }
        });
        //end validations


    }
}
