package com.example.touragency;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @NonNull
    public static List<String> getSus() {
        return Arrays.asList("admin@gmail.com", "admin1@example.com");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                Navigation.findNavController(this, R.id.fragment_container)
                        .navigate(R.id.cartFragment);
                return true;
            } else if (item.getItemId() == R.id.navigation_shop) {
                Navigation.findNavController(this, R.id.fragment_container)
                        .navigate(R.id.shopFragment);
                return true;
            } else if (item.getItemId() == R.id.navigation_about_app) {
                Navigation.findNavController(this, R.id.fragment_container)
                        .navigate(R.id.aboutAppFragment);
                return true;
            } else if (item.getItemId() == R.id.navigation_about_author) {
                Navigation.findNavController(this, R.id.fragment_container)
                        .navigate(R.id.aboutAuthorFragment);
                return true;
            } else if (item.getItemId() == R.id.navigation_manual) {
                Navigation.findNavController(this, R.id.fragment_container)
                        .navigate(R.id.manualFragment);
                return true;
            } else {
                return false;
            }
        });
    }
}
