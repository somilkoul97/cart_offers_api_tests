package com.zomato.models;

public class ApplyOfferRequest {
    private int cart_value;
    private int user_id;
    private int restaurant_id;

    // Constructor
    public ApplyOfferRequest(int cart_value, int user_id, int restaurant_id) {
        this.cart_value = cart_value;
        this.user_id = user_id;
        this.restaurant_id = restaurant_id;
    }

    // Getters and Setters
    public int getCartValue() { return cart_value; }
    public int getUserId() { return user_id; }
    public int getRestaurantId() { return restaurant_id; }
}
