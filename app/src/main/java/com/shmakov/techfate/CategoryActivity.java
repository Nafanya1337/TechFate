package com.shmakov.techfate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.shmakov.techfate.entities.Cart;
import com.shmakov.techfate.fragments.globals.ItemsFragment;
import com.shmakov.techfate.fragments.home.category.CategoryHeaderFragment;
import com.shmakov.techfate.fragments.home.category.CategoryHeaderFragment.goBack;
import com.shmakov.techfate.mytools.ImageManager;

public class CategoryActivity extends AppCompatActivity implements goBack {

    private FrameLayout container, product_container;
    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    private CategoryHeaderFragment header;
    private ItemsFragment itemsFragment;
    private String tittle;
    private Cart cart = new Cart();

    public static final String CATEGORY_TAG = "CATEGORY_TAG";
    public static final String CATEGORY_IMG_TAG = "CATEGORY_IMG_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        fragmentManager = getSupportFragmentManager();
        ft = fragmentManager.beginTransaction();

        tittle = getIntent().getExtras().getString(CATEGORY_TAG);

        this.container = findViewById(R.id.header_container);
        header = new CategoryHeaderFragment();
        header.setCategoryTittle(tittle);
        header.setCategoryBackgroundImage(ImageManager.findCategoryBackgroundIMG(tittle));
        ft.replace(container.getId(), header).commit();


        ft = fragmentManager.beginTransaction();
        product_container = findViewById(R.id.category_items_container);
        itemsFragment = new ItemsFragment(tittle);
        ft.replace(product_container.getId(), itemsFragment);
        ft.commit();
    }

    @Override
    public void goBack(View view) {
        finish();
    }
}