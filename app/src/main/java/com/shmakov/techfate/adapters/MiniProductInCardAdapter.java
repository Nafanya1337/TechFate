package com.shmakov.techfate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmakov.techfate.OrdersFragment;
import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.ProductInCart;
import com.shmakov.techfate.fragments.account.AccountFragment;
import com.shmakov.techfate.fragments.cart.MakeOrderFragment;

public class MiniProductInCardAdapter extends RecyclerView.Adapter<MiniProductInCardAdapter.ViewProductInCart> {

    ProductInCart[] products;

    showProductInfo showProductInfo;

    public interface showProductInfo{
        public void showProductInfo(int position);
    }

    public MiniProductInCardAdapter(MakeOrderFragment context, ProductInCart[] products) {
        showProductInfo = (showProductInfo) context;
        this.products = products;
    }

    public MiniProductInCardAdapter(OrdersFragment context, ProductInCart[] products) {
        showProductInfo = (showProductInfo) context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewProductInCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mini_product_in_cart, parent, false);
        return new ViewProductInCart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewProductInCart holder, int position) {
        holder.item_mini_product_in_cart_img.setImageResource(products[position].product.getImg());
        holder.item_mini_product_in_cart_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProductInfo.showProductInfo(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    class ViewProductInCart extends RecyclerView.ViewHolder {
        ImageView item_mini_product_in_cart_img;
        public ViewProductInCart(@NonNull View itemView) {
            super(itemView);
            item_mini_product_in_cart_img = itemView.findViewById(R.id.item_mini_product_in_cart_img);
        }
    }
}
