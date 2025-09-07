package com.example.givio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddPost extends AppCompatActivity {

    ImageView backBtn, ivDonationImage;
    TextInputEditText etTitle, etCategory, etDescription, etLocation;
    MaterialButton btnUpload;

    Uri imageUri;
    private static final int IMAGE_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_add_post);

        backBtn = findViewById(R.id.BackBtn);
        ivDonationImage = findViewById(R.id.ivDonationImage);
        etTitle = findViewById(R.id.etTitle);
        etCategory = findViewById(R.id.etCategory);
        etDescription = findViewById(R.id.etDescription);
        etLocation = findViewById(R.id.etLocation);
        btnUpload = findViewById(R.id.btnUpload);

        backBtn.setOnClickListener(view -> finish());

        ivDonationImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_REQUEST_CODE);
        });

        btnUpload.setOnClickListener(v -> uploadPost());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            ivDonationImage.setImageURI(imageUri);
        }
    }

    private void uploadPost() {
        String title = etTitle.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String location = etLocation.getText().toString().trim();

        if (title.isEmpty() || category.isEmpty() || description.isEmpty() || location.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill all fields & select image", Toast.LENGTH_SHORT).show();
            return;
        }

        Donations donation = new Donations(title, category, description, location, imageUri);
        DonationRepository.addDonation(donation); // store the actual Uri

        Toast.makeText(this, "Donation saved!", Toast.LENGTH_SHORT).show();


        // Tell MainPage to open profile fragment
        Intent resultIntent = new Intent();
        resultIntent.putExtra("openProfileFragment", true);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
