package com.shmakov.techfate;

import static com.shmakov.techfate.ItemCartActivity.PRODUCT_TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.shmakov.techfate.adapters.ColorPickerAdapter;
import com.shmakov.techfate.adapters.ProductAdapter;
import com.shmakov.techfate.entities.ProductInCart;
import com.shmakov.techfate.entities.User;
import com.shmakov.techfate.entities.inner.Category;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.fragments.globals.ItemsFragment;
import com.shmakov.techfate.fragments.main_activity_fragments.home.category.CategoryHeaderFragment;
import com.shmakov.techfate.fragments.main_activity_fragments.home.category.CategoryHeaderFragment.goBack;
import com.shmakov.techfate.mytools.ColorManager;
import com.shmakov.techfate.mytools.FragmentAdapterUpdater;
import com.shmakov.techfate.mytools.ImageManager;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryActivity extends AppCompatActivity implements goBack, ProductAdapter.onClickProduct, ColorPickerAdapter.checkboxColor {

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