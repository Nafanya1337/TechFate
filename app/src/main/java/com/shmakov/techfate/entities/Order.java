package com.shmakov.techfate.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Order implements Parcelable {

    private final String name;

    private final Cart cart;

    private final String address;

    private final String deliveryMethod;

    private final int final_cost;


    public String getName() {
        return name;
    }

    public Order(String name, Cart cart, String address, String deliveryMethod, int final_cost) {
        this.name = name;
        this.cart = cart;
        this.address = address;
        this.deliveryMethod = deliveryMethod;
        this.final_cost = final_cost;
    }

    protected Order(Parcel in) {
        name = in.readString();
        cart = in.readParcelable(Cart.class.getClassLoader());
        address = in.readString();
        deliveryMethod = in.readString();
        final_cost = in.readInt();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public Cart getCart() {
        return cart;
    }

    public int getFinal_cost() {
        return final_cost;
    }

    public String getAddress() {
        return address;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(cart, flags);
        dest.writeString(address);
        dest.writeString(deliveryMethod);
        dest.writeInt(final_cost);
    }
}
