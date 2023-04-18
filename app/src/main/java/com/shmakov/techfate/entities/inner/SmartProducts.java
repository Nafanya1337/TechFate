package com.shmakov.techfate.entities.inner;

import android.os.Parcel;

import com.shmakov.techfate.entities.DisplayInches_Res;
import com.shmakov.techfate.entities.Power_MinFreq_MaxFreq;
import com.shmakov.techfate.entities.StorageRAM_StorageSSD;

public class SmartProducts extends Product{

    protected AudioProduct audioProduct;
    protected VideoProduct videoProduct;
    protected StorageProduct storageProduct;

    SmartProducts(String categoryProduct, String mark, String name, int cost, String color, int img, int[] images, @Power_MinFreq_MaxFreq int[] audio, @DisplayInches_Res String[] video, @StorageRAM_StorageSSD int[] storage) {
        super(categoryProduct, mark, name, cost, color, img, images);
        audioProduct = new AudioProduct(audio[0], audio[1], audio[2]);
        videoProduct = new VideoProduct(video[0], video[1]);
        storageProduct = new StorageProduct(storage[0], storage[1]);
    }

    protected SmartProducts(Parcel in) {
        super(in);
        audioProduct = in.readParcelable(AudioProduct.class.getClassLoader());
        videoProduct = in.readParcelable(VideoProduct.class.getClassLoader());
        storageProduct = in.readParcelable(StorageProduct.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(audioProduct, flags);
        dest.writeParcelable(videoProduct, flags);
        dest.writeParcelable(storageProduct, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SmartProducts> CREATOR = new Creator<SmartProducts>() {
        @Override
        public SmartProducts createFromParcel(Parcel in) {
            return new SmartProducts(in);
        }

        @Override
        public SmartProducts[] newArray(int size) {
            return new SmartProducts[size];
        }
    };

    @Override
    public boolean isRelatives(Product product) {
        AudioProduct audio = (AudioProduct) product;
        VideoProduct video = (VideoProduct) product;
        return (audioProduct.isRelatives(audio) && videoProduct.isRelatives(video));
    }
}
