package com.shmakov.techfate.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.Category;
import com.shmakov.techfate.mytools.ImageManager;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private final ArrayList<String> categories;
    private final LayoutInflater inflater;
    private Context context;

    public interface OpenCategory{
        public void openCategory(View view, String name);
    }

    OpenCategory openCategory;

    public CategoryAdapter(Context context, ArrayList<String> categories) {
        this.categories = categories;
        this.context = context;
        this.openCategory = (OpenCategory) context;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        String category = categories.get(position);
        holder.img.setImageResource(ImageManager.findCategoryMiniIMG(category));
        holder.categoryName.setText(category);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategory.openCategory(v, holder.categoryName.getText().toString());
            }
        });
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        final ImageView img;
        final TextView categoryName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.category_img);
            categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}
