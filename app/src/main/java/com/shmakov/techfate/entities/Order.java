package com.shmakov.techfate.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Order implements Parcelable {

    private final String name;

    private final Cart cart;

    public static int global_id = 1;

    int id;

    private String status = "В ОБРАБОТКЕ";

    private final int delivery_cost;

    private final String address;

    private final String deliveryMethod;

    private final String promocodeName;

    private final Float promocodeRate;

    private final String paymentMethod;

    private final int final_cost;


    public String getName() {
        return name;
    }

    public Order(int id, String name, Cart cart, String address, String deliveryMethod, String paymentMethod, int final_cost, int delivery_cost, String promocodeName, Float promocodeRate) {
        this.name = name;
        this.cart = cart;
        this.address = address;
        this.deliveryMethod = deliveryMethod;
        this.paymentMethod = paymentMethod;
        this.final_cost = final_cost;
        this.delivery_cost = delivery_cost;
        this.promocodeName = promocodeName;
        this.promocodeRate = promocodeRate;
        this.id = id;

    }

    public Order(String name, Cart cart, String address, String deliveryMethod, String paymentMethod, int final_cost, int delivery_cost, String promocodeName, Float promocodeRate) {
        this.name = name;
        this.cart = cart;
        this.address = address;
        this.deliveryMethod = deliveryMethod;
        this.paymentMethod = paymentMethod;
        this.final_cost = final_cost;
        this.delivery_cost = delivery_cost;
        this.promocodeName = promocodeName;
        this.promocodeRate = promocodeRate;
        id = global_id;
        global_id++;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    protected Order(Parcel in) {
        name = in.readString();
        cart = in.readParcelable(Cart.class.getClassLoader());
        address = in.readString();
        deliveryMethod = in.readString();
        paymentMethod = in.readString();
        final_cost = in.readInt();
        delivery_cost = in.readInt();
        promocodeName = in.readString();
        promocodeRate = in.readFloat();
        status = in.readString();
        id = in.readInt();
    }

    public int getId() {
        return id;
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

    public int getDelivery_cost() {
        return delivery_cost;
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

    public Float getPromocodeRate() {
        return promocodeRate;
    }

    public String getPromocodeName() {
        return promocodeName;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(cart, flags);
        dest.writeString(address);
        dest.writeString(deliveryMethod);
        dest.writeString(paymentMethod);
        dest.writeInt(final_cost);
        dest.writeInt(delivery_cost);
        dest.writeString(promocodeName);
        dest.writeFloat(promocodeRate);
        dest.writeString(status);
        dest.writeInt(id);
    }
}
