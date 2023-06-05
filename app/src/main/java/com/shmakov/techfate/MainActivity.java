package com.shmakov.techfate;



import static com.shmakov.techfate.ItemCartActivity.PRODUCT_TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shmakov.techfate.adapters.CartAdapter;
import com.shmakov.techfate.adapters.CategoryAdapter;
import com.shmakov.techfate.adapters.ProductAdapter;
import com.shmakov.techfate.database.ProductDatabaseHelper;
import com.shmakov.techfate.database.UserDatabaseHelper;
import com.shmakov.techfate.entities.Cart;
import com.shmakov.techfate.entities.Order;
import com.shmakov.techfate.entities.ProductInCart;
import com.shmakov.techfate.entities.User;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.fragments.account.ChangeProfileInfo;
import com.shmakov.techfate.fragments.account.ShowOrdersActivity;
import com.shmakov.techfate.fragments.cart.CartFragment.makePayment;
import com.yandex.mapkit.MapKitFactory;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.openCategory, ProductAdapter.onClickProduct, makePayment, CartAdapter.openItemFromCart {

    private static final int ITEM_ACTIVITY_REQUEST_CODE = 1;
    private static final int CATERGORY_ACTIVITY_REQUEST_CODE = 2;
    private static final int PAYMENT_ACTIVITY_REQUEST_CODE = 3;

    private static boolean mapMade = false;

    BottomNavigationView menu;
    NavController navController;
    FragmentManager fragmentManager;

    ProductDatabaseHelper productDatabaseHelper;

    UserDatabaseHelper userDatabaseHelper;

    public static Cart cart = new Cart();

    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productDatabaseHelper = new ProductDatabaseHelper(this);
        userDatabaseHelper = new UserDatabaseHelper(this);
        try {
            productDatabaseHelper.createDataBase();
            userDatabaseHelper.createDataBase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        createProducts();
        getUserLogin();
        if (!mapMade) {
            MapKitFactory.setApiKey("f425d115-72d7-4ff9-bff7-20f64fbd8769");
            MapKitFactory.initialize(this);
            mapMade = true;
        }
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        menu = findViewById(R.id.nav_menu);
        navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupWithNavController(menu, navController);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }
    }

    private void getUserLogin(){
        userDatabaseHelper.openDataBase();
        user = userDatabaseHelper.getLoggedUser();
        userDatabaseHelper.close();
    }

    private void createProducts(){
        if (!mapMade) {
            productDatabaseHelper.openDataBase();
            productDatabaseHelper.getAllProducts();
            productDatabaseHelper.close();
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

    @Override
    public void openCategory(String category) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra(CategoryActivity.CATEGORY_TAG, category);
        intent.putExtra("requestCode", CATERGORY_ACTIVITY_REQUEST_CODE);
        activityResultLauncher.launch(intent);
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
        product.addWatch();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PRODUCT_TAG, product);
        bundle.putParcelableArrayList("UserCart", user.getCart().getProducts());
        intent.putExtras(bundle);
        intent.putExtra("requestCode", ITEM_ACTIVITY_REQUEST_CODE);
        activityResultLauncher.launch(intent);
    }

    public void onClickProduct(Product product) {
        Intent intent = new Intent(this, ItemCartActivity.class);
        product.addWatch();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PRODUCT_TAG, product);
        bundle.putParcelableArrayList("UserCart", user.getCart().getProducts());
        intent.putExtras(bundle);
        intent.putExtra("requestCode", ITEM_ACTIVITY_REQUEST_CODE);
        activityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    int requestCode = result.getData().getIntExtra("requestCode", 0);
                    if (requestCode == CATERGORY_ACTIVITY_REQUEST_CODE) {
                        if (resultCode == RESULT_OK) {
                            ArrayList<ProductInCart> userCart = result.getData().getParcelableArrayListExtra("UserCart");
                            user.getCart().setProducts(userCart);
                        }
                        else {
                        }
                    } else if (requestCode == PAYMENT_ACTIVITY_REQUEST_CODE) {
                        if (resultCode == RESULT_OK) {
                            Intent data = result.getData();
                            Order order = data.getParcelableExtra("Order");
                            user.addOrder(order);
                            user.getCart().clear();
                        }
                    } else if (requestCode == ITEM_ACTIVITY_REQUEST_CODE) {
                        if (resultCode == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                ArrayList<ProductInCart> userCart = data.getParcelableArrayListExtra("UserCart");
                                user.getCart().setProducts(userCart);
                            }
                        }
                    }
                }
            });


    @Override
    public void makePayment(String PromocodeName, Float PromocodeRate, Cart cart) {
        Intent intent = new Intent(this, PaymentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PromocodeName", PromocodeName);
        bundle.putFloat("PromocodeRate", PromocodeRate);
        bundle.putParcelable("Cart", cart);
        intent.putExtras(bundle);
        intent.putExtra("requestCode", PAYMENT_ACTIVITY_REQUEST_CODE);
        activityResultLauncher.launch(intent);
    }


    public void ButtonOnClickLogout(View view) {
        userDatabaseHelper.openDataBase();
        userDatabaseHelper.logout(user.getId());
        userDatabaseHelper.close();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onClickChangeAccount(View view) {
        Intent intent = new Intent(this, ChangeProfileInfo.class);
        startActivity(intent);
    }

    public void onClickShowOrders(View view) {
        Intent intent = new Intent(this, ShowOrdersActivity.class);
        startActivity(intent);
    }

    @Override
    public void openItemFromCart(ProductInCart product) {
        onClickProduct(product.getProduct());
    }
}