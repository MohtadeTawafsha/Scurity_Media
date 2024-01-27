package com.example.scurity_media;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmailVerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        Button buttonVerify = findViewById(R.id.buttonLogin);
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MainPageActivity when "Verify" button is clicked
                Intent intent = new Intent(EmailVerificationActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
