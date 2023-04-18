package com.shmakov.techfate.entities.inner;

import android.os.Parcel;

public class SmartPhone extends SmartProducts{

    SmartPhone(String mark, String name, int cost, String color, int img, int[] images, int[] audio, String[] video, int[] storage) {
        super(Category.SMARTPHONE_NAME_CATEGORY, mark, name, cost, color, img, images, audio, video, storage);
    }

    protected SmartPhone(Parcel in) {
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
}
