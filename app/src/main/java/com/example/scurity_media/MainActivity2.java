package com.example.scurity_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();

        // Find the button for "Sign Up"
        Button buttonSignUp = findViewById(R.id.buttonLogin);

        // Set an OnClickListener for "Sign Up"
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = "user@example.com";
                String password = "password123";
                String phoneNumber = "0591234567"; // Replace with user's input

                // Create the user with email and password
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity2.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // User account creation success
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        // Navigate to PhoneVerificationActivity and pass phone number
                                        Intent intent = new Intent(MainActivity2.this, PhoneVerifcationActivity.class);
                                        intent.putExtra("phoneNumber", phoneNumber);
                                        startActivity(intent);
                                    }
                                } else {
                                    // User account creation failed
                                    Toast.makeText(MainActivity2.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
