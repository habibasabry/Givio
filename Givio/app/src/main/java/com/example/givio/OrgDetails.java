package com.example.givio;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

public class OrgDetails extends AppCompatActivity {

    private ImageView imgLogo, backBtn;
    private TextView tvTitle, tvDescription, tvAddress, tvPhoneNumber, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.org_details);

        // Bind views
        imgLogo = findViewById(R.id.Dimg);
        backBtn = findViewById(R.id.BackBtn);
        tvTitle = findViewById(R.id.Dtitle);
        tvDescription = findViewById(R.id.Ddescription);
        tvAddress = findViewById(R.id.Daddress);
        tvPhoneNumber = findViewById(R.id.DphoneNumber);
        tvEmail = findViewById(R.id.Demail);

        // Get data from Intent
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            String address = intent.getStringExtra("address");
            String phone = intent.getStringExtra("phone");
            String email = intent.getStringExtra("email");
            int imageRes = intent.getIntExtra("image", R.drawable.org1);

            // Set data to views
            tvTitle.setText(title);
            tvDescription.setText(description);
            tvAddress.setText(address);
            tvPhoneNumber.setText(phone);
            tvEmail.setText(email);
            imgLogo.setImageResource(imageRes);
        }

        // Back button click — close this activity and go back
        backBtn.setOnClickListener(view -> finish());
    }
}
