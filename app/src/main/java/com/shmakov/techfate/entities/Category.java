package com.shmakov.techfate.entities;

import com.shmakov.techfate.R;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private final List<Product> productCategoryList = new ArrayList<>();

    private int Img_category;
    private String category;

    public Category(String category){
        this.category = category;
        this.Img_category = R.drawable.headphones_img;
    }

    public Category(String category, int img){
        this.category = category;
        this.Img_category = img;
    }

    public void addToArrayList(Product product) {
        productCategoryList.add(product);
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setImg(int img) {
        Img_category = img;
    }

    public int getImg() {
        return Img_category;
    }

}
