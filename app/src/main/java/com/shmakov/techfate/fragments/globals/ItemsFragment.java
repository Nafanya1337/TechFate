package com.shmakov.techfate.fragments.globals;


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
import com.shmakov.techfate.entities.inner.Category;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.mytools.MyComparator;

import java.util.Arrays;


public class ItemsFragment extends Fragment {
    public static final String MAKE_POPULAR_ITEMS = "Popular_items_grid";

    public static final int SORT_0_100 = 0;
    public static final int SORT_100_0 = 1;
    public static final int SORT_BY_WATCHES = 2;


    private GridView gridView;
    private String type;
    private ProductAdapter productAdapter;
    private int sortType = 2;
    private Product[] all;

    public ProductAdapter getProductAdapter() {
        return productAdapter;
    }

    public ItemsFragment() {
        this.type = MAKE_POPULAR_ITEMS;
    }

    public ItemsFragment(String type) {
        this.type = type;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
        makeSort();
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
        if (!type.equals(MAKE_POPULAR_ITEMS))
            makeCurrentItems();
        gridView.setAdapter(productAdapter);
        gridView.setFriction(0.1f);
    }

    public void notifyAboutChanges(){
        all = Category.getPopularProducts().toArray(new Product[0]);
        makeSort();
        productAdapter= new ProductAdapter(getContext(), all);
        gridView.setAdapter(productAdapter);
        gridView.setFriction(0.1f);
    }

    public void makePopularGridView() {
    }

    private void makeCurrentItems(){
        if (Category.getCategoriesNamesAsArrayList().contains(type)) {
            all = Category.categories.get(type).toArray(new Product[0]);
            makeSort();
            productAdapter = new ProductAdapter(getContext(), all);
        }
    }

    public void makeSort() {
        switch (sortType){
            case 0:
                Arrays.sort(all, new MyComparator.CostMinToHigh());
                break;
            case 1:
                Arrays.sort(all, new MyComparator.CostMaxToMin());
                break;
            default:
                Arrays.sort(all, new MyComparator.AmountOfWatches());
                break;
        }
    }

}