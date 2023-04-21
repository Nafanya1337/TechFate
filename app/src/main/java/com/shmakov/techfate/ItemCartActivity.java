package com.shmakov.techfate;

import static android.view.Gravity.CENTER;
import static com.shmakov.techfate.ReviewsFragment.REVIEWS_TAG;
import static com.shmakov.techfate.fragments.globals.ColorsFragment.COLORS_ARRAY_TAG;
import static com.shmakov.techfate.fragments.globals.ConfigurationFragment.AMOUNT_KEY;
import static com.shmakov.techfate.fragments.globals.ConfigurationFragment.CONF_KEY;
import static com.shmakov.techfate.fragments.globals.MiniReviewsFragment.AVG_RATING;
import static com.shmakov.techfate.fragments.globals.MiniReviewsFragment.REVIEWS_AMOUNT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.shmakov.techfate.adapters.ConfigurationsAdapter;
import com.shmakov.techfate.adapters.ImageAdapter;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.fragments.globals.ColorsFragment;
import com.shmakov.techfate.fragments.globals.ConfigurationFragment;
import com.shmakov.techfate.fragments.globals.MiniReviewsFragment;
import com.shmakov.techfate.mytools.StringWorker;

import java.util.ArrayList;

public class ItemCartActivity extends AppCompatActivity implements ConfigurationsAdapter.ChooseConf {

    public static final String PRODUCT_TAG = "PRODUCT";

    private Product current_product;
    private ViewPager imageSwitcher;
    private TextView category_product_name, item_name, product_cost, configuration_text;

    ColorsFragment colorsFragment;
    private TableLayout specifications_container;
    private FrameLayout mini_reviews_container, stars_reviews_container, all_reviews_container, colors_item_container, configurations_item_container;
    private boolean headphones = false;
    private FragmentManager fragmentManager;
    private Bundle arr = new Bundle();

    private String current_conf;

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
        configurations_item_container = findViewById(R.id.configurations_item_container);
        configuration_text = findViewById(R.id.configuration_text);
        makeAllColors();
        makeConfigurations();
        makeReviews();
        makeAllReviews();
        makeStarsReviews();
    }

    public void makeStarsReviews() {
        if (current_product == null) return;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putFloat(AVG_RATING, current_product.getAvgReviewsRating());
        bundle.putInt(REVIEWS_AMOUNT, current_product.getReviewsAmount());
        MiniReviewsFragment miniReviewsFragment = new MiniReviewsFragment();
        miniReviewsFragment.setArguments(bundle);
        ft.replace(stars_reviews_container.getId(), miniReviewsFragment).commit();
    }

    public void makeAllReviews() {
        if (current_product.getReviewsAmount() != 0) {
            ReviewsFragment reviewsFragment = new ReviewsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(REVIEWS_TAG, current_product.getReviews());
            reviewsFragment.setArguments(bundle);
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(all_reviews_container.getId(), reviewsFragment).commit();
        }
    }

    public void closeItem(View view) {
        finish();
    }

    public void addToCart(View view) {

    }

    public void makeReviews() {
        if (current_product == null) return;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putFloat(AVG_RATING, current_product.getAvgReviewsRating());
        bundle.putInt(REVIEWS_AMOUNT, current_product.getReviewsAmount());
        MiniReviewsFragment miniReviewsFragment = new MiniReviewsFragment();
        miniReviewsFragment.setArguments(bundle);
        ft.replace(mini_reviews_container.getId(), miniReviewsFragment).commit();
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

    public void makeAllColors(){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        String[] a = current_product.getColors();
        current_conf = a[0];
        arr.putStringArray(COLORS_ARRAY_TAG, a);
        String[] conf = current_product.getConfigurations();
        int[] amount;
        if (conf != null)
            amount = current_product.getCurrentConfigurationAmount(conf[0]);
        else
            amount = current_product.getAmount();
        colorsFragment = new ColorsFragment(amount);
        colorsFragment.setArguments(arr);
        ft.replace(colors_item_container.getId(), colorsFragment).commit();
    }

    private void makeConfigurations() {
        if (current_product.getConfigurations() != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ConfigurationFragment configurationFragment = new ConfigurationFragment();
            String[] b = current_product.getConfigurations();
            arr.putStringArray(CONF_KEY, b);
            configurationFragment.setArguments(arr);
            ft = fragmentManager.beginTransaction();
            ft.replace(configurations_item_container.getId(), configurationFragment).commit();
        }
        else {
            configuration_text.setVisibility(View.GONE);
            configurations_item_container.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateColors(String conf_name) {
        colorsFragment.updateColorsAvailable(current_product.getCurrentConfigurationAmount(conf_name), colorsFragment.selectedColor());
    }
}