package com.example.touragency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder> {

    private Context context;
    private List<Tour> tourList;
    private OnAddToCartListener onAddToCartListener;

    public interface OnAddToCartListener {
        void onAddToCart(Tour tour);
    }

    public TourAdapter(Context context, List<Tour> tourList, OnAddToCartListener onAddToCartListener) {
        this.context = context;
        this.tourList = tourList;
        this.onAddToCartListener = onAddToCartListener;
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tour, parent, false);
        return new TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        Tour tour = tourList.get(position);
        holder.tourName.setText(tour.getName());
        holder.tourDescription.setText(tour.getDescription());
        holder.tourPrice.setText("$" + tour.getPrice());
        Picasso.get().load(tour.getImageUrl()).into(holder.tourImage);

        holder.addToCartButton.setOnClickListener(v -> {
            if (onAddToCartListener != null) {
                onAddToCartListener.onAddToCart(tour);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {
        ImageView tourImage;
        TextView tourName;
        TextView tourDescription;
        TextView tourPrice;
        Button addToCartButton;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            tourImage = itemView.findViewById(R.id.tour_image);
            tourName = itemView.findViewById(R.id.tour_name);
            tourDescription = itemView.findViewById(R.id.tour_description);
            tourPrice = itemView.findViewById(R.id.tour_price);
            addToCartButton = itemView.findViewById(R.id.add_to_cart_button);
        }
    }
}
