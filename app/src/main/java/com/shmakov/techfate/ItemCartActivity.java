package com.shmakov.techfate;


import static com.shmakov.techfate.ReviewsFragment.REVIEWS_TAG;
import static com.shmakov.techfate.fragments.globals.ColorsFragment.COLORS_ARRAY_TAG;
import static com.shmakov.techfate.fragments.globals.ConfigurationFragment.CONF_KEY;
import static com.shmakov.techfate.fragments.globals.MiniReviewsFragment.AVG_RATING;
import static com.shmakov.techfate.fragments.globals.MiniReviewsFragment.REVIEWS_AMOUNT;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shmakov.techfate.adapters.ColorAdapter;
import com.shmakov.techfate.adapters.ConfigurationsAdapter;
import com.shmakov.techfate.adapters.ImageAdapter;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.fragments.globals.ColorsFragment;
import com.shmakov.techfate.fragments.globals.ConfigurationFragment;
import com.shmakov.techfate.fragments.globals.MiniReviewsFragment;
import com.shmakov.techfate.mytools.StringWorker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.function.Predicate;


public class ItemCartActivity extends AppCompatActivity implements ColorAdapter.pickAColor, ConfigurationsAdapter.ChooseConf {

    public static final String PRODUCT_TAG = "PRODUCT";
    public static final String CONFIGURATION_TAG = "CONFIGURATION_TAG";
    public static final String COLOR_TAG = "COLOR_TAG";

    private Button addToCartButton;

    private com.shmakov.techfate.ReviewsFragment reviewsFragment;

    private ConfigurationFragment configurationFragment;
    private Product current_product;
    private ViewPager imageSwitcher;
    private TextView category_product_name, item_name, product_cost, configuration_text, text_amount_views;

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
            current_product = arguments.getParcelable(PRODUCT_TAG);
            makeInfoProduct();
        }
        colors_item_container = findViewById(R.id.colors_item_container);
        configurations_item_container = findViewById(R.id.configurations_item_container);
        configuration_text = findViewById(R.id.configuration_text);
        text_amount_views = findViewById(R.id.text_amount_views);
        text_amount_views.setText(current_product.getAmountOfWatches() + " за день");
        addToCartButton = findViewById(R.id.addToCartButton);
        makeAllColors();
        makeConfigurations();
        makeReviews();
        makeAllReviews();
        makeStarsReviews();
        checkForAdding(-1);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void checkForAdding(int pos){
        if (pos == -1) {
            Animation go_down = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            addToCartButton.setClickable(false);
            addToCartButton.setAnimation(go_down);
            addToCartButton.setVisibility(View.GONE);
            return;
        }
        if ((current_product.getAmount() != null && current_product.getAmount()[pos] <= 0) | (configurationFragment != null && current_product.getCurrentConfigurationAmount(configurationFragment.getConfiguration())[pos] <= 0)) {
            if (addToCartButton.getVisibility() == View.VISIBLE) {
                addToCartButton.setClickable(false);
                Animation go_down = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                addToCartButton.setAnimation(go_down);
                addToCartButton.setVisibility(View.GONE);
            }
        } else {
            if (addToCartButton.getVisibility() == View.GONE | addToCartButton.getText() != "Добавить в корзину") {
                addToCartButton.setClickable(true);
                addToCartButton.setBackgroundColor(Color.parseColor("#FFDB47"));
                addToCartButton.setText("Добавить в корзину");
                Animation go_up = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                addToCartButton.setAnimation(go_up);
                addToCartButton.setVisibility(View.VISIBLE);
            }
        }
    }

    public void makeStarsReviews() {
    }

    public void makeAllReviews() {
        reviewsFragment = new com.shmakov.techfate.ReviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(REVIEWS_TAG, current_product.getReviews());
        reviewsFragment.setArguments(bundle);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(all_reviews_container.getId(), reviewsFragment).commit();
    }


    Intent main_data;
    @Override
    protected void onResume() {
        super.onResume();
        main_data = new Intent();
        main_data.putExtra("requestCode", getIntent().getIntExtra("requestCode", 0));
        setResult(RESULT_CANCELED, main_data);
    }

    public void closeItem(View view) {
        finish();
    }

    public void showMore(View view){
        reviewsFragment.showMore(view);
    }

    public ArrayList<Product> products = new ArrayList<>();
    public void addToCart(View view) {
        Button btn = ((Button)view);
        if (btn.getText().equals("Добавить в корзину")) {
            Bundle bundle = new Bundle();
            products.add(current_product);
            bundle.putParcelable(PRODUCT_TAG, current_product);
            String color =  current_product.getColors()[colorsFragment.selectedColor()];
            bundle.putString(COLOR_TAG, color);
            if (configurationFragment != null)
                bundle.putString(CONFIGURATION_TAG, configurationFragment.getConfiguration());
            else
                bundle.putString(CONFIGURATION_TAG, "");
            main_data.putExtras(bundle);
            main_data.putExtra("requestCode", getIntent().getIntExtra("requestCode", 0));
            setResult(RESULT_OK, main_data);
            btn.setText("Удалить из корзины");
            btn.setBackgroundColor(Color.parseColor("#ff4d4d"));
        }
        else {
            setResult(RESULT_CANCELED, main_data);
            btn.setText("Добавить в корзину");
            btn.setBackgroundColor(Color.parseColor("#FFDB47"));
        }

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
            configurationFragment = new ConfigurationFragment();
            String[] b = current_product.getConfigurations();
            arr.putStringArray(CONF_KEY, b);
            configurationFragment.setArguments(arr);
            ft = fragmentManager.beginTransaction();
            ft.replace(configurations_item_container.getId(), configurationFragment).commit();
        } else {
            configuration_text.setVisibility(View.GONE);
            configurations_item_container.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void pickAColor(int position) {
        checkForAdding(position);
    }

    @Override
    public void updateColors(String conf) {
        colorsFragment.updateColorsAvailable(current_product.getCurrentConfigurationAmount(conf), colorsFragment.selectedColor());
        checkForAdding(colorsFragment.selectedColor());
    }
}