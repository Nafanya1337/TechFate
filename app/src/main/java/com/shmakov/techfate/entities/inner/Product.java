package com.shmakov.techfate.entities.inner;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.shmakov.techfate.entities.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Product implements Parcelable {
    public static int global_id = 0;
    protected int id;
    protected String categoryProduct;
    protected String mark;
    protected String name;
    protected int cost;
    protected int img;
    protected int[] images = new int[1];
    protected int[] amount = null;
    protected String[] colors;
    protected HashMap<String, int[]> configuration_colors = null;
    protected int amountOfWatches;
    protected ArrayList<Review> reviews = new ArrayList<>();

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public Product(String categoryProduct, String mark, String name, int cost,
                   int img, int[] imgs, String colors[], HashMap<String, int[]> configuration_colors,
                   ArrayList<Review> reviews) {
        this(categoryProduct,
                mark,
                name,
                cost,
                img,
                imgs,
                colors,
                configuration_colors);
        this.reviews = reviews;
    }

    public Product(String categoryProduct, String mark, String name, int cost,
                   int img, int[] imgs, String colors[], HashMap<String, int[]> configuration_colors) {
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
        this.configuration_colors = configuration_colors;
        Random random = new Random();
        this.amountOfWatches = random.nextInt(3000) + 1;
        Category.addToArrayList(categoryProduct, this);
        global_id++;
    }

    public Product(String categoryProduct, String mark, String name, int cost,
                   int img, int[] imgs, String colors[], int[] amount) {
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
        configuration_colors = in.readHashMap(HashMap.class.getClassLoader());
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
        return reviews.size() > 0 ? sum / reviews.size() : 0f;
    }

    public void addReview(Review review) {
        reviews.add(review);
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
        return images;
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
        if (configuration_colors == null)
            return null;
        return configuration_colors.keySet().toArray(new String[0]);
    }

    public int[] getCurrentConfigurationAmount(String conf) {
        return configuration_colors.get(conf);
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
        dest.writeMap(configuration_colors);
    }
}
