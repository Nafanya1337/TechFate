package com.shmakov.techfate;

import static com.shmakov.techfate.fragments.globals.ColorsFragment.COLORS_ARRAY_TAG;
import static com.shmakov.techfate.fragments.globals.ConfigurationFragment.CONF_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.shmakov.techfate.adapters.ImageAdapter;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.fragments.globals.ColorsFragment;
import com.shmakov.techfate.fragments.globals.ConfigurationFragment;
import com.shmakov.techfate.mytools.StringWorker;

public class ItemCartActivity extends AppCompatActivity {

    public static final String PRODUCT_TAG = "PRODUCT";

    private Product current_product;
    private ViewPager imageSwitcher;
    private TextView category_product_name, item_name, product_cost;
    private TableLayout specifications_container;
    private FrameLayout mini_reviews_container, stars_reviews_container, all_reviews_container, colors_item_container, configurations_item_container;
    private boolean headphones = false;
    private FragmentManager fragmentManager;
    private Bundle arr = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_item_cart);
        Bundle arguments = getIntent().getExtras();
        imageSwitcher = findViewById(R.id.product_images);
        category_product_name = findViewById(R.id.category_product_name);
        item_name = findViewById(R.id.item_name);
        product_cost = findViewById(R.id.product_cost);
        mini_reviews_container = findViewById(R.id.mini_reviews_container);
        specifications_container = findViewById(R.id.specifications_container);
        stars_reviews_container = findViewById(R.id.stars_reviews_container);
        all_reviews_container = findViewById(R.id.all_reviews_container);
        if (arguments != null) {
            current_product = arguments.getParcelable(Product.class.getSimpleName());
            makeInfoProduct();
        }
        colors_item_container = findViewById(R.id.colors_item_container);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        String[] a = current_product.getColors();
        arr.putStringArray(COLORS_ARRAY_TAG, a);
        ColorsFragment colorsFragment = new ColorsFragment();
        colorsFragment.setArguments(arr);
        ft.replace(colors_item_container.getId(), colorsFragment).commit();

        configurations_item_container = findViewById(R.id.configurations_item_container);
        a = current_product.getConfigurations();
        arr.putStringArray(CONF_KEY, a);
        ConfigurationFragment configurationFragment = new ConfigurationFragment();
        configurationFragment.setArguments(arr);
        ft = fragmentManager.beginTransaction();
        ft.replace(configurations_item_container.getId(), configurationFragment).commit();
    }

    public void closeItem(View view) {
        finish();
    }

    public void addToCart(View view) {

    }

    public void makeInfoProduct() {
        if (current_product == null) return;
        int[] imgs;
        String category = StringWorker.pluralMaker(current_product.getCategoryProduct());
        category_product_name.setText(category);
        item_name.setText(StringWorker.makeProductName(current_product.getMark(), current_product.getName()));
        product_cost.setText(StringWorker.makePriceString(current_product.getCost()));
        imgs = current_product.getImages();
        ImageAdapter imageAdapter = new ImageAdapter(this, imgs);
        imageSwitcher.setAdapter(imageAdapter);
        makeSpec(current_product);
    }

    public void makeSpec(Product product) {
    }
}