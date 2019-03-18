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
    private FirebaseAuth mAuth;
    private EditText emailEdit, passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        super.onCreate(savedInstanceState);

        if(user != null){
            Intent newActivity = new Intent(this, gardenListActivity.class);
            startActivity(newActivity);
        }
        else {
            setContentView(R.layout.activity_login);

            emailEdit = findViewById(R.id.email);
            passwordEdit = findViewById(R.id.password);
            Button signin = findViewById(R.id.email_sign_in_button);
            mAuth = FirebaseAuth.getInstance();

            final TextInputLayout errorEmail = findViewById(R.id.text_input_email); //For proper Error Message in Email field
            final TextInputLayout errorPass = findViewById(R.id.text_input_password); //For proper Error Message in Password field

            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = emailEdit.getText().toString().trim();
                    String password = passwordEdit.getText().toString().trim();

                    //Start Validations
                    if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                        errorEmail.setError("Invalid Email Address");
                        emailEdit.requestFocus();
                        return;
                    } else {
                        errorEmail.setError(null);
                    }

                    if (TextUtils.isEmpty(password) || password.length() < 6) {
                        errorPass.setError("Invalid Password");
                        passwordEdit.requestFocus();
                        return;
                    } else {
                        errorPass.setError(null);
                    }
                    //End Validations

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(), gardenListActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Authentication Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
        }
    }

    public void openSignUpPage(View view){
        Intent newActivity = new Intent(this, SignupActivity.class);
        startActivity(newActivity);
    }
}

