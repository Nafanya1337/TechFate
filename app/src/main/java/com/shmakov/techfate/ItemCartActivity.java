package com.shmakov.techfate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.shmakov.techfate.adapters.ImageAdapter;
import com.shmakov.techfate.entities.Category;
import com.shmakov.techfate.entities.Headphones;
import com.shmakov.techfate.entities.Product;
import com.shmakov.techfate.mytools.StringWorker;

import java.util.ArrayList;

public class ItemCartActivity extends AppCompatActivity {

    public static final String PRODUCT_TAG = "PRODUCT";

    private Object current_product;
    private ViewPager imageSwitcher;
    private TextView category_product_name, item_name, product_cost;
    private FrameLayout mini_reviews_container, color_container, specifications_container, stars_reviews_container, all_reviews_container;
    private boolean headphones = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_cart);
        Bundle arguments = getIntent().getExtras();
        imageSwitcher = findViewById(R.id.product_images);
        category_product_name = findViewById(R.id.category_product_name);
        item_name = findViewById(R.id.item_name);
        product_cost = findViewById(R.id.product_cost);
        mini_reviews_container = findViewById(R.id.mini_reviews_container);
        color_container = findViewById(R.id.color_container);
        specifications_container = findViewById(R.id.specifications_container);
        stars_reviews_container = findViewById(R.id.stars_reviews_container);
        all_reviews_container = findViewById(R.id.all_reviews_container);
        if (arguments != null) {
            if (arguments.containsKey(Headphones.class.getSimpleName()))
                current_product = (Headphones) arguments.getParcelable(Headphones.class.getSimpleName());
            else
                current_product = arguments.getParcelable(Product.class.getSimpleName());
            makeInfoProduct();
        }
    }

    public void closeItem(View view) {
        finish();
    }

    public void addToCart(View view) {

    }

    public void makeInfoProduct() {
        if (current_product == null) return;
        int[] imgs;
        if (current_product instanceof Headphones) {
            Headphones headphones = (Headphones) current_product;
            category_product_name.setText(headphones.getCategoryProduct());
            item_name.setText(StringWorker.makeProductName(headphones.getMark(), headphones.getName()));
            product_cost.setText(StringWorker.makePriceString(headphones.getCost()));
            imgs = headphones.getImages();
        }
        else {
            Product product = (Product) current_product;
            category_product_name.setText(product.getCategoryProduct());
            item_name.setText(StringWorker.makeProductName(product.getMark(), product.getName()));
            product_cost.setText(StringWorker.makePriceString(product.getCost()));
            imgs = new int[1];
            imgs[0] = product.getImg();
        }
        ImageAdapter imageAdapter = new ImageAdapter(this, imgs);
        imageSwitcher.setAdapter(imageAdapter);
    }
}