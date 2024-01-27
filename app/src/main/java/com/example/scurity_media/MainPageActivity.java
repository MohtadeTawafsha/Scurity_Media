package com.example.scurity_media;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPageActivity extends AppCompatActivity {

    Button btsettings , btvideos , btphotos ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        btsettings = findViewById(R.id.btsettings);
        btvideos = findViewById(R.id.btvideos);
        btphotos = findViewById(R.id.btphotos);


        btsettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent EditEmailActivity = new Intent(MainPageActivity.this,EditInformationActivity.class);
                startActivity(EditEmailActivity);
                MainPageActivity.this.finish();

            }
        });
        btvideos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent EditEmailActivity = new Intent(MainPageActivity.this,MainActivity.class);
                startActivity(EditEmailActivity);
                MainPageActivity.this.finish();

            }
        });
        btphotos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent EditEmailActivity = new Intent(MainPageActivity.this,MainActivity.class);
                startActivity(EditEmailActivity);
                MainPageActivity.this.finish();

            }
        });





    }

}