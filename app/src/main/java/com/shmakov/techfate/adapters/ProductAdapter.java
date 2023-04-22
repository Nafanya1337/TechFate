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
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.mytools.StringWorker;


public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private Product[] products = new Product[0];
    private TextView name, price, stars;
    private ImageView img, fire;

    public void setProducts(Product[] products) {
        this.products = products;
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
        this.products = products;
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

        stars.setText(String.valueOf(products[position].getAvgReviewsRating()));

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

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProduct.onClickProduct(v, products[position]);
            }
        });

        if (products[position].getAmountOfWatches() > 1000) {
            fire = convertView.findViewById(R.id.fire);
            fire.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}