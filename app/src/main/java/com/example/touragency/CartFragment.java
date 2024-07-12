package com.example.touragency;

import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private TourAdapter tourAdapter;
    private List<Tour> cartTourList;
    private static final String TAG = "CartFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartTourList = new ArrayList<>();
        tourAdapter = new TourAdapter(getContext(), cartTourList, tour -> {
            // Handle add to cart action if needed
        }, tour -> {
            // Handle remove from cart action
            String tourId = tour.getId();
            if (tourId != null && !tourId.isEmpty()) {
                DatabaseReference tourRef = FirebaseDatabase.getInstance().getReference("tours").child(tourId).child("added");
                tourRef.setValue(false)
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "Tour removed from cart successfully"))
                        .addOnFailureListener(e -> Log.e(TAG, "Failed to remove tour from cart", e));
            } else {
                Log.e(TAG, "Tour ID is null or empty");
            }
            loadCartToursFromDatabase(); // Refresh cart list
        });
        recyclerView.setAdapter(tourAdapter);
        loadCartToursFromDatabase();
        return view;
    }

    private void loadCartToursFromDatabase() {
        FirebaseDatabase.getInstance().getReference("tours").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartTourList.clear();
                for (DataSnapshot tourSnapshot : snapshot.getChildren()) {
                    Tour tour = tourSnapshot.getValue(Tour.class);
                    if (tour != null && tour.isAdded()) {
                        tour.setId(tourSnapshot.getKey());
                        cartTourList.add(tour);
                    }
                }
                tourAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Log.e(TAG, "Database error: " + error.getMessage());
            }
        });
    }
}
