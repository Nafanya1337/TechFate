package com.shmakov.techfate.fragments.account;

import static android.view.Gravity.CENTER;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shmakov.techfate.MainActivity;
import com.shmakov.techfate.OrdersFragment;
import com.shmakov.techfate.ProductCardDialog;
import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.MiniProductInCardAdapter;
import com.shmakov.techfate.entities.Order;
import com.shmakov.techfate.entities.User;
import com.shmakov.techfate.mytools.ColorManager;

import java.util.ArrayList;


public class AccountFragment extends Fragment {


    FrameLayout orders_container;

    OrdersFragment ordersFragment;

    TextView accountFragment_user_name, AccountEmailText;

    ImageView accountFragment_user_img;

    ArrayList<Order> orders;

    User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = MainActivity.user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        accountFragment_user_img = view.findViewById(R.id.accountFragment_user_img);
        orders_container = view.findViewById(R.id.orders_container);
        accountFragment_user_name = view.findViewById(R.id.accountFragment_user_name);
        AccountEmailText = view.findViewById(R.id.AccountEmailText);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accountFragment_user_img.setClipToOutline(true);
        orders = user.getOrders();
        ordersFragment = new OrdersFragment(this, orders);
        if (orders.size() != 0) {
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(orders_container.getId(), ordersFragment).commit();
        }
        else {
            TextView text = new TextView(getContext());
            text.setText("На данный момент у Вас нет активных заказов \uD83D\uDEAB\uD83D\uDCE6");
            text.setGravity(CENTER);
            text.setTextColor(Color.BLACK);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(50,350,50,0);
            text.setLayoutParams(params);
            orders_container.addView(text);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = getActivity().getWindow();
            final int colorFrom = ContextCompat.getColor(getContext(), R.color.white);
            final int colorTo = ContextCompat.getColor(getContext(), R.color.yellow);

            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.setDuration(150); // Длительность анимации в миллисекундах

            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    int animatedValue = (int) animator.getAnimatedValue();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(animatedValue);
                }
            });

            colorAnimation.start();
        }

        accountFragment_user_name.setText(user.getName());
        AccountEmailText.setText(user.getEmailAdress());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = getActivity().getWindow();
            final int colorFrom = ContextCompat.getColor(getContext(), R.color.yellow);
            final int colorTo = ContextCompat.getColor(getContext(), R.color.white);

            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.setDuration(150); // Длительность анимации в миллисекундах

            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    int animatedValue = (int) animator.getAnimatedValue();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(animatedValue);
                }
            });

            colorAnimation.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (user.getOrders().size() != orders.size()) {
            ordersFragment.setOrders(user.getOrders());
            orders = user.getOrders();
        }
    }
}