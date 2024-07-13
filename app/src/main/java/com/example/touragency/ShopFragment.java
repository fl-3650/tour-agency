package com.example.touragency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopFragment extends Fragment {

    private TourAdapter tourAdapter;
    private List<Tour> tourList;
    private Button addButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        addButton = view.findViewById(R.id.add_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tourList = new ArrayList<>();
        tourAdapter = new TourAdapter(getContext(), tourList, tour -> {}, tour -> {});
        recyclerView.setAdapter(tourAdapter);

        checkIfAdmin();

        addButton.setOnClickListener(v -> showAddTourDialog());

        loadToursFromDatabase();
        return view;
    }

    private void checkIfAdmin() {
        String userEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
                .getEmail();
        if (userEmail != null && MainActivity.getSus().contains(userEmail)) {
            addButton.setVisibility(View.VISIBLE);
        }
    }

    private void loadToursFromDatabase() {
        FirebaseDatabase.getInstance().getReference("tours")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tourList.clear();
                for (DataSnapshot tourSnapshot : snapshot.getChildren()) {
                    Tour tour = tourSnapshot.getValue(Tour.class);
                    if (tour != null) {
                        tour.setId(tourSnapshot.getKey());
                        tourList.add(tour);
                    } else {
                        Toast.makeText(getContext(), "Error",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                tourAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showAddTourDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Tour");

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText tourNameInput = new EditText(getContext());
        tourNameInput.setHint("Tour Name");
        layout.addView(tourNameInput);

        final EditText tourDescriptionInput = new EditText(getContext());
        tourDescriptionInput.setHint("Tour Description");
        layout.addView(tourDescriptionInput);

        final EditText tourPriceInput = new EditText(getContext());
        tourPriceInput.setHint("Tour Price");
        layout.addView(tourPriceInput);

        final EditText tourDurationInput = new EditText(getContext());
        tourDurationInput.setHint("Duration (days)");
        layout.addView(tourDurationInput);

        final EditText tourImageUrlInput = new EditText(getContext());
        tourImageUrlInput.setHint("Image URL");
        layout.addView(tourImageUrlInput);

        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = tourNameInput.getText().toString();
            String description = tourDescriptionInput.getText().toString();
            String price = tourPriceInput.getText().toString();
            String duration = tourDurationInput.getText().toString();
            String imageUrl = tourImageUrlInput.getText().toString();

            addTourToDatabase(name, description, price, duration, imageUrl);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void addTourToDatabase(String name, String description, String price,
                                   String duration, String imageUrl) {
        DatabaseReference toursRef = FirebaseDatabase.getInstance().getReference("tours");
        String tourId = toursRef.push().getKey();
        if (tourId != null) {
            Tour newTour = new Tour(tourId, name, description, Double.parseDouble(price),
                    Integer.parseInt(duration), imageUrl, false);
            toursRef.child(tourId).setValue(newTour)
                    .addOnSuccessListener(aVoid -> {
                        loadToursFromDatabase();
                        Toast.makeText(getContext(), "Tour added successfully",
                                Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(),
                            "Failed to add tour", Toast.LENGTH_SHORT).show());
        }
    }
}
