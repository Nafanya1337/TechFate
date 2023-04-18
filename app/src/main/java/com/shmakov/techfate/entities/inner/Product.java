package com.shmakov.techfate.entities.inner;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.shmakov.techfate.mytools.StringWorker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;


public class Product implements Parcelable {

    public static int global_id = 0;

    private static HashMap<String, Product> products = new HashMap<>();

    protected int id;
    protected String categoryProduct;
    protected String mark;
    protected String name;
    protected int cost;
    protected String color;
    protected int img;
    protected int[] images = new int[1];
    protected int amountOfWatches;
    protected ArrayList<Product> relatives = new ArrayList<>();

    Product(String categoryProduct, String mark, String name, int cost, String color, int img, int[] images) {
        boolean isRelatives = false;
        this.id = global_id;
        this.categoryProduct = new Category(categoryProduct).getCategory();
        this.mark = mark;
        this.name = name;
        this.cost = cost;
        this.color = color;
        this.img = img;
        this.images = images;
        this.amountOfWatches = new Random().nextInt(3000) + 1;
        global_id++;
        if (products.containsKey(StringWorker.makeProductName(mark, name))) {
            Product product = products.get(StringWorker.makeProductName(mark, name));
            isRelatives = this.isRelatives(product);
            if (!isRelatives)
                product.relatives.add(this);
        }
        else{
            products.put(StringWorker.makeProductName(mark, name), this);
            Category.addToArrayList(categoryProduct, this);
        }
    }

    Product(String categoryProduct, String mark, String name, int cost, String color, int img) {
        boolean isRelatives = false;
        this.id = global_id;
        this.categoryProduct = new Category(categoryProduct).getCategory();
        this.mark = mark;
        this.name = name;
        this.cost = cost;
        this.color = color;
        this.img = img;
        this.images[0] = img;
        this.amountOfWatches = new Random().nextInt(3000) + 1;
        global_id++;
        if (products.containsKey(StringWorker.makeProductName(mark, name))) {
            Product product = products.get(StringWorker.makeProductName(mark, name));
            isRelatives = this.isRelatives(this);
            if (!isRelatives)
                product.relatives.add(this);
        }
        else{
            products.put(StringWorker.makeProductName(mark, name), this);
            Category.addToArrayList(categoryProduct, this);
        }
    }


    protected Product(Parcel in) {
        id = in.readInt();
        categoryProduct = in.readString();
        mark = in.readString();
        name = in.readString();
        cost = in.readInt();
        color = in.readString();
        img = in.readInt();
        images = in.createIntArray();
        amountOfWatches = in.readInt();
        relatives = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String[] getAllRelativeColors(){
        Set<String> colors = new HashSet<>();
        colors.add(this.getColor());
        for (Product relative : relatives) {
            if (!colors.contains(relative.getColor()))
                colors.add(relative.getColor());
        }
        Log.d("mymy", "len" + colors.toArray().length);
        return colors.toArray(new String[0]);
    }

    Product(){}

    public boolean isRelatives(Product product){return false;}

    public int[] getImages() {
        return images;
    }

    public int getAmountOfWatches() {
        return amountOfWatches;
    }

    public int getImg() {
        return img;
    }

    public String getMark() {
        return mark;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Product> getRelatives() {
        return relatives;
    }

    public static HashMap<String, Product> getProducts() {
        return products;
    }

    public int getCost() {
        return cost;
    }

    public int getId() {
        return id;
    }

    public String getCategoryProduct() {
        return categoryProduct;
    }

    public void updateAmountOfWatches() {
        this.amountOfWatches++;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(categoryProduct);
        dest.writeString(mark);
        dest.writeString(name);
        dest.writeInt(cost);
        dest.writeString(color);
        dest.writeInt(img);
        dest.writeIntArray(images);
        dest.writeInt(amountOfWatches);
        dest.writeTypedList(relatives);
    }
}
