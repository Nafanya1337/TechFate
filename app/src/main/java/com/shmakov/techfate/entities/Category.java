package com.shmakov.techfate.entities;

import android.util.Log;

import com.shmakov.techfate.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category {

    public static final List<Product> allProducts = new ArrayList<>();
    public List<Product> productCategoryList = new ArrayList<>();
    public static ArrayList<Category> categories = new ArrayList<>();

    public static HashMap<String, Integer> mini_images_categories;
    public static HashMap<String, Integer> background_images_categories;

    public static String[] categoryNames =
            {
                    "Смартфоны",
                    "Часы",
                    "Планшеты",
                    "Наушники",
                    "Игровые приставки",
                    "Ноутбуки",
                    "Мониторы"
            };

    static {
        mini_images_categories = new HashMap<>();
        mini_images_categories.put(categoryNames[0], R.drawable.smartphones_img);
        mini_images_categories.put(categoryNames[1], R.drawable.watches_img);
        mini_images_categories.put(categoryNames[2], R.drawable.tablets_img);
        mini_images_categories.put(categoryNames[3], R.drawable.headphones_img);
        mini_images_categories.put(categoryNames[4], R.drawable.consoles_img);
        mini_images_categories.put(categoryNames[5], R.drawable.laptop_img);
        mini_images_categories.put(categoryNames[6], R.drawable.monitors_img);
    }

    static {
        background_images_categories = new HashMap<>();
        background_images_categories.put(categoryNames[0], R.drawable.category_smartphones_back);
        background_images_categories.put(categoryNames[3], R.drawable.category_headphones_back);
    }


    public static void fillCategories(){
        for (int i = 0; i < categoryNames.length; i++) {
            categories.add(new Category(categoryNames[i], mini_images_categories.get(categoryNames[i])));
        }
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
