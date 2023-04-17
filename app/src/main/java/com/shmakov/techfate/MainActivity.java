package com.shmakov.techfate;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shmakov.techfate.adapters.CategoryAdapter;
import com.shmakov.techfate.adapters.ProductAdapter;
import com.shmakov.techfate.entities.Headphones;
import com.shmakov.techfate.entities.Product;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.openCategory, ProductAdapter.onClickProduct {

    BottomNavigationView menu;
    NavController navController;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        menu = findViewById(R.id.nav_menu);
        navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupWithNavController(menu, navController);
    }

    @Override
    public void openCategory(String category) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra(CategoryActivity.CATEGORY_TAG, category);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menu = null;
        navController = null;
        fragmentManager = null;
    }

    @Override
    public void onClickProduct(View view, Product product) {
        Intent intent = new Intent(this, ItemCartActivity.class);
        if (product instanceof Headphones)
            intent.putExtra(Headphones.class.getSimpleName(), (Headphones) product);
        else
            intent.putExtra(Product.class.getSimpleName(), product);
        startActivity(intent);
    }
}