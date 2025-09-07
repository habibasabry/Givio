package com.example.givio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrgAdapter extends RecyclerView.Adapter<OrgAdapter.ViewHolder> implements Filterable {

    private final List<Org> orgList;
    private final List<Org> orgListFiltered;
    private OnItemClickListener clickListener;

    // Click listener interface
    public interface OnItemClickListener {
        void onItemClick(Org org);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    public OrgAdapter(List<Org> orgList) {
        this.orgList = orgList;
        this.orgListFiltered = new ArrayList<>(orgList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_org, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Org org = orgListFiltered.get(position);
        holder.tvTitle.setText(org.getName());
        holder.tvDesc.setText(org.getDescription());
        holder.imgLogo.setImageResource(org.getImageRes());

        // Item click
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(org);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orgListFiltered.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView tvTitle, tvDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            tvTitle = itemView.findViewById(R.id.Title);
            tvDesc = itemView.findViewById(R.id.Desc);
        }
    }

    // Filterable implementation
    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    private final Filter nameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Org> filtered = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filtered.addAll(orgList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Org org : orgList) {
                    if (org.getName().toLowerCase().contains(filterPattern)) {
                        filtered.add(org);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtered;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            orgListFiltered.clear();
            //noinspection unchecked
            orgListFiltered.addAll((List<Org>) results.values);
            notifyDataSetChanged();
        }
    };
}
