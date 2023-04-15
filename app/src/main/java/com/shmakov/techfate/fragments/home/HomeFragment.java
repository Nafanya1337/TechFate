package com.shmakov.techfate.fragments.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;
import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.CategoryAdapter;
import com.shmakov.techfate.entities.Category;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView listView = (RecyclerView) getView().findViewById(R.id.categories_list);
        GridView gridView = (GridView) getView().findViewById(R.id.popular_products_grid);


        Category[] categories = {
               new Category("Смартфоны", R.drawable.smartphones_img),
                new Category("Часы", R.drawable.watches_img),
                new Category("Планшеты", R.drawable.tablets_img),
                new Category("Наушники", R.drawable.headphones_img),
                new Category("Игровые приставки", R.drawable.consoles_img),
                new Category("Ноутбуки", R.drawable.laptop_img),
                new Category("Мониторы", R.drawable.monitors_img)
        };

//        Product[] products = {
//                new Product("Смартфоны", "Apple", "iPhone 14 Pro Max", 100000, "white", R.drawable.smartphones_img),
//                new Product("Смартфоны", "Apple", "iPhone 13 Pro Max", 80000, "white", R.drawable.smartphones_img),
//                new Product("Смартфоны", "Apple", "iPhone 14 Pro Max", 100000, "white", R.drawable.smartphones_img),
//                new Product("Смартфоны", "Apple", "iPhone 13 Pro Max", 80000, "white", R.drawable.smartphones_img),
//                new Product("Смартфоны", "Apple", "iPhone 14 Pro Max", 100000, "white", R.drawable.smartphones_img),
//                new Product("Смартфоны", "Apple", "iPhone 13 Pro Max", 80000, "white", R.drawable.smartphones_img),
//                new Product("Смартфоны", "Apple", "iPhone 14 Pro Max", 100000, "white", R.drawable.smartphones_img),
//                new Product("Смартфоны", "Apple", "iPhone 13 Pro Max", 80000, "white", R.drawable.smartphones_img),
//                new Product("Смартфоны", "Apple", "iPhone 14 Pro Max", 100000, "white", R.drawable.smartphones_img),
//                new Product("Смартфоны", "Apple", "iPhone 13 Pro Max", 80000, "white", R.drawable.smartphones_img)
//        };

        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categories);
        LinearLayoutManager categories_layout = new LinearLayoutManager (getContext());
        categories_layout.setOrientation(RecyclerView.HORIZONTAL);
        listView.setLayoutManager(categories_layout);
        listView.setAdapter(categoryAdapter);

        //ProductAdapter productAdapter = new ProductAdapter(getContext(), products);
        //gridView.setAdapter(productAdapter);
        //gridView.setFriction(0.1f);
    }

    private void chooseACategory(int position){
        switch (position) {
            case 0:
                Toast.makeText(getContext(), "Вы выбрали смартфон", Toast.LENGTH_LONG);
                break;
            case 1:
                Toast.makeText(getContext(), "Вы выбрали телевизоры", Toast.LENGTH_LONG);
                break;
        }
    }
}