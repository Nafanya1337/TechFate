package com.shmakov.techfate;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shmakov.techfate.adapters.CategoryAdapter;
import com.shmakov.techfate.fragments.home.HomeFragment;
import com.shmakov.techfate.fragments.home.category.CategoryHeaderFragment;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.openCategory {

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
}