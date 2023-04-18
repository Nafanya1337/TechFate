package com.shmakov.techfate.entities.inner;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

class AudioProduct extends Product {

    protected int power = -1, min_freq = -1, max_freq = -1;

    AudioProduct(String category, String mark, String name, int cost, String color, int img, int[] images, int power, int min_freq, int max_freq) {
        super(category, mark, name, cost, color, img, images);
        this.power = power;
        this.min_freq = min_freq;
        this.max_freq = max_freq;
    }


    AudioProduct(int power, int min_freq, int max_freq) {
        this.power = power;
        this.min_freq = min_freq;
        this.max_freq = max_freq;
    }

    protected AudioProduct(Parcel in) {
        super(in);
        power = in.readInt();
        min_freq = in.readInt();
        max_freq = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(power);
        dest.writeInt(min_freq);
        dest.writeInt(max_freq);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AudioProduct> CREATOR = new Creator<AudioProduct>() {
        @Override
        public AudioProduct createFromParcel(Parcel in) {
            return new AudioProduct(in);
        }

        @Override
        public AudioProduct[] newArray(int size) {
            return new AudioProduct[size];
        }
    };

    @Override
    public boolean isRelatives(Product product) {
        return false;
    }
}
