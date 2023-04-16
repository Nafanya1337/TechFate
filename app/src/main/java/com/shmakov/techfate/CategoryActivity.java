package com.shmakov.techfate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.shmakov.techfate.entities.Cart;
import com.shmakov.techfate.fragments.home.category.CategoryHeaderFragment.goBack;

public class CategoryActivity extends AppCompatActivity implements goBack {

    Cart cart = new Cart();
    public static final String category_tag = "CATEGORY_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
    }


    @Override
    public void goBack(View view) {
        finish();
    }
}