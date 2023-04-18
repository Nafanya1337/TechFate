package com.shmakov.techfate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.shmakov.techfate.adapters.ProductAdapter;
import com.shmakov.techfate.entities.Cart;
import com.shmakov.techfate.entities.inner.Category;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.fragments.globals.ItemsFragment;
import com.shmakov.techfate.fragments.home.category.CategoryHeaderFragment;
import com.shmakov.techfate.fragments.home.category.CategoryHeaderFragment.goBack;
import com.shmakov.techfate.mytools.FragmentAdapterUpdater;
import com.shmakov.techfate.mytools.ImageManager;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements goBack, ProductAdapter.onClickProduct {

    private FrameLayout container, product_container;
    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    private CategoryHeaderFragment header;
    private ItemsFragment itemsFragment;
    private String tittle;
    private Cart cart = new Cart();
    private Spinner spinner;
    private TextView category_amount, category_available;
    private FragmentAdapterUpdater fragmentAdapterUpdater;

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

        String[] array_modes = {
                "По возрастанию",
                "По убыванию",
                "По популярности"
        };

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                array_modes
        );

        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinner.setAdapter(adapter);

        fragmentAdapterUpdater = new FragmentAdapterUpdater(itemsFragment, ft, product_container.getId());
        spinner.setOnItemSelectedListener(fragmentAdapterUpdater);

        category_amount = findViewById(R.id.category_amount);
        ArrayList<Product> products = Category.categories.get(tittle);
        int amount = products.size();
        String amounts = getResources().getQuantityString(R.plurals.products, amount, amount);
        category_amount.setText(amounts);

        category_available = findViewById(R.id.category_available);
        String category_available_str = getResources().getQuantityString(R.plurals.avaliable, amount);
        category_available.setText(category_available_str);
    }

    @Override
    public void goBack(View view) {
        finish();
    }

    @Override
    public void onClickProduct(View view, Product product) {
        Intent intent = new Intent(this, ItemCartActivity.class);
        intent.putExtra(Product.class.getSimpleName(), product);
        startActivity(intent);
    }
}