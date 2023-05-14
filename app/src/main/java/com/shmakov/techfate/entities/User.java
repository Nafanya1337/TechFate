package com.shmakov.techfate.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.shmakov.techfate.R;

import java.util.ArrayList;

public class User implements Parcelable {

    private String name;
    private int img = R.drawable.simple_avatar;
    private String EmailAdress;
    private int phoneNumber;

    private Cart cart = new Cart();

    private ArrayList<Order> orders = new ArrayList<>();

    public User(){
        name = "Абоба абобович";
        EmailAdress = "aboba@mail.ru";
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public User(String name, String EmailAdress, int img, int phone) {
        this.name = name;
        this.EmailAdress = EmailAdress;
        this.img = img;
        this.phoneNumber = phone;
    }

    public User(String name, String EmailAdress, int phone) {
        this.name = name;
        this.EmailAdress = EmailAdress;
        this.phoneNumber = phone;
    }


    protected User(Parcel in) {
        name = in.readString();
        img = in.readInt();
        EmailAdress = in.readString();
        phoneNumber = in.readInt();
        cart = in.readParcelable(Cart.class.getClassLoader());
        orders = in.createTypedArrayList(Order.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAdress() {
        return EmailAdress;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmailAdress(String emailAdress) {
        EmailAdress = emailAdress;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(img);
        dest.writeString(EmailAdress);
        dest.writeInt(phoneNumber);
        dest.writeParcelable(cart, flags);
        dest.writeTypedList(orders);
    }
}
