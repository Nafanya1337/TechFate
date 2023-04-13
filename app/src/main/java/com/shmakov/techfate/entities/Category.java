package com.shmakov.techfate.entities;

import com.shmakov.techfate.R;

public class Category {

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
