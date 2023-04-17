package com.shmakov.techfate.entities;

import static com.shmakov.techfate.entities.Category.categories;

import java.util.Comparator;
import java.util.Random;


public abstract class Product {
    protected Category categoryProduct;
    protected int cost;
    protected String name;
    protected String mark;
    protected String color;
    protected int img;
    protected int amountOfWatches;

    public Product(String category, String mark, String name, int cost, String color, int img) {
        this(category, mark, name, cost, color);
        this.img = img;
    }

    public Product(String category, String mark, String name, int cost, String color) {
        this(category, mark, name, cost);
        this.color = color;
        this.img = 0;
    }

    public Product(String category, String mark, String name, int cost) {
        this.mark = mark;
        this.name = name;
        this.cost = cost;
        this.color = "null";
        this.img = 0;
        Random random = new Random();
        amountOfWatches = random.nextInt(100) + 10;
        if (mark.equals("Apple"))
            amountOfWatches = 200;
        Category.addToArrayList(category, this);
    }


    public Integer getAmountOfWatches() {
        return amountOfWatches;
    }

    public String getCategoryProduct() {
        return categoryProduct.getCategory();
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

    abstract public String getMiniInfo();
}
