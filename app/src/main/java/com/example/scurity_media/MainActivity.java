package com.example.scurity_media;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_PHOTO_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private ArrayList<Uri> selectedPhotosList;
    private ArrayList<Uri> originalPhotosList;
    private PhotoAdapter photoAdapter;
    private RecyclerView photoRecyclerView;
    private EncryptionUtils encryptionUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSelectPhotos = findViewById(R.id.btnSelectPhotos);
        Button btnDecryption = findViewById(R.id.btnDecryotion);

        photoRecyclerView = findViewById(R.id.photoRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        photoRecyclerView.setLayoutManager(layoutManager);

        selectedPhotosList = new ArrayList<>();
        originalPhotosList = new ArrayList<>();
        photoAdapter = new PhotoAdapter(selectedPhotosList);
        photoRecyclerView.setAdapter(photoAdapter);

        encryptionUtils = new EncryptionUtils();

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
                try {
                    if (selectedPhotosList.isEmpty()) {
                        showToast("No photos selected");
                        return;
                    }

                    Uri originalUri = originalPhotosList.get(originalPhotosList.size() - 1);
                    showToast("Selected photo URI: " + originalUri.toString());

                    // Get the path of the selected image
                    String imagePath = getPathFromUri(originalUri);
                    showToast("Image path: " + imagePath);

                    if (imagePath == null) {
                        showToast("Image path is null");
                        return;
                    }

                    // Read the content of the image file
                    byte[] encryptedBytes = readContentFromFile(imagePath);
                    showToast("Encrypted content read successfully");

                    if (encryptedBytes == null) {
                        showToast("Encrypted content is null");
                        return;
                    }

                    // Decrypt the content
                    String decryptedBytes = encryptionUtils.decrypt(String.valueOf(encryptedBytes));
                    String decryptedContent = new String(decryptedBytes);

                    // Check the decrypted content
                    showToast("Decrypted content: " + decryptedContent);

                    // Update the UI or perform any further actions with decrypted content
                    showToast("Decryption successful");

                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("Decryption failed: " + e.getMessage()); // Print the exception message
                }
            }
        });
    }

    private void openGalleryForPhotos() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_PHOTO_REQUEST);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        } else {
            showToast("Failed to retrieve file path");
            return null;
        }
    }

    private byte[] readContentFromFile(String filePath) {
        byte[] content = null;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            int fileSize = fis.available();
            content = new byte[fileSize];
            fis.read(content);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
