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
import com.shmakov.techfate.fragments.home.category.CategoryHeaderFragment;
import com.shmakov.techfate.fragments.home.category.CategoryHeaderFragment.goBack;

public class CategoryActivity extends AppCompatActivity implements goBack {

    private FrameLayout container;
    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    private CategoryHeaderFragment header;
    private String tittle;
    private Cart cart = new Cart();

    public static final String CATEGORY_TAG = "CATEGORY_TAG";
    public static final String CATEGORY_IMG_TAG = "CATEGORY_IMG_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        tittle = getIntent().getExtras().getString(CATEGORY_TAG);

        this.container = findViewById(R.id.header_container);
        fragmentManager = getSupportFragmentManager();
        ft = fragmentManager.beginTransaction();
        header = new CategoryHeaderFragment();
        header.setCategoryTittle(tittle);
        chooseAnImg();
        ft.replace(container.getId(), header).commit();
    }


    private void chooseAnImg() {
        switch (tittle) {
            case "Смартфоны":
                header.setCategoryBackgroundImage(R.drawable.category_smartphones_back);
                break;
            case "Наушники":
                header.setCategoryBackgroundImage(R.drawable.category_headphones_back);
                break;
        }
    }

    @Override
    public void goBack(View view) {
        finish();
    }
}