package com.shmakov.techfate.fragments.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shmakov.techfate.MainActivity;
import com.shmakov.techfate.OrdersFragment;
import com.shmakov.techfate.ProductCardDialog;
import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.MiniProductInCardAdapter;
import com.shmakov.techfate.entities.Order;
import com.shmakov.techfate.entities.User;

import java.util.ArrayList;


public class AccountFragment extends Fragment {


    FrameLayout orders_container;

    OrdersFragment ordersFragment;

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accountFragment_user_img.setClipToOutline(true);
        orders = user.getOrders();
        ordersFragment = new OrdersFragment(this, orders);
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(orders_container.getId(), ordersFragment).commit();
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