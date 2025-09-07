package com.example.givio;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class OrgListFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrgAdapter adapter;
    private List<Org> organizationList;

    public OrgListFragment() {
        // Required empty public constructor
    }

    public static OrgListFragment newInstance(String param1, String param2) {
        OrgListFragment fragment = new OrgListFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_org_list, container, false);

        // Setup RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewOrganizations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create sample data (6 organizations)
        organizationList = new ArrayList<>();
        organizationList.add(new Org(
                "Life Line",
                "Crisis support and mental health guidance.",
                R.drawable.org6,
                "123 Hope Street, Kuala Lumpur, 50000, Malaysia",
                "+60123456789",
                "contact@lifeline.org"
        ));
        organizationList.add(new Org(
                "Beyond Blue",
                "Mental health programs and awareness campaigns.",
                R.drawable.org5,
                "45 Wellness Avenue, Penang, 10200, Malaysia",
                "+60198765432",
                "info@beyondblue.org"
        ));
        organizationList.add(new Org(
                "National Breast Cancer",
                "Supporting patients and funding research.",
                R.drawable.org4,
                "78 Care Road, Johor Bahru, 80000, Malaysia",
                "+60112233445",
                "support@nbc.org"
        ));
        organizationList.add(new Org(
                "Unicef for Every Child",
                "Education, healthcare, and child support programs.",
                R.drawable.org3,
                "12 Children Lane, Kuching, 93100, Malaysia",
                "+60223344556",
                "hello@unicef.org"
        ));
        organizationList.add(new Org(
                "World Food Programme",
                "Fighting hunger and promoting food security.",
                R.drawable.org2,
                "56 Relief Street, Malacca, 75000, Malaysia",
                "+60334455667",
                "contact@wfp.org"
        ));
        organizationList.add(new Org(
                "Mercy to Humanity",
                "Humanitarian aid and disaster relief programs.",
                R.drawable.org1,
                "89 Aid Avenue, Kota Kinabalu, 88000, Malaysia",
                "+60445566778",
                "info@mercy.org"
        ));

        // Set Adapter
        adapter = new OrgAdapter(organizationList);
        recyclerView.setAdapter(adapter);

        // Set item click listener to open OrgDetails activity
        adapter.setOnItemClickListener(org -> {
            Intent intent = new Intent(getContext(), OrgDetails.class);
            intent.putExtra("title", org.getName());
            intent.putExtra("description", org.getDescription());
            intent.putExtra("address", org.getAddress());
            intent.putExtra("phone", org.getPhone());
            intent.putExtra("email", org.getEmail());
            intent.putExtra("image", org.getImageRes());
            startActivity(intent);
        });

        return view;
    }
}
