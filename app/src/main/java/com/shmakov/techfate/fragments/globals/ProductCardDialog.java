package com.shmakov.techfate.fragments.globals;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.ProductInCart;
import com.shmakov.techfate.mytools.ColorManager;
import com.shmakov.techfate.mytools.StringWorker;


public class ProductCardDialog extends DialogFragment {

    ProductInCart product;

    TextView product_dialog_cost_text, product_dialog_amount_text, product_dialog_color_text, product_dialog_header_name;

    ImageView product_dialog_img;
    public ProductCardDialog(ProductInCart product) {
        this.product = product;
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_card_dialog, container, false);
        product_dialog_cost_text = view.findViewById(R.id.product_dialog_cost_text);
        product_dialog_amount_text = view.findViewById(R.id.product_dialog_amount_text);
        product_dialog_color_text = view.findViewById(R.id.product_dialog_color_text);
        product_dialog_img = view.findViewById(R.id.product_dialog_img);
        product_dialog_header_name = view.findViewById(R.id.product_dialog_header_name);
        return view;
    }

    public void onResume(){
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(700, WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        product_dialog_cost_text.setText(String.valueOf(product.product.getCost()));
        product_dialog_amount_text.setText(String.valueOf(product.getAmount()));
        product_dialog_img.setImageResource(product.product.getImg());
        product_dialog_color_text.setText(product.getSelected_color());
        String mark = product.product.getMark();
        String name = product.product.getName();
        product_dialog_header_name.setText(StringWorker.makeProductName(mark, name));
    }
}