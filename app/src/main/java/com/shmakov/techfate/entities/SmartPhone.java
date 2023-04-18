package com.shmakov.techfate.entities;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SmartPhone extends Product {

    public static final int MAX_IMAGES = 7;

    public static final String SMARTPHONES_DISPLAY_TAG = "Дисплей";

    private int ram;
    private int ssd;
    private int[] images;


    public SmartPhone(String mark, String name, int cost, String[] colors, int img, int[] photos, int ram, int ssd) {
        super(Category.SMARTPHONE_NAME_CATEGORY, mark, name, cost, colors, img, photos);
        this.ram = ram;
        this.ssd = ssd;
    }

    public SmartPhone(String mark, String name, int cost, String[] colors, int img, HashMap<String, String> specifications) {
        super(Category.SMARTPHONE_NAME_CATEGORY, mark, name, cost, colors, img, specifications);
    }

    public SmartPhone(String mark, String name, int cost, String[] colors, int img, int ram, int ssd) {
        this(mark, name, cost, colors, img);
        this.ram = ram;
        this.ssd = ssd;
    }

    public SmartPhone(String mark, String name, int cost, String[] colors, int img) {
        super(Category.SMARTPHONE_NAME_CATEGORY, mark, name, cost, colors, img);
        ram = 0;
        ssd = 0;
    }

    protected SmartPhone(Parcel in) {
        super(in);
        ram = in.readInt();
        ssd = in.readInt();
        images = in.createIntArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(ram);
        dest.writeInt(ssd);
        dest.writeIntArray(images);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SmartPhone> CREATOR = new Creator<SmartPhone>() {
        @Override
        public SmartPhone createFromParcel(Parcel in) {
            return new SmartPhone(in);
        }

        @Override
        public SmartPhone[] newArray(int size) {
            return new SmartPhone[size];
        }
    };

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


    public static void addSmartPhones(List<SmartPhone> phones){
        Category.categories.get(Category.SMARTPHONE_NAME_CATEGORY).addAll(phones);
    }

}
