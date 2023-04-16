package com.shmakov.techfate.entities;


import java.util.ArrayList;
import java.util.List;

public class SmartPhone extends Product{


    public static ArrayList<SmartPhone> mass = new ArrayList<SmartPhone>();

    private int ram;
    private int ssd;


    public SmartPhone(String mark, String name, int cost, String color, int img, int ram, int ssd) {
        super("Смартфон", mark, name, cost, color, img);
        this.ram = ram;
        this.ssd = ssd;
    }

    public SmartPhone(String mark, String name, int cost) {
        super("Смартфон", mark, name, cost);
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
        mass.addAll(phones);
    }

}
