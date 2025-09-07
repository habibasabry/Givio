package com.example.givio;

import android.net.Uri;

public class Donations {
    private String title;
    private String category;
    private String description;
    private String location;
    private Uri imageUri; // store Uri directly

    public Donations(String title, String category, String description, String location, Uri imageUri) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.location = location;
        this.imageUri = imageUri;
    }

    // Getters
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public Uri getImageUri() { return imageUri; }
}
