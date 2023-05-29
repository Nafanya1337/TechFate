package com.shmakov.techfate.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.shmakov.techfate.R;

import java.util.ArrayList;

public class User implements Parcelable {
    private String name;
    private int id;

    String img_name;
    private String password;
    private int img = R.drawable.ava1;
    private String EmailAdress;
    private String phoneNumber;
    private Cart cart = new Cart();
    private ArrayList<Order> orders = new ArrayList<>();

    private ArrayList<String> addresses = new ArrayList<>();

    private ArrayList<Card> cards = new ArrayList<>();

    public User(){
        name = "ФИО";
        EmailAdress = "name@mail.ru";
    }

    public ArrayList<String> getAddresses() {
        return addresses;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addAddress(String address) {
        this.addresses.add(address);
    }

    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        password = in.readString();
        img = in.readInt();
        EmailAdress = in.readString();
        phoneNumber = in.readString();
        cart = in.readParcelable(Cart.class.getClassLoader());
        orders = in.createTypedArrayList(Order.CREATOR);
        addresses = in.createStringArrayList();
        cards = in.createTypedArrayList(Card.CREATOR);
        img_name = in.readString();
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

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }


    public User(int id, int img, String name, String EmailAdress, String phone, String password, Cart cart, ArrayList<Order> orders, ArrayList<String> addresses, ArrayList<Card> cards) {
        this(id, name, EmailAdress, phone, password, cart, orders, addresses, cards);
        this.img = img;
    }

    public User(int id, String name, String EmailAdress, String phone, String password, Cart cart, ArrayList<Order> orders, ArrayList<String> addresses,  ArrayList<Card> cards) {
        this.id = id;
        this.name = name;
        this.EmailAdress = EmailAdress;
        this.phoneNumber = phone;
        this.password = password;
        this.cart = cart;
        this.orders = orders;
        this.addresses = addresses;
        this.cards = cards;
    }

    public User(String name, String EmailAdress, int img, String phone, String password) {
        this.name = name;
        this.EmailAdress = EmailAdress;
        this.img = img;
        this.phoneNumber = phone;
        this.password = password;
    }

    public User(String name, String EmailAdress, String phone) {
        this.name = name;
        this.EmailAdress = EmailAdress;
        this.phoneNumber = phone;
    }




    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }

    public String getPhoneNumber() {
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Cart getCart() {
        return cart;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeInt(img);
        dest.writeString(EmailAdress);
        dest.writeString(phoneNumber);
        dest.writeParcelable(cart, flags);
        dest.writeTypedList(orders);
        dest.writeStringList(addresses);
        dest.writeTypedList(cards);
        dest.writeString(img_name);
    }
}
