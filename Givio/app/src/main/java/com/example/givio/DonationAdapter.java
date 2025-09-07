package com.example.givio;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationViewHolder> {

    private final Context context;
    private final List<Donations> donationList;
    private final OnDeleteClickListener deleteListener;

    // Interface for delete action
    public interface OnDeleteClickListener {
        void onDelete(int position);
    }

    // Constructor
    public DonationAdapter(Context context, List<Donations> donationList, OnDeleteClickListener deleteListener) {
        this.context = context;
        this.donationList = donationList;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donations, parent, false);
        return new DonationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        Donations donation = donationList.get(position);

        holder.tvTitle.setText(donation.getTitle());
        holder.tvCategory.setText(donation.getCategory());
        holder.tvDescription.setText(donation.getDescription());
        holder.tvLocation.setText(donation.getLocation());


        // Load image directly from Uri string
        if (donation.getImageUri() != null) {
            Glide.with(context)
                    .load(donation.getImageUri()) // pass Uri directly
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.imgDonation);
        } else {
            holder.imgDonation.setImageResource(R.drawable.ic_launcher_background);
        }



        // Delete button listener
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donationList.size();
    }

    public static class DonationViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDonation, btnDelete;
        TextView tvTitle, tvCategory, tvDescription, tvLocation;

        public DonationViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDonation = itemView.findViewById(R.id.imgDonation);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
