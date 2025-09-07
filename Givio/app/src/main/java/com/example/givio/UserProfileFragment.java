package com.example.givio;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class UserProfileFragment extends Fragment {

    private TextView tvProfileName, tvProfileEmail, tvProfilePhone;
    private ImageView logout;
    private RecyclerView recyclerViewDonations;
    private DonationAdapter donationAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Profile views
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        tvProfilePhone = view.findViewById(R.id.tvProfilePhone);
        logout = view.findViewById(R.id.Logout);

        // RecyclerView
        recyclerViewDonations = view.findViewById(R.id.recyclerViewOrganizations);
        recyclerViewDonations.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load donations
        List<Donations> donationsList = DonationRepository.getDonations();

        // Adapter with delete listener
        donationAdapter = new DonationAdapter(getContext(), donationsList, position -> {
            // Remove from repository
            DonationRepository.removeDonation(position);
            // Notify adapter
            donationAdapter.notifyItemRemoved(position);
        });

        recyclerViewDonations.setAdapter(donationAdapter);

        // Get profile data from arguments
        Bundle args = getArguments();
        if (args != null) {
            tvProfileName.setText(args.getString("name", "N/A"));
            tvProfileEmail.setText(args.getString("email", "N/A"));
            tvProfilePhone.setText(args.getString("phone", "N/A"));
        }

        // Logout button
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LogIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the list every time the fragment becomes visible
        donationAdapter.notifyDataSetChanged();
    }
}
