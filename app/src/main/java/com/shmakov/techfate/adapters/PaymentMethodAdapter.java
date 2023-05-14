package com.shmakov.techfate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.mytools.ImageManager;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.MyViewHolder> {

    String[] methods;
    Context context;

    int selected = -1;

    public PaymentMethodAdapter(Context context, String[] methods) {
        this.context = context;
        this.methods = methods;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payment_method, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String method = methods[position];
        holder.text.setText(method);
        holder.img.setImageResource(ImageManager.findPaymentIMG(method));
        if (selected == -1)
            selected = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btn.setChecked(true);
            }
        });
        holder.btn.setChecked(selected == position);
        holder.btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notifyItemChanged(selected);
                    selected = position;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return methods.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView img;
        RadioButton btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.item_payment_method_text);
            img = itemView.findViewById(R.id.item_payment_method_img);
            btn = itemView.findViewById(R.id.payment_method_checkbox);
        }
    }

    public int getSelected() {
        return selected;
    }
}
