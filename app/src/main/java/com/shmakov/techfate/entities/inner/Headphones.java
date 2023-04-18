package com.shmakov.techfate.entities.inner;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Headphones extends AudioProduct implements Parcelable {

    protected String type = "NoN";

    public Headphones(String mark, String name, int cost, String color, int img, int[] images, int power, int min_freq, int max_freq, String type) {
        super(Category.HEADPHONES_NAME_CATEGORY, mark, name, cost, color, img, images, power, min_freq, max_freq);
        this.type = type;
    }


    protected Headphones(Parcel in) {
        super(in);
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(type);
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
