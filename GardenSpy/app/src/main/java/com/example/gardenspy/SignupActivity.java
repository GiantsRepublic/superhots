package com.example.gardenspy;

import android.content.Intent;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "SignupActivity";
    private EditText nameEdit, emailEdit, passwordEdit, passwordMatch;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameEdit = findViewById(R.id.editTextName);
        emailEdit = findViewById(R.id.editTextEmail);
        passwordEdit = findViewById(R.id.editTextPassword);
        passwordMatch = findViewById(R.id.editTextPasswordRepeat);
        register = findViewById(R.id.buttonSignup);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString().trim();
                String email = emailEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                String passwordRepeat = passwordMatch.getText().toString().trim();

                if(TextUtils.isEmpty(name))
                {
                    nameEdit.setError("Name cannot be empty.");
                    return;
                }
                if(TextUtils.isEmpty(email))
                {
                    emailEdit.setError("Email cannot be empty.");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    passwordEdit.setError("Password cannot be empty.");
                    return;
                }
                if (!TextUtils.equals(password, passwordRepeat))
                {
                    passwordMatch.setError("Your passwords must match");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Email or password is wrong",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
