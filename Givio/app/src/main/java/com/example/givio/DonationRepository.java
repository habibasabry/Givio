package com.example.givio;

import java.util.ArrayList;
import java.util.List;

public class DonationRepository {

    // A static list that holds all donations
    private static final List<Donations> donations = new ArrayList<>();

    // Add a donation
    public static void addDonation(Donations donation) {
        donations.add(donation);
    }

    // Get all donations
    public static List<Donations> getDonations() {
        return donations;
    }

    // Remove donation by position
    public static void removeDonation(int position) {
        if (position >= 0 && position < donations.size()) {
            donations.remove(position);
        }
    }
}
