package com.shmakov.techfate.entities;

public class Watches extends Product{


    public Watches(String mark, String name, int cost, String color, int img) {
        super(Category.WATCHES_NAME_CATEGORY, mark, name, cost, color, img);
    }

    @Override
    public String getMiniInfo() {
        return null;
    }
}
