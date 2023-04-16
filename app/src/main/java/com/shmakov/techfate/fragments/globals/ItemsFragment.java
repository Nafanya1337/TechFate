package com.shmakov.techfate.fragments.globals;

import static com.shmakov.techfate.entities.SmartPhone.mass;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.ProductAdapter;
import com.shmakov.techfate.entities.Product;
import com.shmakov.techfate.entities.SmartPhone;

import java.util.Arrays;


public class ItemsFragment extends Fragment {
    public static final String MAKE_POPULAR_ITEMS = "Popular_items_grid";
    public static final String MAKE_CURRENT_ITEMS = "Category_items_grid";


    private GridView gridView;
    private String type;
    private ProductAdapter productAdapter;

    public ItemsFragment(String type) {
        this.type = type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.products_grid);
        if (type.equals(MAKE_POPULAR_ITEMS))
            makePopularGridView();
        else
            makeCurrentItems();
        gridView.setAdapter(productAdapter);
        gridView.setFriction(0.1f);
    }

    public void makePopularGridView() {
        SmartPhone.addSmartPhones(
                Arrays.asList
                (
                        new SmartPhone("Huawei", "P 30 Pro", 19000),
                        new SmartPhone("Apple", "iPhone 13 Pro Max", 90000),
                        new SmartPhone("Xiaomi", "13 Lite Top Ultra Momo dlv,ofepv,mewslves", 90000)
                )
        );
        Product[] all = mass.toArray(new SmartPhone[0]);
        productAdapter = new ProductAdapter(getContext(), all);
    }

    private void makeCurrentItems(){}
}