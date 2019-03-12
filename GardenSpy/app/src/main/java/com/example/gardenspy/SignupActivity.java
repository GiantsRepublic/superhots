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

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText nameEdit, emailEdit, passwordEdit, passwordMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameEdit = findViewById(R.id.editTextName);
        emailEdit = findViewById(R.id.editTextEmail);
        passwordEdit = findViewById(R.id.editTextPassword);
        passwordMatch = findViewById(R.id.editTextPasswordRepeat);
        Button register = findViewById(R.id.buttonSignup);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString().trim();
                String email = emailEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                String passwordRepeat = passwordMatch.getText().toString().trim();

                //Start Validations
                if(TextUtils.isEmpty(name)) {
                    nameEdit.setError("Invalid Name");
                    return;
                }
                if(TextUtils.isEmpty(email)) {
                    emailEdit.setError("Invalid Email");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    passwordEdit.setError("Invalid Password");
                    return;
                }
                if(password.length() < 6){
                    passwordEdit.setError("Password must be at least 6 characters long");
                }
                if (!TextUtils.equals(password, passwordRepeat)) {
                    passwordMatch.setError("Your passwords must match");
                    return;
                }
                if(passwordRepeat.length() < 6){
                    passwordMatch.setError("Password must be at least 6 characters long");
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailEdit.setError("Invalid Email Address");
                    return;
                }
                //End Validations

                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Unable to Register",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
