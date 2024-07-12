package com.example.touragency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private RecyclerView recyclerView;
    private TourAdapter tourAdapter;
    private List<Tour> tourList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tourList = new ArrayList<>();
        tourAdapter = new TourAdapter(getContext(), tourList, tour -> {
            // Handle add to cart action
        });
        recyclerView.setAdapter(tourAdapter);
        loadToursFromDatabase();
        return view;
    }

    private void loadToursFromDatabase() {
        FirebaseDatabase.getInstance().getReference("tours").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tourList.clear();
                for (DataSnapshot tourSnapshot : snapshot.getChildren()) {
                    Tour tour = tourSnapshot.getValue(Tour.class);
                    tourList.add(tour);
                }
                tourAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
}
