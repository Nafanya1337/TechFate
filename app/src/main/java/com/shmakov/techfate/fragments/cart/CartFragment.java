package com.shmakov.techfate.fragments.cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shmakov.techfate.MainActivity;
import com.shmakov.techfate.PaymentActivity;
import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.CartAdapter;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.mytools.ProgressBarAnimation;

import java.util.ArrayList;


public class CartFragment extends Fragment implements CartAdapter.updateAmount {

    TextView promocode_active, cost_cart;
    RecyclerView cart_list;
    EditText promocodeEditText;

    int sum = 0;

    ArrayList<Product> products = new ArrayList<>();

    RadioButton promocode_check;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cart_list = view.findViewById(R.id.cart_list);
        promocodeEditText = view.findViewById(R.id.promocodeEditText);
        promocode_check = view.findViewById(R.id.promocode_check);
        promocode_active = view.findViewById(R.id.promocode_active);
        cost_cart = view.findViewById(R.id.cost_cart);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        products = MainActivity.products;
        CartAdapter cartAdapter = new CartAdapter(this, products);
        cart_list.setAdapter(cartAdapter);

        promocodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                promocode_check.setChecked(false);
                if (promocode_active.getVisibility() == View.VISIBLE) {
                    removePromocode();
                    promocodeIsActivated = 0f;
                    cost_cart.setText(sum + " ₽");
                    promocode_active.setVisibility(View.INVISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(getContext() ,R.anim.slide_down_promo);
                    promocode_active.setAnimation(animation);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 7 || s.toString().length() == 0) {
                    promocodeEditText.setEnabled(false);
                    if (s.toString().length() != 0) {
                        if (s.toString().equals("MIREA33")) {
                            promocode_active.setText("-33%");
                            promocodeIsActivated = 33f;
                            makeCost(33f);
                            promocode_active.setTextSize(17);
                            promocode_active.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
                            promocode_active.setAnimation(animation);
                            promocode_check.setChecked(true);
                        } else {
                            promocode_active.setText("Неизвестный\nпромокод");
                            promocode_active.setTextSize(14);
                            promocode_active.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
                            promocode_active.setAnimation(animation);
                            promocode_check.setChecked(false);
                        }
                    }
                    promocodeEditText.setEnabled(true);
                }
            }
        });
        makeCost();
    }

    public void removePromocode(){
        if (promocodeIsActivated == 0f) return;
        sum = old_sum;
        cost_cart.setText(sum + " ₽");
    }

    int old_sum = sum;

    public void makeCost(){
        for (int i = 0; i < products.size(); i++) {
            sum+=products.get(i).getCost();
        }
        old_sum = sum;
        cost_cart.setText(sum + " ₽");
    }

    public void makeCost(float percents){
        sum = (int) (sum - sum * percents / 100);
        cost_cart.setText(sum + " ₽");
    }

    float promocodeIsActivated = 0;

    @Override
    public void add(int cost) {
        old_sum += cost;
        if (promocodeIsActivated != 0)
            sum+= (int) (cost - cost * promocodeIsActivated / 100);
        else
            sum+=cost;
        cost_cart.setText(sum + " ₽");
    }

    @Override
    public void remove(int cost) {
        old_sum -= cost;
        if (promocodeIsActivated != 0)
            sum-= (int) (cost - cost * promocodeIsActivated / 100);
        else
            sum-= cost;
        cost_cart.setText(sum + " ₽");
    }
}