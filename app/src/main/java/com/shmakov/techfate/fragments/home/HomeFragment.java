package com.shmakov.techfate.fragments.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.CategoryAdapter;
import com.shmakov.techfate.entities.Category;
import com.shmakov.techfate.entities.Headphones;
import com.shmakov.techfate.entities.SmartPhone;
import com.shmakov.techfate.entities.Watches;
import com.shmakov.techfate.fragments.globals.ItemsFragment;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private CategoryAdapter categoryAdapter;
    private FrameLayout popular_items_container;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getParentFragmentManager();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView categories_list = view.findViewById(R.id.categories_list);

        Category.init();

        categoryAdapter = new CategoryAdapter(getContext(), Category.getCategoriesNamesAsArrayList());

        categories_list.setAdapter(categoryAdapter);

        popular_items_container = view.findViewById(R.id.popular_items_container);

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ItemsFragment itemsFragment = new ItemsFragment(ItemsFragment.MAKE_POPULAR_ITEMS);
        ft.replace(popular_items_container.getId(), itemsFragment);
        ft.commit();
        makeProducts();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentManager = null;
        categoryAdapter = null;
        popular_items_container = null;
    }

    public void makeProducts() {
        int[] imgs = {
            R.drawable.apple_airpods_2_pro_img1,
            R.drawable.apple_airpods_2_pro_img2,
            R.drawable.apple_airpods_2_pro_img3
        };
        new Headphones("Apple", "AirPods 2 Pro", 15000, "white", R.drawable.headphones_2, true, imgs);
        new Headphones("Skullcandy", "Hesh 3", 10000, "blue", R.drawable.headphones_1, true);
        new Headphones("Skullcandy", "Hesh 2", 8000, "blue", R.drawable.headphones_1, false);
        new Headphones("Huawei", "P9", 5000, "white", R.drawable.headphones_3, false);
        new SmartPhone("Apple", "iPhone 13 Pro Max", 60000, "blue", R.drawable.smartphones_img);
        new Watches("Samsung", "Gear S3", 25000, "black", R.drawable.watches_img);
    }

}