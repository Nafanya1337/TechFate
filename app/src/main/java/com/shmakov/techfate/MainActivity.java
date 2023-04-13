package com.shmakov.techfate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.shmakov.techfate.fragments.account.AccountFragment;
import com.shmakov.techfate.fragments.cart.CartFragment;
import com.shmakov.techfate.fragments.home.HomeFragment;
import com.shmakov.techfate.fragments.search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView menu;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.nav_menu);
        navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupWithNavController(menu, navController);
    }
}