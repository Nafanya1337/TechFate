package com.shmakov.techfate.entities;

import android.util.Log;

import com.shmakov.techfate.R;

import java.util.ArrayList;
import java.util.List;

public class Category {

    public static final List<Product> allProducts = new ArrayList<>();
    public List<Product> productCategoryList = new ArrayList<>();
    public static ArrayList<Category> categories = new ArrayList<>();

    public static void fillCategories(){
        categories.add(new Category("Смартфоны", R.drawable.smartphones_img));
        categories.add(new Category("Часы", R.drawable.watches_img));
        categories.add(new Category("Планшеты", R.drawable.tablets_img));
        categories.add(new Category("Наушники", R.drawable.headphones_img));
        categories.add(new Category("Игровые приставки", R.drawable.consoles_img));
        categories.add(new Category("Ноутбуки", R.drawable.laptop_img));
        categories.add(new Category("Мониторы", R.drawable.monitors_img));
    }

    private int Img_category;
    private String category;

    public Category(String category){
        this.category = category;
    }

    public Category(String category, int img){
        this.category = category;
        this.Img_category = img;
    }

    public void addToArrayList(Product product) {
        productCategoryList.add(product);
        allProducts.add(product);
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
