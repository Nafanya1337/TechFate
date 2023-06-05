package com.shmakov.techfate;

import static com.shmakov.techfate.FilterFragment.AVAILABLE;
import static com.shmakov.techfate.FilterFragment.COLORS_KEY;
import static com.shmakov.techfate.FilterFragment.MIN_MAX_COST_KEY;
import static com.shmakov.techfate.ItemCartActivity.COLOR_TAG;
import static com.shmakov.techfate.ItemCartActivity.CONFIGURATION_TAG;
import static com.shmakov.techfate.ItemCartActivity.PRODUCT_TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.shmakov.techfate.adapters.ColorPickerAdapter;
import com.shmakov.techfate.adapters.ProductAdapter;
import com.shmakov.techfate.entities.Cart;
import com.shmakov.techfate.entities.ProductInCart;
import com.shmakov.techfate.entities.User;
import com.shmakov.techfate.entities.inner.Category;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.fragments.globals.ItemsFragment;
import com.shmakov.techfate.fragments.home.category.CategoryHeaderFragment;
import com.shmakov.techfate.fragments.home.category.CategoryHeaderFragment.goBack;
import com.shmakov.techfate.mytools.ColorManager;
import com.shmakov.techfate.mytools.FragmentAdapterUpdater;
import com.shmakov.techfate.mytools.ImageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CategoryActivity extends AppCompatActivity implements goBack, ProductAdapter.onClickProduct, CategoryHeaderFragment.openFilters, FilterFragment.makeFilters, ColorPickerAdapter.checkboxColor {

    private FrameLayout container, product_container;
    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    private CategoryHeaderFragment header;
    private ItemsFragment itemsFragment;
    private String tittle;
    private Spinner spinner;
    private TextView category_amount, category_available;
    private FragmentAdapterUpdater fragmentAdapterUpdater;

    private BottomSheetDialogFragment dialog;

    private ArrayList<Product> products;
    private ArrayList<Product> all;

    User user;

    private int min_cost = -1, max_cost = -1;

    public static ArrayList<String> selected_colors = new ArrayList<>();

    public static final String CATEGORY_TAG = "CATEGORY_TAG";
    public static final String CATEGORY_IMG_TAG = "CATEGORY_IMG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        fragmentManager = getSupportFragmentManager();

        user = MainActivity.user;

        tittle = getIntent().getExtras().getString(CATEGORY_TAG);

        this.container = findViewById(R.id.header_container);
        header = new CategoryHeaderFragment();
        header.setCategoryTittle(tittle);
        header.setCategoryBackgroundImage(ImageManager.findCategoryBackgroundIMG(tittle));

        selected_colors.addAll(ColorManager.all_available_colors);
        product_container = findViewById(R.id.category_items_container);
        products = Category.categories.get(tittle);
        itemsFragment = new ItemsFragment(this, products.toArray(new Product[0]));

        String[] array_modes = {
                "По возрастанию",
                "По убыванию",
                "По популярности",
                "По рейтингу"
        };

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                array_modes
        );

        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinner.setAdapter(adapter);

        category_amount = findViewById(R.id.category_amount);
        products = Category.categories.get(tittle);
        int amount = products.size();
        String amounts = getResources().getQuantityString(R.plurals.products, amount, amount);
        category_amount.setText(amounts);

        category_available = findViewById(R.id.category_available);
        String category_available_str = getResources().getQuantityString(R.plurals.avaliable, amount);
        category_available.setText(category_available_str);
        all = Category.categories.get(tittle);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(ContextCompat.getColor(this, R.color.transparent));
//
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ft = fragmentManager.beginTransaction();
        ft.replace(container.getId(), header).commit();
        ft = fragmentManager.beginTransaction();
        ft.replace(product_container.getId(), itemsFragment);
        ft.commit();
        fragmentAdapterUpdater = new FragmentAdapterUpdater(itemsFragment, ft, product_container.getId());
        spinner.setOnItemSelectedListener(fragmentAdapterUpdater);
    }

    @Override
    public void goBack(View view) {
        onBackPressed();
    }

    @Override
    public void onClickProduct(View view, Product product) {
        Intent intent = new Intent(this, ItemCartActivity.class);
        product.addWatch();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PRODUCT_TAG, product);
        bundle.putParcelableArrayList("UserCart", user.getCart().getProducts());
        intent.putExtras(bundle);
        intent.putExtra("requestCode", "");
        activityResultLauncher.launch(intent);
    }

    ArrayList<ProductInCart> products_in_cart = new ArrayList<ProductInCart>();

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            ArrayList<ProductInCart> userCart = data.getParcelableArrayListExtra("UserCart");
                            user.getCart().setProducts(userCart);
                        }
                    }
                }
            });

    @Override
    public void onBackPressed() {
        Intent main_data = new Intent();
        main_data.putExtra("requestCode", getIntent().getIntExtra("requestCode", 0));
        if (products_in_cart.size() > 0) {
            main_data.putParcelableArrayListExtra("UserCart", user.getCart().getProducts());
            setResult(RESULT_OK, main_data);
        }
        else
            setResult(RESULT_CANCELED, main_data);
        super.onBackPressed();
    }

    public static final String PRODUCT_ARRAY_TAG = "ARRAY_OF_PRODUCTS";

    private HashMap<String, ArrayList<String>> filters = new HashMap<>();

    private void createDialog() {
        if (all.size() > 0) {
            FilterFragment ff = new FilterFragment(this, all, filters);
            ff.show(getSupportFragmentManager(), "filters");
        }
    }

    @Override
    public void openFilters(View view) {
        createDialog();
    }

    @Override
    public void makeFilters(int minCost, int maxCost, Integer valueFrom, Integer valueTo) {
        all = Category.categories.get(tittle);
        products = new ArrayList<Product>(Arrays.asList(all.stream().filter(product -> acceptFilters(product, minCost, maxCost)).toArray(Product[]::new)));
        if (!products.isEmpty()) {
            itemsFragment.setAll(products.toArray(new Product[0]));
            itemsFragment.setSortType(spinner.getSelectedItemPosition());
            this.min_cost = minCost;
            this.max_cost = maxCost;
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(new String[]{String.valueOf(minCost), String.valueOf(maxCost), String.valueOf(valueFrom), String.valueOf(valueTo)}));
            filters.put(MIN_MAX_COST_KEY, arrayList);
            filters.put(COLORS_KEY, selected_colors);
            header.setOptionsAreCheckable(minCost != valueFrom | maxCost != valueTo | !selected_colors.containsAll(ColorManager.all_available_colors));
        }
        else {
            header.setOptionsAreCheckable(false);
        }
    }

    private boolean acceptFilters(Product product, int minCost, int maxCost) {
        if ((product.getCost() >= minCost) && (product.getCost() <= maxCost)) {
            if (Arrays.stream(product.getColors()).anyMatch(color -> selected_colors.contains(color))) {
                int position_of_color = -1;
                for (String color : selected_colors) {
                    position_of_color = -1;
                    if (Arrays.asList(product.getColors()).contains(color))
                        position_of_color = Arrays.asList(product.getColors()).indexOf(color);
                    if (product.getConfigurations() != null && position_of_color != -1) {
                        return true;
                    } else if (position_of_color != -1 && product.getAmount()[position_of_color] > 0) {

                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void addColor(String col) {
        if (selected_colors.containsAll(ColorManager.all_available_colors))
            selected_colors.clear();
        selected_colors.add(col);
    }

    @Override
    public void deleteColor(String col) {
        selected_colors.remove(col);
        if (selected_colors.isEmpty())
            selected_colors.addAll(ColorManager.all_available_colors);
    }
}