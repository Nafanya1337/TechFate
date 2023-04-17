package com.shmakov.techfate.entities;


import android.os.Parcel;
import android.util.Log;

public class Headphones extends Product{

    public static final int MAX_IMAGES = 7;

    private boolean bluetooth = false;
    private int[] images;

    public Headphones(String mark, String name, int cost, String color, int img, boolean bluetooth) {
        super(Category.HEADPHONES_NAME_CATEGORY, mark, name, cost, color, img);
        this.bluetooth = bluetooth;
        images = new int[1];
        images[0] = img;
    }

    public Headphones(String mark, String name, int cost, String color, int img, boolean bluetooth, int[] images) {
        this(mark, name, cost, color, img, bluetooth );
        this.images = images;
    }

    public int[] getImages() {
        return images;
    }

    protected Headphones(Parcel in) {
        super(in);
        bluetooth = in.readByte() != 0;
        this.images = in.createIntArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (bluetooth ? 1 : 0));
        dest.writeIntArray(this.images);
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

    @Override
    public String getMiniInfo() {
        return null;
    }
}
