package com.example.touragency;

public class Tour {
    private String id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private String imageUrl;
    private boolean added;

    public Tour() {
        // Default constructor required for calls to DataSnapshot.getValue(Tour.class)
    }

    public Tour(String id, String name, String description, double price, int duration,
                String imageUrl, boolean added) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.imageUrl = imageUrl;
        this.added = added;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }
}
