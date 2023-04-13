package com.shmakov.techfate.entities;

public class SmartPhone extends Product{
    private int ram;
    private int ssd;


    SmartPhone(String category, String mark, String name, int cost, String color, int img) {
        super(category, mark, name, cost, color, img);
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


}
