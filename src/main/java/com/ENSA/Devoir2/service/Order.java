package com.ENSA.Devoir2.service;

import lombok.Data;

@Data
public class Order {
    private int id;
    private String date;
    private double amount;
    private int customer_id;

    // Constructor
    public Order(int id, String date, double amount, int customer_id) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.customer_id = customer_id;
    }
}
