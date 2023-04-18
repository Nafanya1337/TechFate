package com.shmakov.techfate.entities.inner;

import android.os.Parcel;

public class Watches extends SmartProducts {

    public String OS = "NoN";

    Watches(String mark, String name, int cost, String color, int img, int[] images, int[] audio, String[] video, int[] storage, String OS) {
        super(Category.WATCHES_NAME_CATEGORY, mark, name, cost, color, img, images, audio, video, storage);
        this.OS = OS;
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
