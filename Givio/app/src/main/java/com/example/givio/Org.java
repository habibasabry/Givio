package com.example.givio;

public class Org {
    private String name;
    private String description;
    private int imageRes;
    private String address;
    private String phone;
    private String email;

    public Org(String name, String description, int imageRes, String address, String phone, String email) {
        this.name = name;
        this.description = description;
        this.imageRes = imageRes;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
