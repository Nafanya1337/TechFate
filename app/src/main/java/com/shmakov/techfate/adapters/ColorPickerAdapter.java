package com.shmakov.techfate.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.TooltipCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.mytools.ColorManager;

import java.util.ArrayList;

public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.MyViewHolder>{

    public interface checkboxColor{
        public void addColor(String col);
        public void deleteColor(String col);
    }

    private checkboxColor checkboxColor;
    private ArrayList<String> colors;
    private LayoutInflater inflater;

    private ArrayList<String> selected = new ArrayList<>();

    public ColorPickerAdapter(Context context, ArrayList<String> colors) {
        inflater = LayoutInflater.from(context);
        this.colors = colors;
        checkboxColor = (checkboxColor) context;
    }

    public ColorPickerAdapter(Context context, ArrayList<String> colors, ArrayList<String> selected) {
        inflater = LayoutInflater.from(context);
        this.colors = colors;
        checkboxColor = (checkboxColor) context;
        this.selected = selected;
    }

    @NonNull
    @Override
    public ColorPickerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_color_checkbox, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String color = colors.get(position);
        GradientDrawable bgShape = (GradientDrawable) holder.color.getBackground();
        bgShape.setColor(ColorManager.nameColorToInt(color));

        if (selected.size() == 0 || selected.contains(color)) {
            holder.color_checkbox.setChecked(true);
        } else {
            holder.color_checkbox.setChecked(false);
        }

        holder.color_checkbox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TooltipCompat.setTooltipText(holder.color_checkbox, colors.get(position));
                return false;
            }
        });

        holder.color_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkboxColor.addColor(colors.get(position));
                else
                    checkboxColor.deleteColor(colors.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox color_checkbox;
        private final ImageView color;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            color_checkbox = itemView.findViewById(R.id.color_checkbox);
            color = itemView.findViewById(R.id.color_check);
        }
    }
}
