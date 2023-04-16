package com.shmakov.techfate.entities;

public class Wathes extends Product{


    public Wathes(String mark, String name, int cost, String color, int img) {
        super(Category.WATCHES_NAME_CATEGORY, mark, name, cost, color, img);
    }

    @Override
    public String getMiniInfo() {
        return null;
    }
}
