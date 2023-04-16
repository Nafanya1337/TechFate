package com.shmakov.techfate.entities;


import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static ArrayList<Product> products = new ArrayList<>();
    private static int total_sum = 0;

    public static ArrayList<Product> getProducts() {
        return products;
    }

    public static int getTotal_sum() {
        return total_sum;
    }

    public static void setProducts(ArrayList<Product> productsList) {
        products = productsList;
    }

    public static void addFewProducts(List<Product> productsList) {
        products.addAll(productsList);
        for (int i=0; i<productsList.size(); i++)
            addPriceOfProduct(productsList.get(i).getCost());
    }

    public static void addProduct(Product product) {
        products.add(product);
        addPriceOfProduct(product.getCost());
    }

    public static void deleteProduct(int index) {
        if (index < 0 || index > products.size())
            return;
        subPriceOfProduct(products.get(index).getCost());
        products.remove(index);
    }

    private static void addPriceOfProduct(int price) {
        total_sum += price;
    }

    private static void subPriceOfProduct(int price) {
        total_sum -= price;
    }
}
