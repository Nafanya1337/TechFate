package com.shmakov.techfate.adapters;
import android.content.Context;
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
import com.shmakov.techfate.entities.Product;
import com.shmakov.techfate.mytools.StringWorker;


public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private Product[] products;
    private TextView name, price;
    private ImageView img;

    public ProductAdapter(@NonNull Context context, Product[] products) {
        super(context, R.layout.item_mini, products);
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mini, parent, false);
        name = convertView.findViewById(R.id.mini_item_name);
        price = convertView.findViewById(R.id.mini_item_price);
        img = convertView.findViewById(R.id.mini_item_img);

        StringBuilder mark_and_name = new StringBuilder(products[position].getMark() + " " + products[position].getName());
        if (mark_and_name.length() >= 32) {
            int i = mark_and_name.lastIndexOf(" ", 25);
            if (i == -1)
                i = 22;
            mark_and_name = mark_and_name.delete(i, mark_and_name.length());
            mark_and_name.append("...");
        }
        name.setText(mark_and_name);
        String price_string = StringWorker.makePriceString(products[position].getCost());
        price.setText(price_string);
        img.setImageResource(products[position].getImg());
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        convertView.setAnimation(animation);
        return convertView;
    }
}