package com.shmakov.techfate.mytools;

import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentTransaction;

import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.fragments.globals.ItemsFragment;

public class FragmentAdapterUpdater implements AdapterView.OnItemSelectedListener{

    private ItemsFragment fragment;
    private FragmentTransaction ft;
    private int container;

    public FragmentAdapterUpdater(ItemsFragment fragment, FragmentTransaction ft, int container) {
        this.fragment = fragment;
        this.ft = ft;
        this.container = container;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        fragment.setSortType(position);
        Product[] products = fragment.getAll();
        fragment.getProductAdapter().setProducts(fragment.getAll());
        fragment.getProductAdapter().notifyDataSetChanged();
        ft.replace(container, fragment);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
