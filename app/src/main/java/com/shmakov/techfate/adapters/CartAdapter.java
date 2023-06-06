package com.shmakov.techfate.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.ProductInCart;
import com.shmakov.techfate.fragments.main_activity_fragments.cart.CartFragment;
import com.shmakov.techfate.mytools.ColorManager;
import com.shmakov.techfate.mytools.StringWorker;

import java.util.ArrayList;
import java.util.Objects;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    public interface openItemFromCart {
        public void openItemFromCart(ProductInCart product);
    }

    openItemFromCart openItemFromCart;
    ArrayList<ProductInCart> productsInCart;
    Context context;

    public interface updateAmount{
        public void add(int position);
        public void remove(int position);
    }

    updateAmount updateAmount;
    public CartAdapter(CartFragment fragment, ArrayList<ProductInCart> productsInCart) {
        this.context = fragment.getContext();
        this.productsInCart = productsInCart;
        updateAmount = (updateAmount) fragment;
        openItemFromCart = (openItemFromCart) context;
    }

    public void setProductsInCart(ArrayList<ProductInCart> productsInCart) {
        this.productsInCart = productsInCart;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder view = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_product_layout, parent, false));
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_product_cart.setText(StringWorker.makeProductName(productsInCart.get(position).product.getMark(), productsInCart.get(position).product.getName()));
        holder.picture_product_cart.setImageResource(productsInCart.get(position).product.getImg());
        holder.price_product_cart.setText(String.valueOf(productsInCart.get(position).product.getCost()) + " ₽");

        holder.amount_product.setText(String.valueOf(productsInCart.get(position).getAmount()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openItemFromCart.openItemFromCart(productsInCart.get(position));
            }
        });
        holder.add_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.amount_product.setText((Integer.valueOf(holder.amount_product.getText().toString()) + 1) + "");
                updateAmount.add(position);
            }
        });
        holder.remove_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.amount_product.setText((Integer.valueOf(holder.amount_product.getText().toString()) - 1) + "");
                updateAmount.remove(position); // Добавить эту строку
                if (holder.amount_product.getText().equals("0")) {
                    productsInCart.remove(position);
                    notifyDataSetChanged();
                }
            }
        });

        if (Objects.equals(productsInCart.get(position).getSelected_configuration(), ""))
            holder.cartProduct_configurationTextView.setVisibility(View.INVISIBLE);
        else
            holder.cartProduct_configurationTextView.setText(productsInCart.get(position).getSelected_configuration());

        GradientDrawable bgShape = (GradientDrawable)holder.color_product_cart.getBackground();
        bgShape.setColor(ColorManager.nameColorToInt(productsInCart.get(position).getSelected_color()));

    }

    @Override
    public int getItemCount() {
        return productsInCart.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_product_cart, price_product_cart, amount_product, cartProduct_configurationTextView;
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
            cartProduct_configurationTextView = itemView.findViewById(R.id.cartProduct_configurationTextView);
        }
    }

}
