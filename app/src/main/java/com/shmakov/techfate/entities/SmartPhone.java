package com.shmakov.techfate.entities;

import com.shmakov.techfate.R;

public class SmartPhone extends Product{

    public static SmartPhone[] mass = {
      new SmartPhone("Смартфон", "Huawei", "P 30 Lite", 18000, "black", R.drawable.smartphones_img, 4, 120)
    };

    private int ram;
    private int ssd;


    public SmartPhone(String category, String mark, String name, int cost, String color, int img, int ram, int ssd) {
        super(category, mark, name, cost, color, img);
        this.ram = ram;
        this.ssd = ssd;
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


}
