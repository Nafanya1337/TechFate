package com.shmakov.techfate.entities.inner;

import android.os.Parcel;

import java.util.Objects;

class VideoProduct extends Product{

    protected String display_inches = "0", resolution = "0";

    VideoProduct(String categoryProduct, String mark, String name, int cost, String color, int img, int[] images, String display_inches, String resolution) {
        super(categoryProduct, mark, name, cost, color, img, images);
        this.display_inches = display_inches;
        this.resolution = resolution;
    }

    VideoProduct(String display_inches, String resolution) {
        this.display_inches = display_inches;
        this.resolution = resolution;
    }

    protected VideoProduct(Parcel in) {
        super(in);
        display_inches = in.readString();
        resolution = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(display_inches);
        dest.writeString(resolution);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoProduct> CREATOR = new Creator<VideoProduct>() {
        @Override
        public VideoProduct createFromParcel(Parcel in) {
            return new VideoProduct(in);
        }

        @Override
        public VideoProduct[] newArray(int size) {
            return new VideoProduct[size];
        }
    };

    @Override
    public boolean isRelatives(Product product) {
        VideoProduct videoProduct = (VideoProduct) product;
        return (Objects.equals(videoProduct.display_inches, this.display_inches) && Objects.equals(videoProduct.resolution, this.resolution));
    }
}
