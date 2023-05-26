package com.shmakov.techfate.fragments.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shmakov.techfate.MainActivity;
import com.shmakov.techfate.PaymentActivity;
import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.CartAdapter;
import com.shmakov.techfate.entities.Cart;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.mytools.ProgressBarAnimation;

import java.util.ArrayList;


public class CartFragment extends Fragment implements CartAdapter.updateAmount {

    TextView promocode_active, cost_cart;
    RecyclerView cart_list;
    EditText promocodeEditText;

    Button button;

    int sum = 0;

    boolean onResuming = false;

    Cart cart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cart = MainActivity.user.getCart();
    }

    public interface makePayment{
        public void makePayment(String PromocodeName, Float PromocodeRate, Cart cart);
    }

    makePayment makePayment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        makePayment = (makePayment) context;
    }

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
        button = view.findViewById(R.id.button);
        promocodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                promocode_check.setChecked(false);
                if (promocode_active.getVisibility() == View.VISIBLE) {
                    cart.setRate(1.0f);
                    cost_cart.setText(cart.getTotal_cost() + " ₽");
                    promocode_active.setVisibility(View.INVISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down_promo);
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
                            cart.setRate(1.0f - 0.33f);
                            cost_cart.setText(cart.getTotal_cost() + " ₽");
                            promocode_active.setTextSize(17);
                            promocode_active.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
                            promocode_active.setAnimation(animation);
                            promocode_check.setChecked(true);
                        } else {
                            cart.setRate(1.0f);
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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        CartAdapter cartAdapter = new CartAdapter(this, cart.getProducts());
        button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (cart.getProducts().size() == 0) {
                    Toast.makeText(getContext(), "Корзина пуста", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (promocode_active.getVisibility() == View.VISIBLE && promocode_active.getText().toString().charAt(0) == '-') {
                    String promo_name = promocodeEditText.getText().toString();
                    String promo_val_str = promocode_active.getText().toString().replace("-","").replace("%", "");
                    Float promo_value = Float.valueOf(promo_val_str);
                    makePayment.makePayment(promo_name, promo_value, cart);
                }
                else
                    makePayment.makePayment("", new Float(0), cart);
            }
        });

        cart_list.setAdapter(cartAdapter);
        makeCost();
        super.onResume();
    }

    public void makeCost(){
        cost_cart.setText(cart.getTotal_cost() + " ₽");
    }

    float promocodeIsActivated = 0;

    @Override
    public void add(int position) {
        cart.addAmount(position);
        cost_cart.setText(cart.getTotal_cost() + " ₽");
    }

    @Override
    public void remove(int position) {
        cart.subAmount(position);
        cost_cart.setText(cart.getTotal_cost() + " ₽");
    }
}