package com.shmakov.techfate.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.mytools.ColorManager;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {

    private ArrayList<String> colors;

    private LayoutInflater inflater;


    public ColorAdapter(Context context, ArrayList<String> colors) {
        this.colors = colors;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_color, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GradientDrawable bgShape = (GradientDrawable)holder.color_img.getBackground();
        bgShape.setColor(ColorManager.nameColorToInt(colors.get(position)));
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView color_img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            color_img = itemView.findViewById(R.id.color_img);
            FrameLayout.LayoutParams par = new FrameLayout.LayoutParams(100, 100);
            par.setMargins(0,0,15,0);
            color_img.setLayoutParams(par);
        }
    }
}
