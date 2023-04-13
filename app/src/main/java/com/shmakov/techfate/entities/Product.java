package com.shmakov.techfate.entities;

import java.util.Calendar;
import java.util.Locale;

public class Product extends Category {
    private int cost;
    private String name;
    private String mark;
    private String color;
    private int img;

    public Product(String category, String mark, String name, int cost, String color, int img){
        super(category);
        this.cost = cost;
        this.name = name;
        this.color = color;
        this.mark = mark;
        this.img = img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getMiniInfo() {
        return this.getMark() + " " + this.getName() + this.getCost();
    }
}
