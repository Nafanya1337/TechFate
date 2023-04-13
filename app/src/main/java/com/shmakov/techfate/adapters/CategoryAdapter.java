package com.shmakov.techfate.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.Category;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private Category[] categories;


    public CategoryAdapter(@NonNull Context context, Category[] categories) {
        this.context = context;
        this.categories = categories;
    }


    // Связываем карточку категории с родителем
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)) {};
        return viewHolder;
    }


    // Заполняем карточку
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView name = holder.itemView.findViewById(R.id.category_name);
        name.setText(categories[position].getCategory());
        ImageView img = holder.itemView.findViewById(R.id.category_img);
        img.setImageResource(categories[position].getImg());

        img.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Удаляем слушателя представлений, чтобы избежать многократных вызовов
                img.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Получаем размеры ImageView после того, как он был измерен и размещен на экране
                int width = img.getWidth();
                int height = img.getHeight();

                if (width > 300)
                    img.setScaleX((float)(width/300));
                if (height > 300)
                    img.setScaleY((float)(height/300));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.categories.length;
    }
}
