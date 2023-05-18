package com.shmakov.techfate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmakov.techfate.OrdersFragment;
import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.Order;
import com.shmakov.techfate.entities.ProductInCart;
import com.shmakov.techfate.fragments.account.AccountFragment;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderView> {


    ArrayList<Order> orders;
    OrdersFragment ordersFragment;

    public OrdersAdapter (OrdersFragment ordersFragment, ArrayList<Order> orders) {
        this.ordersFragment = ordersFragment;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderView holder, int position) {
        Order order = orders.get(position);
        holder.order_name.setText(order.getName());
        holder.miniProductInCardAdapter = new MiniProductInCardAdapter(ordersFragment, order.getCart().getProducts().toArray(new ProductInCart[0]));
        holder.products_in_order_recycler.setAdapter(holder.miniProductInCardAdapter);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderView extends RecyclerView.ViewHolder{

        TextView order_name;
        RecyclerView products_in_order_recycler;

        Button show_more_info_order_btn;

        MiniProductInCardAdapter miniProductInCardAdapter;
        public OrderView(@NonNull View itemView) {
            super(itemView);
            order_name = itemView.findViewById(R.id.order_name);
            products_in_order_recycler = itemView.findViewById(R.id.products_in_order_recycler);
            show_more_info_order_btn = itemView.findViewById(R.id.show_more_info_order_btn);
            show_more_info_order_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();

    }
}
