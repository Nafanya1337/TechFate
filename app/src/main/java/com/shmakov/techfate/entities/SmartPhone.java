package com.shmakov.techfate.entities;


import java.util.ArrayList;
import java.util.List;

public class SmartPhone extends Product{

    private int ram;
    private int ssd;
    private ArrayList<Integer> photos;


    public SmartPhone(String mark, String name, int cost, String color, int img, int ram, int ssd, ArrayList<Integer> photos) {
        super(Category.SMARTPHONE_NAME_CATEGORY, mark, name, cost, color, img);
        this.ram = ram;
        this.ssd = ssd;
        photos = new ArrayList<>(photos);
    }

    public SmartPhone(String mark, String name, int cost) {
        super(Category.SMARTPHONE_NAME_CATEGORY, mark, name, cost);
    }

    @Override
    public String getMiniInfo() {
        return this.getMark() + this.getName() + Integer.toString(this.getCost());
    }

    public int getRam() {
        return ram;
    }

    public int getSsd() {
        return ssd;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public void setSsd(int ssd) {
        this.ssd = ssd;
    }

    public String getMaxInfo() {
        return this.getMiniInfo() + " " + this.getColor() + " " + this.getRam() + " " + this.getSsd();
    }


    public static void addSmartPhones(List<SmartPhone> phones){
        Category.categories.get(Category.SMARTPHONE_NAME_CATEGORY).addAll(phones);
    }

}
