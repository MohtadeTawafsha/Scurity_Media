package com.example.scurity_media;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InsertEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_email);

        Button buttonNext = findViewById(R.id.nextButton);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertEmailActivity.this, EmailVerificationActivity.class);
                startActivity(intent);
            }
        });

        Button buttonSkip = findViewById(R.id.skipButton);
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertEmailActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
