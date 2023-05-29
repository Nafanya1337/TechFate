package com.shmakov.techfate.entities.inner;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.shmakov.techfate.entities.Review;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


public class Product implements Parcelable {
    public static int global_id = 1;
    protected int id;
    protected String categoryProduct;
    protected String mark;
    protected String name;
    protected int cost;
    protected int img;
    protected int[] images = new int[1];
    protected int[] amount = null;
    protected String[] colors;

    protected String[] configurations;
    protected int amountOfWatches;
    protected ArrayList<Review> reviews = new ArrayList<>();

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    protected Context context;

    public Product(Context context, String categoryProduct, String mark, String name, int cost,
                   int img, int[] imgs, String colors[], int[] amount, String[] configurations) {
        this.context = context;
        this.id = global_id;
        this.categoryProduct = new Category(categoryProduct).getCategory();
        this.mark = mark;
        this.name = name;
        this.cost = cost;
        this.img = img;
        if (imgs.length != 0)
            this.images = imgs;
        else
            this.images[0] = img;
        this.colors = colors;
        this.configurations = configurations;
        Random random = new Random();
        this.amountOfWatches = random.nextInt(3000) + 1;
        Category.addToArrayList(categoryProduct, this);
        global_id++;
    }

    public Product(Context context, String categoryProduct, String mark, String name, int cost,
                   int img, int[] imgs, String colors[], int[] amount) {
        this.context = context;
        this.id = global_id;
        this.categoryProduct = new Category(categoryProduct).getCategory();
        this.mark = mark;
        this.name = name;
        this.cost = cost;
        this.img = img;
        if (imgs.length != 0)
            this.images = imgs;
        else
            this.images[0] = img;
        this.colors = colors;
        this.amount = amount;
        Random random = new Random();
        this.amountOfWatches = random.nextInt(3000) + 1;
        Category.addToArrayList(categoryProduct, this);
        global_id++;
    }


    public Product(Product product) {
        this.id = product.id;
        this.categoryProduct = product.categoryProduct;
        this.mark = product.mark;
        this.name = product.name;
        this.cost = product.cost;
        this.img = product.img;
        this.images = product.images;
        this.amount = product.amount;
        this.colors = product.colors;
        this.amountOfWatches = product.amountOfWatches;
        this.reviews = product.reviews;
        this.configurations = product.configurations;
    }

    protected Product(Parcel in) {
        id = in.readInt();
        categoryProduct = in.readString();
        mark = in.readString();
        name = in.readString();
        cost = in.readInt();
        img = in.readInt();
        images = in.createIntArray();
        amount = in.createIntArray();
        colors = in.createStringArray();
        amountOfWatches = in.readInt();
        reviews = in.createTypedArrayList(Review.CREATOR);
        configurations = in.createStringArray();
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

    public int getReviewsAmount() {
        return reviews.size();
    }

    public float getAvgReviewsRating() {
        float sum = 0;
        for ( Review review : reviews ) {
            sum += review.getRating();
        }
        if (sum == 0) return 0f;
        sum = sum / reviews.size();
        sum = sum * 10;
        sum = Math.round(sum);
        return sum / 10;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int[] amount) {
        this.amount = amount;
    }

    public void setConfigurations(String[] configurations) {
        this.configurations = configurations;
    }

    public void setAmountOfWatches(int amountOfWatches) {
        this.amountOfWatches = amountOfWatches;
    }

    public void setCategoryProduct(String categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public void setConfiguration_colors(HashMap<String, int[]> configuration_colors) {
        this.configurations = configurations;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public static void setGlobal_id(int global_id) {
        Product.global_id = global_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImages(String[] images) {
        this.images = new int[images.length];
        for (int i =0; i < images.length; i++) {
            this.images[i] = context.getResources().getIdentifier(images[i], "drawable", context.getPackageName());
        }
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public int getCost() {
        return cost;
    }

    public int getId() {
        return id;
    }

    public int getImg() {
        return img;
    }

    public int[] getImages() {
        if (images.length != 0)
            return images;
        return new int[]{img};
    }

    public String getCategoryProduct() {
        return categoryProduct;
    }

    public String getMark() {
        return mark;
    }

    public String getName() {
        return name;
    }

    public int getAmountOfWatches() {
        return amountOfWatches;
    }

    public String[] getColors() {
        return colors;
    }

    public String[] getConfigurations() {
        if (configurations == null)
            return null;
        return configurations;
    }

    public int[] getAmount() {
        return amount;
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
        dest.writeInt(img);
        dest.writeIntArray(images);
        dest.writeIntArray(amount);
        dest.writeStringArray(colors);
        dest.writeInt(amountOfWatches);
        dest.writeTypedList(reviews);
        dest.writeStringArray(configurations);
    }

    public void addWatch() {
        this.amountOfWatches++;
    }
}
