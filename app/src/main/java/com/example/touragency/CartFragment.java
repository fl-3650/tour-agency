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

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private TourAdapter tourAdapter;
    private List<Tour> cartTourList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartTourList = new ArrayList<>();
        tourAdapter = new TourAdapter(getContext(), cartTourList, tour -> {
            // Handle remove from cart action
        });
        recyclerView.setAdapter(tourAdapter);
        loadCartTours();
        return view;
    }

    private void loadCartTours() {
        // Load tours from the cart (this can be implemented as per your app's logic)
    }
}
