package com.shmakov.techfate.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.mytools.ColorManager;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {

    private int picked = -1;
    private ArrayList<String> colors;

    private LayoutInflater inflater;

    private int[] amount;

    public ColorAdapter(Context context, ArrayList<String> colors, int[] amount) {
        this.colors = colors;
        inflater = LayoutInflater.from(context);
        this.amount = amount;
    }

    public void updateAvailable(int[] available) {
        amount = available;
        picked = -1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_color, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GradientDrawable bgShape = (GradientDrawable)holder.color.getBackground();
        bgShape.setColor(ColorManager.nameColorToInt(colors.get(position)));
        if (amount[position] <= 0) {
            if (holder.color_img.isChecked())
                holder.color_img.setChecked(false);
            holder.color_img.setChecked(false);
            holder.color_img.setClickable(false);
            holder.color_img.setAlpha(0.3f);
        }
        else {
            if (picked == -1) {
                picked = position;
            }
            holder.color_img.setAlpha(1.0f);
            //holder.color_img.setBackgroundColor(ColorManager.nameColorToInt(colors.get(position)));
            holder.color_img.setClickable(true);
            holder.color_img.setChecked(position == picked);
        }
        holder.color_img.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    notifyItemChanged(picked);
                    notifyItemChanged(position);
                    picked = position;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final RadioButton color_img;
        private final ImageView color;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            color_img = itemView.findViewById(R.id.color_img);
            color = itemView.findViewById(R.id.color);
        }
    }
}
