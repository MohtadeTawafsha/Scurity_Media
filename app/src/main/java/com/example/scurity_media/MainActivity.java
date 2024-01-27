package com.example.scurity_media;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_PHOTO_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;

    private ArrayList<Uri> selectedPhotosList;
    private ArrayList<Uri> originalPhotosList;
    private PhotoAdapter photoAdapter;
    private RecyclerView photoRecyclerView;
    private ImageView selectedPhotoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSelectPhotos = findViewById(R.id.btnSelectPhotos);
        Button btnDecryption = findViewById(R.id.btnDecryotion);

        photoRecyclerView = findViewById(R.id.photoRecyclerView);
        selectedPhotoImageView = findViewById(R.id.selectedPhotoImageView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        photoRecyclerView.setLayoutManager(layoutManager);

        selectedPhotosList = new ArrayList<>();
        originalPhotosList = new ArrayList<>();
        photoAdapter = new PhotoAdapter(selectedPhotosList);
        photoRecyclerView.setAdapter(photoAdapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        btnSelectPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryForPhotos();

            }



        });

        btnDecryption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if there are any selected photos
                if (selectedPhotosList.isEmpty()) {
                    showToast("No photos selected");
                    return;
                }

                // Assuming you want to reveal the latest added photo
                Uri originalUri = originalPhotosList.get(originalPhotosList.size() - 1);

                // Use the original URI to display the image
                selectedPhotoImageView.setImageURI(originalUri);

                // Make the RecyclerView and ImageView visible
                photoRecyclerView.setVisibility(View.VISIBLE);
                selectedPhotoImageView.setVisibility(View.VISIBLE);

                showToast("Decryption successful");
            }
        });
    }

    private void openGalleryForPhotos() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_PHOTO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PHOTO_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            selectedPhotosList.add(selectedImageUri);
            originalPhotosList.add(selectedImageUri);  // Save the original URI
            photoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGalleryForPhotos();
                } else {
                    showToast("Permission denied. Cannot select photos.");
                }
                break;
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
