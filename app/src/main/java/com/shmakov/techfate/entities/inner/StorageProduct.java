package com.shmakov.techfate.entities.inner;

import android.os.Parcel;

class StorageProduct extends Product {

    protected int ram = 0, ssd = 0;

    StorageProduct(String categoryProduct, String mark, String name, int cost, String color, int img, int[] images, int ram, int ssd) {
        super(categoryProduct, mark, name, cost, color, img, images);
        this.ram = ram;
        this.ssd = ssd;
    }

    StorageProduct(int ram, int ssd) {
        this.ram = ram;
        this.ssd = ssd;
    }

    protected StorageProduct(Parcel in) {
        super(in);
        ram = in.readInt();
        ssd = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(ram);
        dest.writeInt(ssd);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StorageProduct> CREATOR = new Creator<StorageProduct>() {
        @Override
        public StorageProduct createFromParcel(Parcel in) {
            return new StorageProduct(in);
        }

        @Override
        public StorageProduct[] newArray(int size) {
            return new StorageProduct[size];
        }
    };

    @Override
    public boolean isRelatives(Product product) {
        StorageProduct storageProduct = (StorageProduct) product;
        return ram == storageProduct.ram && ssd == storageProduct.ssd;
    }
}
