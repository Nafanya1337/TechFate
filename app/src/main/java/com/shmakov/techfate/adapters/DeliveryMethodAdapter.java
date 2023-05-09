package com.shmakov.techfate.adapters;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.mytools.ImageManager;

import java.util.ArrayList;

public class DeliveryMethodAdapter extends RecyclerView.Adapter<DeliveryMethodAdapter.MyViewHolder>{

    Context context;
    String[] deliveryMethods;

    int chosen_position = -1;

    public int getChosen_position() {
        return chosen_position;
    }

    public DeliveryMethodAdapter(Context context, String[] deliveryMethods) {
        this.context = context;
        this.deliveryMethods = deliveryMethods;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_delivery_method, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String method = deliveryMethods[position];
        String[] parts = method.split("\\) ");
        holder.item_delivery_text.setText(parts[0] + ')');
        holder.item_delivery_cost.setText(parts[1]);
        holder.item_delivery_img.setImageResource(ImageManager.findDeliveryIMG(parts[0]));
        if (chosen_position == -1)
            chosen_position = position;

        holder.item_delivery_radioButton.setChecked(chosen_position == position);

        holder.item_delivery_radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notifyItemChanged(chosen_position);
                    chosen_position = position;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryMethods.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_delivery_text, item_delivery_cost;

        ImageView item_delivery_img;

        RadioButton item_delivery_radioButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_delivery_text = itemView.findViewById(R.id.item_delivery_text);
            item_delivery_cost = itemView.findViewById(R.id.item_delivery_cost);
            item_delivery_img = itemView.findViewById(R.id.item_delivery_img);
            item_delivery_radioButton = itemView.findViewById(R.id.item_delivery_radioButton);
        }
    }
}
