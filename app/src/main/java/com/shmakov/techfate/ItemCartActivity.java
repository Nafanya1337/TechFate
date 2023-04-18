package com.shmakov.techfate;

import static com.shmakov.techfate.ColorsFragment.COLORS_ARRAY_TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.shmakov.techfate.adapters.ImageAdapter;
import com.shmakov.techfate.entities.Product;
import com.shmakov.techfate.mytools.StringWorker;

import java.util.HashMap;

public class ItemCartActivity extends AppCompatActivity {

    public static final String PRODUCT_TAG = "PRODUCT";

    private Product current_product;
    private ViewPager imageSwitcher;
    private TextView category_product_name, item_name, product_cost;
    private TableLayout specifications_container;
    private FrameLayout mini_reviews_container, stars_reviews_container, all_reviews_container, colors_item_container;
    private boolean headphones = false;
    private FragmentManager fragmentManager;

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
        Bundle arr = new Bundle();
        arr.putStringArrayList(COLORS_ARRAY_TAG, current_product.getColors());
        ColorsFragment colorsFragment = new ColorsFragment();
        colorsFragment.setArguments(arr);
        ft.replace(colors_item_container.getId(), colorsFragment).commit();
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
        TableRow name_spec;
        TextView name, val;
        HashMap<String, String> specs = product.getSpecifications();
        String[] specs_names = specs.keySet().toArray(new String[0]);
        TableRow.LayoutParams params_name = new TableRow.LayoutParams(
                300,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f);
        params_name.setMargins(0, 20, 0, 20);
        TableRow.LayoutParams params_specs = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f);
        params_specs.setMargins(0, 20, 0, 20);
        for (int i = 0; i < specs_names.length; i++) {
            name_spec = new TableRow(this);
            name = new TextView(this);
            val = new TextView(this);
            name.setText(specs_names[i] + "\t");
            name.setTextSize(18);
            name.setMaxLines(2);
            name.setLayoutParams(params_name);
            name.setTextColor(Color.BLACK);
            name.setTypeface(null, Typeface.BOLD);
            val.setText(specs.get(specs_names[i]));
            val.setTextColor(Color.BLACK);
            val.setTextSize(18);
            val.setLayoutParams(params_specs);
            val.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            val.setMaxLines(1);
            val.setGravity(Gravity.CENTER|Gravity.BOTTOM);
            name.setGravity(Gravity.START);
            val.setTypeface(null, Typeface.NORMAL);
            name_spec.addView(name);
            name_spec.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            name_spec.addView(val);
            specifications_container.addView(name_spec);
        }
    }
}