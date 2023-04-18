package com.shmakov.techfate.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class Watches extends Product {

    private String OS;

    public static final String SMARTPHONES_DISPLAY_TAG = "Дисплей";

    public Watches(String mark, String name, int cost, String[] colors, int img, int[] imgs, String OS) {
        super(Category.WATCHES_NAME_CATEGORY, mark, name, cost, colors, img, imgs);
        this.OS = OS;
    }

    public Watches(String mark, String name, int cost, String[] colors, int img, String OS) {
        this(mark, name, cost, colors, img);
        this.OS = OS;
    }

    public Watches(String mark, String name, int cost, String[] colors, int img, HashMap<String, String> specification) {
        super(Category.WATCHES_NAME_CATEGORY, mark, name, cost, colors, img, specification);
    }

    public Watches(String mark, String name, int cost, String[] colors, int img) {
        super(Category.WATCHES_NAME_CATEGORY, mark, name, cost, colors, img);
        OS = "Неизвестно";
    }

    protected Watches(Parcel in) {
        super(in);
        OS = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(OS);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Watches> CREATOR = new Creator<Watches>() {
        @Override
        public Watches createFromParcel(Parcel in) {
            return new Watches(in);
        }

        @Override
        public Watches[] newArray(int size) {
            return new Watches[size];
        }
    };
}
