package com.shmakov.techfate.entities;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.shmakov.techfate.entities.inner.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart implements Parcelable {
    private ArrayList<ProductInCart> products = new ArrayList<>();
    int total_cost = 0;

    float rate = 1.0f;

    protected Cart(Parcel in) {
        products = in.createTypedArrayList(ProductInCart.CREATOR);
        total_cost = in.readInt();
        rate = in.readFloat();
    }

    public Cart(){}

    public Cart(ArrayList<ProductInCart> products, int total_cost, float rate){
        this.products = products;
        this.total_cost = total_cost;
        this.rate = rate;
    }

    public void clear(){
        this.products.clear();
        this.total_cost = 0;
        this.rate = 1.0f;
    }

    public void addSum(int sum){total_cost += sum;}

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public void addAmount(int position) {
        products.get(position).addToAmount();
        total_cost += (int) (products.get(position).product.getCost() * rate);
    }

    public void subAmount(int position) {
        products.get(position).subToAmount();
        total_cost -= (int) (products.get(position).product.getCost() * rate);
    }

    private void resetCost(){
        total_cost = 0;
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i).product;
            total_cost += (int) (product.getCost() * products.get(i).amount * rate);
        }
    }

    public void setRate(float rate) {
        this.rate = rate;
        resetCost();
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public float getRate() {
        return rate;
    }

    public ArrayList<ProductInCart> getProducts() {
        return products;
    }

    public void addProducts(ArrayList<ProductInCart> products) {
        this.products.addAll(products);
    }

    public void addProductToCart(Product product, String selected_color, String selected_conf) {
        ProductInCart temp = new ProductInCart(product, selected_color, selected_conf);
        products.add(temp);
        total_cost += temp.product.getCost() * rate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeTypedList(products);
        dest.writeInt(total_cost);
        dest.writeFloat(rate);
    }
}
