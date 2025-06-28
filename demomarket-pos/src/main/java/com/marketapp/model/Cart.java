package com.marketapp.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items = new ArrayList<>();
    private double total = 0.0;

    public void addItem(CartItem item) {
        items.add(item);
        updateTotal();
    }

    public void removeItem(int index) {
        items.remove(index);
        updateTotal();
    }

    public void updateTotal() {
        total = items.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }
}