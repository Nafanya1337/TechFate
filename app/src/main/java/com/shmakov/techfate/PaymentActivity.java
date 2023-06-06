package com.shmakov.techfate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.shmakov.techfate.database.UserDatabaseHelper;
import com.shmakov.techfate.entities.Cart;
import com.shmakov.techfate.entities.Order;
import com.shmakov.techfate.entities.User;
import com.shmakov.techfate.fragments.paymentactivity_fragments.OrderInfoFragment;

import java.io.IOException;

public class PaymentActivity extends AppCompatActivity implements OrderInfoFragment.exitPayment {

    public static String PromocodeName = "";
    public static Float PromocodeRate;
    public static Cart cart;

    public static User user;
    UserDatabaseHelper userDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        FragmentManager manager = getSupportFragmentManager();
        if (getIntent().getExtras() != null) {
            PromocodeName = getIntent().getExtras().getString("PromocodeName");
            PromocodeRate = getIntent().getExtras().getFloat("PromocodeRate");
            cart = getIntent().getExtras().getParcelable("Cart");
        }
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
        user = MainActivity.user;
    }

    @Override
    public void exitPayment(Order order) {
        Intent main_data = new Intent();
        main_data.putExtra("requestCode", getIntent().getIntExtra("requestCode", 0));

        userDatabaseHelper.openDataBase();
        userDatabaseHelper.saveOrder(order, user.getId());
        userDatabaseHelper.close();

        Bundle bundle = new Bundle();
        bundle.putParcelable("Order", order);
        main_data.putExtras(bundle);
        setResult(RESULT_OK, main_data);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent main_data = new Intent();
        main_data.putExtra("requestCode", getIntent().getIntExtra("requestCode", 0));
        setResult(RESULT_CANCELED, main_data);
        super.onBackPressed();
    }
}