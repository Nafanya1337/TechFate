package com.example.techfate;

public class Product {
    private int price;
    private String name = "";

    Product(){}

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
