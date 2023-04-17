package com.shmakov.techfate.entities;


import android.os.Parcel;
import android.util.Log;

import java.util.Collections;
import java.util.HashMap;

public class Headphones extends Product{


    public static final String HEADPHONES_BLUETOOTH_TAG = "Подключение по Bluetooth";
    public static final String HEADPHONES_WIRED_TAG = "Проводное подключение";
    public static final String HEADPHONES_NOISECANCELLATION_TAG = "Система активного шумоподавления";
    public static final String HEADPHONES_MICRO_TAG = "Встроенный микрофон";


    public Headphones(String mark, String name, int cost, String color, int img, int[] images, HashMap<String, String> specifications) {
        super(Category.HEADPHONES_NAME_CATEGORY, mark, name, cost, color, img, images, specifications);
    }

    public Headphones(String mark, String name, int cost, String color, int img, HashMap<String, String> specifications) {
        super(Category.HEADPHONES_NAME_CATEGORY, mark, name, cost, color, img, specifications);
    }

    public Headphones(String mark, String name, int cost, String color, int img, int[] images) {
        super(Category.HEADPHONES_NAME_CATEGORY, mark, name, cost, color, img, images);
    }

    public Headphones(String mark, String name, int cost, String color, int img) {
        super(Category.HEADPHONES_NAME_CATEGORY, mark, name, cost, color, img);
    }

    protected Headphones(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Headphones> CREATOR = new Creator<Headphones>() {
        @Override
        public Headphones createFromParcel(Parcel in) {
            return new Headphones(in);
        }

        @Override
        public Headphones[] newArray(int size) {
            return new Headphones[size];
        }
    };

}
