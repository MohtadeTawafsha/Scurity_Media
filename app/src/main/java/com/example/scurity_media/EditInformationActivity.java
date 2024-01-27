package com.example.scurity_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class EditInformationActivity extends AppCompatActivity {

    private EditText editTextPhoneNumber, editTextOldPassword,
            editTextNewPassword, editTextConfirmPassword;
    private Button button_save,buttonEditEmail ,button_back;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);

        editTextPhoneNumber = findViewById(R.id.editTextPhone);
        editTextOldPassword = findViewById(R.id.editTextOldPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmNewPassword);
        button_save = findViewById(R.id.button_save);
        button_back = findViewById(R.id.button_back);
        buttonEditEmail = findViewById(R.id.buttonEditEmail);

        firebaseAuth = FirebaseAuth.getInstance();

        button_save.setOnClickListener(view -> updateUserInformation());


        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent forgotPasswordActivity = new Intent(EditInformationActivity.this,MainPageActivity.class);
                startActivity(forgotPasswordActivity);
                EditInformationActivity.this.finish();

            }
        });


        buttonEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent EditEmailActivity = new Intent(EditInformationActivity.this,EditEmailActivity.class);
                startActivity(EditEmailActivity);
                EditInformationActivity.this.finish();

            }
        });



    }

    private void updateUserInformation() {
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String oldPassword = editTextOldPassword.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();


        // Validate the inputs
        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(oldPassword) ||
                TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(EditInformationActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate that newPassword and confirmPassword match
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(EditInformationActivity.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve the currently signed-in user
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            // Check if the provided phone number matches the user's phone number
            if (!phoneNumber.equals(currentUser.getPhoneNumber())) {
                Toast.makeText(EditInformationActivity.this, "Incorrect phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            // Authenticate the user with old password
            firebaseAuth.signInWithEmailAndPassword(currentUser.getEmail(), oldPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Update user information
                            currentUser.updatePassword(newPassword)
                                    .addOnCompleteListener(updateTask -> {
                                        if (updateTask.isSuccessful()) {
                                            Toast.makeText(EditInformationActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(EditInformationActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // Handle authentication failure
                            Exception e = task.getException();
                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(EditInformationActivity.this, "Invalid old password", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditInformationActivity.this, "Authentication error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(EditInformationActivity.this, "User not signed in", Toast.LENGTH_SHORT).show();
        }





    }
}