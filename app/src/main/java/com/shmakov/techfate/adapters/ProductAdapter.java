package com.shmakov.techfate.adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.mytools.StringWorker;

import java.util.ArrayList;
import java.util.Arrays;


public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private ArrayList<Product> products = new ArrayList<>();
    private TextView name, price, stars;
    private ImageView img, fire;

    public void setProducts(Product[] products) {
        this.products.clear();
        this.products.addAll(Arrays.asList(products));
        notifyDataSetChanged();
    }

    public interface onClickProduct {
        public void onClickProduct(View view, Product product);
    }

    private onClickProduct onClickProduct;

    public ProductAdapter(@NonNull Context context) {
        super(context, R.layout.item_mini);
        this.context = context;
        onClickProduct = (ProductAdapter.onClickProduct) context;
    }

    public ProductAdapter(@NonNull Context context, Product[] products) {
        super(context, R.layout.item_mini, products);
        this.context = context;
        this.products.addAll(Arrays.asList(products));
        onClickProduct = (ProductAdapter.onClickProduct) context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mini, parent, false);
        name = convertView.findViewById(R.id.mini_item_name);
        price = convertView.findViewById(R.id.mini_item_price);
        img = convertView.findViewById(R.id.mini_item_img);
        stars = convertView.findViewById(R.id.mini_star);

        stars.setText(String.valueOf(products.get(position).getAvgReviewsRating()));

        StringBuilder mark_and_name = new StringBuilder(products.get(position).getMark() + " " + products.get(position).getName());
        if (mark_and_name.length() >= 32) {
            int i = mark_and_name.lastIndexOf(" ", 25);
            if (i == -1)
                i = 22;
            mark_and_name = mark_and_name.delete(i, mark_and_name.length());
            mark_and_name.append("...");
        }
        name.setText(mark_and_name);
        String price_string = StringWorker.makePriceString(products.get(position).getCost());
        price.setText(price_string);
        img.setImageResource(products.get(position).getImg());
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        convertView.setAnimation(animation);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProduct.onClickProduct(v, products.get(position));
            }
        });

        if (products.get(position).getAmountOfWatches() > 1000) {
            fire = convertView.findViewById(R.id.fire);
            fire.setVisibility(View.VISIBLE);
        }
        else {
            fire = convertView.findViewById(R.id.fire);
            fire.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return products.size();
    }
}