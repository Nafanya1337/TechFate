package com.shmakov.techfate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.shmakov.techfate.fragments.globals.ProductCardDialog;
import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.AllOrdersAdapter;
import com.shmakov.techfate.adapters.MiniProductInCardAdapter;
import com.shmakov.techfate.database.UserDatabaseHelper;
import com.shmakov.techfate.entities.Order;
import com.shmakov.techfate.entities.ProductInCart;
import com.shmakov.techfate.fragments.main_activity_fragments.account.setOrderStatusFragment;

import java.io.IOException;
import java.util.ArrayList;

public class ShowOrdersActivity extends AppCompatActivity implements AllOrdersAdapter.showStatuses, MiniProductInCardAdapter.showProduct {

    UserDatabaseHelper userDatabaseHelper;

    ArrayList<Order> orders;

    RecyclerView allOrdersRecyclerView;

    AllOrdersAdapter allOrdersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_orders);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }
        userDatabaseHelper = new UserDatabaseHelper(this);
        try {
            userDatabaseHelper.createDataBase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userDatabaseHelper.openDataBase();
        orders = userDatabaseHelper.getAllOrders();
        userDatabaseHelper.close();
        allOrdersRecyclerView = findViewById(R.id.allOrdersRecyclerView);
        allOrdersAdapter = new AllOrdersAdapter(orders, this);
        allOrdersRecyclerView.setAdapter(allOrdersAdapter);
    }



    public void closeOrders(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void showStatuses(int orderId) {
        setOrderStatusFragment fragment = new setOrderStatusFragment(orders.get(orderId - 1).getStatus());
        fragment.show(getSupportFragmentManager(), "bottomSheetFragment");

        getSupportFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (requestKey.equals("requestKey")) {
                    if (result.containsKey("Status")) {
                        String status = result.getString("Status");
                        Toast.makeText(getApplicationContext(), "Статус заказа " + orders.get(orderId - 1).getName() + " : " + status, Toast.LENGTH_SHORT).show();
                        orders.get(orderId - 1).setStatus(status);
                        allOrdersAdapter.updateOrder(orders.get(orderId - 1), orderId - 1);
                        userDatabaseHelper.openDataBase();
                        userDatabaseHelper.updateOrderInfo(orders.get(orderId - 1), status);
                        userDatabaseHelper.close();
                    }
                }
            }
        });
    }

    @Override
    public void showProduct(ProductInCart product) {
        ProductCardDialog productCardDialog = new ProductCardDialog(product);
        productCardDialog.show(getSupportFragmentManager(), "ProductInfo");
    }
}