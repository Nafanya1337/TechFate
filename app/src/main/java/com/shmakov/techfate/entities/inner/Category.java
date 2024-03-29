package com.shmakov.techfate.entities.inner;



import com.shmakov.techfate.mytools.ImageManager;

import java.util.ArrayList;

import java.util.HashMap;

public class Category {

    public static HashMap<String, ArrayList<Product>> categories = new HashMap<>();

    public static ArrayList<Product> all = new ArrayList<>();

    public static final String SMARTPHONE_NAME_CATEGORY = "Смартфоны";
    public static final String WATCHES_NAME_CATEGORY = "Часы";
    public static final String TABLETS_NAME_CATEGORY = "Планшеты";
    public static final String HEADPHONES_NAME_CATEGORY = "Наушники";
    public static final String CONSOLES_NAME_CATEGORY = "Игровые приставки";
    public static final String LAPTOPS_NAME_CATEGORY = "Ноутбуки";
    public static final String MONITORS_NAME_CATEGORY = "Мониторы";

    private static final String[] Categories_names =
            {
                    SMARTPHONE_NAME_CATEGORY,
                    WATCHES_NAME_CATEGORY,
                    TABLETS_NAME_CATEGORY,
                    HEADPHONES_NAME_CATEGORY,
                    CONSOLES_NAME_CATEGORY,
                    LAPTOPS_NAME_CATEGORY,
                    MONITORS_NAME_CATEGORY
            };


    private int category_mini_img, category_background_img;
    private String category;

    public static ArrayList<String> getCategoriesNamesAsArrayList() {
        return new ArrayList<String>(categories.keySet());
    }

    public Category(String category){
        this.category = category;
        this.category_mini_img = ImageManager.findCategoryMiniIMG(category);
        this.category_background_img = ImageManager.findCategoryBackgroundIMG(category);
        if (categories.get(category) == null)
            categories.put(category, new ArrayList<>());
    }

    public static void addToArrayList(String category, Product product) {
        ArrayList<Product> currentArray = categories.get(category);
        currentArray.add(product);
        all.add(product);
    }


    public static ArrayList<Product> getAllProducts() {
        return all;
    }

    public static ArrayList<Product> getPopularProducts() {
        ArrayList<Product> popular = new ArrayList<>();
        for (int i=0; i<Categories_names.length; i++) {
            ArrayList<Product> categoryList = categories.get(Categories_names[i]);
            for (Product temp: categoryList) {
                if (temp.getAmountOfWatches() > 1000)
                    popular.add(temp);
            }
        }
        return popular;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static void init(){
        new Category(SMARTPHONE_NAME_CATEGORY);
        new Category(WATCHES_NAME_CATEGORY);
        new Category(TABLETS_NAME_CATEGORY);
        new Category(HEADPHONES_NAME_CATEGORY);
        new Category(CONSOLES_NAME_CATEGORY);
        new Category(LAPTOPS_NAME_CATEGORY);
        new Category(MONITORS_NAME_CATEGORY);
    }

    public String getCategory() {
        return category;
    }

}
