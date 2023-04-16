package com.shmakov.techfate.entities;

public class Headphones extends Product{


    public Headphones(String mark, String name, int cost, String color, int img) {
        super(Category.HEADPHONES_NAME_CATEGORY, mark, name, cost, color, img);
    }

    @Override
    public String getMiniInfo() {
        return null;
    }
}
