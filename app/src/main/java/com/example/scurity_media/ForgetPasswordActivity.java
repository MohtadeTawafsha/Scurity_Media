package com.example.scurity_media;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPasswordActivity extends AppCompatActivity {

    String phone ;
    Button btback ,btnext;

    private EditText phoneoremail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        btback = findViewById(R.id. btback);
        btnext= findViewById(R.id. btnext);
        phoneoremail= findViewById(R.id.     phoneoremail);

        btback  .setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent MainActivity= new Intent(ForgetPasswordActivity.this,Login.class);
                startActivity(MainActivity);
                ForgetPasswordActivity.this.finish();

            }
        });



        btnext  .setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String input =  phoneoremail.getText().toString().trim();

                // Check if the input is empty
                // Check if the input is empty
                if (input.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Input cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the input is a valid email address
                    if (Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                        Toast.makeText(getApplicationContext(), "Valid email address", Toast.LENGTH_SHORT).show();
                    } else {
                        // Check if the input is a valid phone number
                        if (Patterns.PHONE.matcher(input).matches()) {
                            Toast.makeText(getApplicationContext(), "Valid phone number", Toast.LENGTH_SHORT).show();
                        } else {
                            // If neither email nor phone number, show an error
                            Toast.makeText(getApplicationContext(), "Invalid email or phone number", Toast.LENGTH_SHORT).show();
                        }
                    }

                    Intent ChangePasswordFotgetActivity = new Intent(ForgetPasswordActivity.this, ChangePasswordFotgetActivity.class);
                    startActivity(ChangePasswordFotgetActivity);
                    ForgetPasswordActivity.this.finish();

                }
            }
        });



    }
}