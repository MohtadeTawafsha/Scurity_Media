package com.example.scurity_media;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangePasswordFotgetActivity extends AppCompatActivity {
    private Button backButton = findViewById(R.id.btback);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_fotget);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to ForgetPasswordActivity when "Back" button is clicked
                Intent intent = new Intent(ChangePasswordFotgetActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                finish(); // Finish the ChangePasswordFotgetActivity
            }
        });
    }
}