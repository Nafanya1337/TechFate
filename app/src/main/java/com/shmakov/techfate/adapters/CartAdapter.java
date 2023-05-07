package com.shmakov.techfate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.mytools.StringWorker;

import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {


    ArrayList<Product> productsInCart;
    Context context;

    public interface updateAmount{
        public void add(int cost);
        public void remove(int cost);
    }

    updateAmount updateAmount;
    public CartAdapter(Context context, ArrayList<Product> productsInCart) {
        this.context = context;
        this.productsInCart = productsInCart;
        updateAmount = (updateAmount) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder view = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_product_layout, parent, false));
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_product_cart.setText(StringWorker.makeProductName(productsInCart.get(position).getMark(), productsInCart.get(position).getName()));
        holder.picture_product_cart.setImageResource(productsInCart.get(position).getImg());
        holder.price_product_cart.setText(String.valueOf(productsInCart.get(position).getCost()) + " ₽");
        holder.add_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.amount_product.setText((Integer.valueOf(holder.amount_product.getText().toString()) + 1) + "");
            }
        });
        holder.remove_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.amount_product.setText((Integer.valueOf(holder.amount_product.getText().toString()) - 1) + "");
                if (holder.amount_product.getText().equals("0")) {
                    productsInCart.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsInCart.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_product_cart, price_product_cart, amount_product;
        ImageView picture_product_cart, color_product_cart;

        ImageButton add_amount, remove_amount;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_product_cart = itemView.findViewById(R.id.name_product_cart);
            price_product_cart = itemView.findViewById(R.id.price_product_cart);
            picture_product_cart = itemView.findViewById(R.id.picture_product_cart);
            color_product_cart = itemView.findViewById(R.id.color_product_cart);
            amount_product = itemView.findViewById(R.id.amount_product);
            add_amount = itemView.findViewById(R.id.add_amount);
            remove_amount = itemView.findViewById(R.id.remove_amount);
        }
    }

}
