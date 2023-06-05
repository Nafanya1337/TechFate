package com.shmakov.techfate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shmakov.techfate.Helpers.SwipeHelper;
import com.shmakov.techfate.adapters.MiniProductInCardAdapter;
import com.shmakov.techfate.adapters.OrdersAdapter;
import com.shmakov.techfate.entities.Order;
import com.shmakov.techfate.fragments.account.AccountFragment;

import java.util.ArrayList;


public class OrdersFragment extends Fragment implements MiniProductInCardAdapter.showProductInfo, OrdersAdapter.ShowFullOrderInfo {

    ArrayList<Order> orders;

    AccountFragment accountFragment;

    OrdersAdapter ordersAdapter;
    RecyclerView orders_recycler;

    FullOrderInfoFragment fullOrderInfoFragment;

    int current_order = -1;

    public OrdersFragment (AccountFragment accountFragment, ArrayList<Order> orders) {
        this.accountFragment = accountFragment;
        this.orders = orders;
        if (orders.size() > 0)
            current_order = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        orders_recycler = view.findViewById(R.id.orders_recycler);
        return view;
    }

    public void setOrders(ArrayList<Order> orders) {
        if (orders.size() != this.orders.size()) {
            this.orders = orders;
            ordersAdapter.setOrders(orders);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ordersAdapter = new OrdersAdapter(this, orders);
        orders_recycler.setAdapter(ordersAdapter);
        SwipeHelper linearSnapHelper = new SwipeHelper();
        linearSnapHelper.attachToRecyclerView(orders_recycler);
    }

    @Override
    public void showProductInfo(int position) {
        if (current_order >= 0) {
            ProductCardDialog productCardDialog = new ProductCardDialog(orders.get(current_order).getCart().getProducts().get(position));
            productCardDialog.show(getParentFragmentManager(), "ProductInfo");
        }
    }

    @Override
    public void ShowFullOrderInfo(int position) {
        Bundle bundle = new Bundle();
        Order order = orders.get(position);
        bundle.putString("Address", order.getAddress());
        bundle.putString("DeliveryMethod", order.getDeliveryMethod());
        bundle.putString("PaymentMethod", order.getPaymentMethod());
        bundle.putBoolean("IsAlreadyMade", true);
        bundle.putParcelable("Cart", order.getCart());
        bundle.putInt("DeliveryCost", order.getDelivery_cost());
        bundle.putString("PromocodeName", order.getPromocodeName());
        bundle.putFloat("PromocodeRate", order.getPromocodeRate());
        bundle.putString("Status", order.getStatus());
        View view = getView();
        Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_makeOrderFragment2, bundle);

    }
}