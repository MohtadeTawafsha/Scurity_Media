package com.example.scurity_media;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;


public class EditEmailActivity extends AppCompatActivity {

    Button save_button, back_button;
    EditText editTextUsername;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_email);

        save_button = findViewById(R.id.save_button);
        back_button = findViewById(R.id.back_button);
        editTextUsername = findViewById(R.id.editTextUsername);

        fAuth = FirebaseAuth.getInstance();

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextUsername.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    editTextUsername.setError("Email is required!");
                    return;
                }
            }

        });

        back_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent EditInformationActivity = new Intent(EditEmailActivity.this,EditInformationActivity.class);
                startActivity(EditInformationActivity);
                EditEmailActivity.this.finish();

            }
        });

    }
}

