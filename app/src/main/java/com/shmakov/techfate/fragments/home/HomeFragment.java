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
import com.shmakov.techfate.entities.inner.Category;
import com.shmakov.techfate.entities.inner.Headphones;
import com.shmakov.techfate.entities.inner.SmartPhone;
import com.shmakov.techfate.fragments.globals.ItemsFragment;
import com.shmakov.techfate.mytools.ColorManager;


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
        Headphones headphones1 = new Headphones("Apple", "AirPods Pro 2", 20000, ColorManager.BLUE_COLOR, R.drawable.apple_airpods_2_pro_img1, imgs, 30, 20, 20000, "Внутриканальные");
        Headphones headphones2 = new Headphones("Apple", "AirPods Pro 2", 20000, ColorManager.GREEN_COLOR, R.drawable.apple_airpods_2_pro_img1, imgs, 30, 20, 20000, "Внутриканальные");
        Headphones headphones3 = new Headphones("Apple", "AirPods Pro 2", 20000, ColorManager.BLACK_COLOR, R.drawable.apple_airpods_2_pro_img1, imgs, 30, 20, 20000, "Внутриканальные");
        Headphones headphones4 = new Headphones("Apple", "AirPods Pro 2", 20000, ColorManager.YELLOW_COLOR, R.drawable.apple_airpods_2_pro_img1, imgs, 30, 20, 20000, "Внутриканальные");
        Headphones headphones5 = new Headphones("Apple", "AirPods Pro 2", 20000, ColorManager.GRAY_COLOR, R.drawable.apple_airpods_2_pro_img1, imgs, 30, 20, 20000, "Внутриканальные");
        Headphones headphones6 = new Headphones("Apple", "AirPods Pro 2", 20000, ColorManager.WHITE_COLOR, R.drawable.apple_airpods_2_pro_img1, imgs, 30, 20, 20000, "Внутриканальные");

    }

}