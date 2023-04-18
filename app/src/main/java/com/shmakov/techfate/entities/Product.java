package com.shmakov.techfate.entities;

import static com.shmakov.techfate.entities.Category.categories;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;


public class Product implements Parcelable {
    public static final int MAX_IMAGES = 7;

    public static final String EXIST_TAG = "+";
    public static final String NON_EXIST_TAG = "Отсутствует";

    private HashMap<String, String> specifications = new HashMap<>();

    protected Category categoryProduct;
    protected int cost;
    protected String name;
    protected String mark;
    protected ArrayList<String> colors;
    protected int img;
    protected int amountOfWatches;
    protected int[] images = new int[1];


    public HashMap<String, String> getSpecifications() {
        return specifications;
    }

    public Product(@NonNull Parcel in) {
        this.categoryProduct = new Category(in.readString());
        this.mark = in.readString();
        this.name = in.readString();
        this.cost = in.readInt();
        this.colors = in.readArrayList(ArrayList.class.getClassLoader());
        this.img = in.readInt();
        this.amountOfWatches = in.readInt();
        this.images = in.createIntArray();
        specifications = new HashMap<>();
        in.readMap(specifications, HashMap.class.getClassLoader());
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(categoryProduct.getCategory());
        dest.writeString(this.mark);
        dest.writeString(this.name);
        dest.writeInt(this.cost);
        dest.writeList(this.colors);
        dest.writeInt(this.img);
        dest.writeInt(this.amountOfWatches);
        dest.writeIntArray(this.images);
        dest.writeMap(this.specifications);
    }

    @Override
    public int describeContents() {
        return 0;
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


    public Product(String category, String mark, String name, int cost, String[] colors, int img, int[] imgs, HashMap<String, String> specifications) {
        this(category, mark, name, cost, colors, img, imgs);
        this.specifications = specifications;
    }

    public Product(String category, String mark, String name, int cost, String[] colors, int img, HashMap<String, String> specifications) {
        this(category, mark, name, cost, colors, img);
        this.specifications = specifications;
    }

    public Product(String category, String mark, String name, int cost, String[] colors, int img, int[] imgs) {
        this(category, mark, name, cost, colors, img);
        this.images = imgs;
    }

    public int[] getImages() {
        return images;
    }

    public Product(String category, String mark, String name, int cost, String[] colors, int img) {
        this.mark = mark;
        this.name = name;
        this.cost = cost;
        this.colors = new ArrayList<String>(Arrays.asList(colors));
        Random random = new Random();
        amountOfWatches = random.nextInt(100) + 10;
        if (mark.equals("Apple"))
            amountOfWatches = 200;
        Category.addToArrayList(category, this);
        this.categoryProduct = new Category(category);
        this.img = img;
        this.images[0] = img;
    }

    public Integer getAmountOfWatches() {
        return amountOfWatches;
    }

    public String getCategoryProduct() {
        return categoryProduct.getCategory();
    }

    public int getImg() {
        return img;
    }

    public String getMark() {
        return mark;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(ArrayList<String> color) {
        this.colors = color;
    }

}
