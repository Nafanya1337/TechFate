package com.shmakov.techfate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.fragments.cart.account.ShowOrdersActivity;
import com.shmakov.techfate.entities.Order;
import com.shmakov.techfate.entities.ProductInCart;

import java.util.ArrayList;

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.OrderView> {

    ArrayList<Order> orders;

    ShowOrdersActivity context;


    public interface showStatuses{
        public void showStatuses(int orderId);
    }

    showStatuses showStatuses;

    public AllOrdersAdapter(ArrayList<Order> orders, ShowOrdersActivity activity) {
        this.orders = orders;
        showStatuses = (showStatuses) activity;
        context = activity;
    }

    public void updateOrder(Order order, int position) {
        orders.set(position, order);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_info, parent, false);
        return new OrderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderView holder, int position) {
        Order order = orders.get(position);
        holder.textViewOrderName.setText(order.getName());
        holder.buttonOrderInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatuses.showStatuses(order.getId());
            }
        });

        holder.textViewOrderID.setText(String.valueOf(order.getId()));
        holder.item_order_Status.setText(order.getStatus());
        holder.item_order_DeliveryMethod.setText(order.getDeliveryMethod());
        holder.item_order_Address.setText(order.getAddress());
        MiniProductInCardAdapter adapter = new MiniProductInCardAdapter(context, order.getCart().getProducts().toArray(new ProductInCart[0]));
        holder.item_order_RecyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderView extends RecyclerView.ViewHolder {
        TextView textViewOrderName, textViewOrderID, item_order_Status, item_order_DeliveryMethod, item_order_Address;
        ImageButton buttonOrderInfo;

        RecyclerView item_order_RecyclerView;


        public OrderView(@NonNull View itemView) {
            super(itemView);
            textViewOrderName = itemView.findViewById(R.id.textViewOrderName);
            buttonOrderInfo = itemView.findViewById(R.id.buttonOrderInfo);
            textViewOrderID = itemView.findViewById(R.id.textViewOrderID);
            item_order_Status = itemView.findViewById(R.id.item_order_Status);
            item_order_DeliveryMethod = itemView.findViewById(R.id.item_order_DeliveryMethod);
            item_order_Address = itemView.findViewById(R.id.item_order_Address);
            item_order_RecyclerView = itemView.findViewById(R.id.item_order_RecyclerView);
        }
    }
}
