package com.shmakov.techfate.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.shmakov.techfate.entities.inner.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductInCart implements Parcelable {
    public Product product;
    int amount = 1;
    String selected_color;
    String selected_configuration;

    public ProductInCart (Product product, String selected_color, String selected_configuration) {
        this.product = product;
        this.selected_color = selected_color;
        this.selected_configuration = selected_configuration;
    }

    public Product getProduct() {
        return product;
    }

    protected ProductInCart(Parcel in) {
        product = in.readParcelable(Product.class.getClassLoader());
        amount = in.readInt();
        selected_color = in.readString();
        selected_configuration = in.readString();
    }

    public static final Creator<ProductInCart> CREATOR = new Creator<ProductInCart>() {
        @Override
        public ProductInCart createFromParcel(Parcel in) {
            return new ProductInCart(in);
        }

        @Override
        public ProductInCart[] newArray(int size) {
            return new ProductInCart[size];
        }
    };

    public void addToAmount() {
        amount++;
    }

    public void subToAmount() {
        amount--;
    }

    public int getAmount() {
        return amount;
    }

    public String getSelected_color() {
        return selected_color;
    }

    public String getSelected_configuration() {
        return selected_configuration;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(product, flags);
        dest.writeInt(amount);
        dest.writeString(selected_color);
        dest.writeString(selected_configuration);
    }
}
