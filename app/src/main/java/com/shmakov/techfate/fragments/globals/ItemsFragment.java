package com.shmakov.techfate.fragments.globals;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.shmakov.techfate.MainActivity;
import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.ProductAdapter;
import com.shmakov.techfate.entities.inner.Category;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.mytools.MyComparator;

import java.util.Arrays;
import java.util.function.IntFunction;


public class ItemsFragment extends Fragment {
    public static final String MAKE_POPULAR_ITEMS = "Popular_items_grid";

    public static final int SORT_0_100 = 0;

    public static final int SORT_100_0 = 1;

    public static final int SORT_BY_WATCHES = 2;
    private final Context context;

    private GridView gridView;
    private ProductAdapter productAdapter;
    private int sortType = 2;
    private Product[] all;
    public Product[] getAll() {
        return all;
    }

    public ItemsFragment(Context context, Product[] all) {
        this.context = context;
        Log.d("mymy", "Context = " + context);
        this.all = all;
        this.productAdapter = new ProductAdapter(this.context, all);
    }


    public void setSortType(int sortType) {
        this.sortType = sortType;
        makeSort();
        productAdapter.setProducts(all);
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_items, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.products_grid);
        gridView.setAdapter(productAdapter);
        gridView.setFriction(0.1f);
    }


    public void setAll(Product[] all) {
        this.all = all;
        productAdapter.setProducts(all);
    }

    public void makeSort() {
        switch (sortType){
            case 0:
                Arrays.sort(all, new MyComparator.CostMinToHigh());
                break;
            case 1:
               Arrays.sort(all, new MyComparator.CostMaxToMin());
                break;
            case 3:
                Arrays.sort(all, new MyComparator.SortByRating());
                break;
            default:
                Arrays.sort(all, new MyComparator.AmountOfWatches());
                break;
        }
    }

}