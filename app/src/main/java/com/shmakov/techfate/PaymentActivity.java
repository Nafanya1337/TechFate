package com.shmakov.techfate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shmakov.techfate.entities.Cart;
import com.shmakov.techfate.entities.Order;
import com.shmakov.techfate.fragments.cart.OrderInfoFragment;
import com.yandex.mapkit.MapKitFactory;

public class PaymentActivity extends AppCompatActivity implements OrderInfoFragment.exitPayment {

    public static String PromocodeName = "";
    public static Float PromocodeRate;
    public static Cart cart;

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
    }

    @Override
    public void exitPayment(Order order) {
        Intent main_data = new Intent();
        main_data.putExtra("requestCode", getIntent().getIntExtra("requestCode", 0));
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