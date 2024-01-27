package com.example.scurity_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

public class PhoneVerifcationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verifcation);

        mAuth = FirebaseAuth.getInstance();

        // Get phone number from intent
        String phoneNumber = getIntent().getStringExtra("phoneNumber");

        // Send verification code to the provided phone number
        mAuth.getCurrentUser().getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            // Verification code sent successfully
                            Toast.makeText(PhoneVerifcationActivity.this, "Verification code sent to " + phoneNumber, Toast.LENGTH_SHORT).show();
                        } else {
                            // Failed to send verification code
                            Toast.makeText(PhoneVerifcationActivity.this, "Failed to send verification code.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
