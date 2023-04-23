package com.shmakov.techfate.adapters;

import static android.os.Looper.myLooper;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.recyclerview.widget.RecyclerView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.mytools.ColorManager;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {

    public interface pickAColor{
        public void pickAColor(int position);
    }

    private int picked = -1;
    private ArrayList<String> colors;
    private Context context;
    private LayoutInflater inflater;

    private int[] amount;

    private pickAColor pickAColor;

    public ColorAdapter(Context context, ArrayList<String> colors, int[] amount) {
        this.colors = colors;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.amount = amount;
        pickAColor = (ColorAdapter.pickAColor) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_color, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        holder.color_radiobutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (picked != holder.position) {
                        picked = holder.position;
                        notifyDataSetChanged();
                        pickAColor.pickAColor(holder.position);
                    }
                }
            }
        });

        return holder;
    }

    public void updateAvailable(int[] available, int picked){
        this.amount = available;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GradientDrawable bgShape = (GradientDrawable)holder.color.getBackground();
        bgShape.setColor(ColorManager.nameColorToInt(colors.get(position)));
        holder.position = position;
        if (this.amount[position]<=0) {
            if (holder.color_radiobutton.isChecked()){
                Animation fadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
                holder.color_radiobutton.startAnimation(fadeOut);
            }
            holder.color_radiobutton.setClickable(false);
            holder.color_radiobutton.setAlpha(0.2f);
            holder.color.setAlpha(0.05f);
        }
        else {
            holder.color_radiobutton.setClickable(true);
            holder.color_radiobutton.setAlpha(1f);
            holder.color.setAlpha(1f);
            if (picked == -1) {
                picked = position;
                pickAColor.pickAColor(holder.position);
            }
        }
        holder.color_radiobutton.setChecked(picked != -1 && amount[picked] > 0 && position == picked);
    }

    public void setPicked(int picked) {
        this.picked = picked;
    }

    public int getPicked() {
        return picked;
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final RadioButton color_radiobutton;
        private final ImageView color;
        private int position;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            color_radiobutton = itemView.findViewById(R.id.color_img);
            color = itemView.findViewById(R.id.color);
        }
    }
}
