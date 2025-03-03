package com.zomato.models;

import java.util.List;

public class OfferRequest {
    private int restaurant_id;
    private String offer_type;
    private int offer_value;
    private List<String> customer_segment;

    // Constructor
    public OfferRequest(int restaurant_id, String offer_type, int offer_value, List<String> customer_segment) {
        this.restaurant_id = restaurant_id;
        this.offer_type = offer_type;
        this.offer_value = offer_value;
        this.customer_segment = customer_segment;
    }

    // Getters and Setters
    public int getRestaurantId() { return restaurant_id; }
    public String getOfferType() { return offer_type; }
    public int getOfferValue() { return offer_value; }
    public List<String> getCustomerSegment() { return customer_segment; }
}
