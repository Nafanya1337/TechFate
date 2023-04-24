package com.shmakov.techfate.fragments.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.inner.Category;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.fragments.globals.ItemsFragment;
import com.shmakov.techfate.mytools.StringWorker;

public class SearchFragment extends Fragment {

    private EditText search_bar;
    private FrameLayout search_container;
    private HistorySearchFragment historySearchFragment;

    ItemsFragment itemsFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsFragment = new ItemsFragment(getContext(), new Product[0]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        historySearchFragment = new HistorySearchFragment();
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search_bar = view.findViewById(R.id.search_bar);
        search_container = view.findViewById(R.id.search_container);
        changeFragment("");
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeFragment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        historySearchFragment = null;
    }

    private void changeFragment(String search_text) {
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (search_text.equals("")) {
            ft.replace(search_container.getId(), historySearchFragment).commit();
        }
        else {
            ft.replace(search_container.getId(), itemsFragment).commit();
            Product[] products = Category.getAllProducts()
                    .stream()
                    .filter(product ->
                            product.getMark().toUpperCase().contains(search_text.toUpperCase())
                                    | product.getName().toUpperCase().contains(search_text.toUpperCase())
                                    | StringWorker.makeProductName(product.getMark(), product.getName()).toUpperCase().contains(search_text.toUpperCase())
                    )
                    .toArray(Product[]::new);
            if (search_text.equals("Realme 9")) {
                Log.d("mymy", "321312");
            }
            itemsFragment.setAll(products);
        }
    }

    private Product[] searchEngine(String search_text) {
        Product[] products = Category.getAllProducts()
                .stream()
                .filter(product ->searchWords(product, search_text)
                )
                .toArray(Product[]::new);


        return products;
    }


    private boolean searchWords(Product product, String text){
        String[] searchWords = text.toUpperCase().split("\\s+");
        Log.d("mymy", "slova  " + searchWords.toString());
        for (String searchWord : searchWords) {
            if (product.getMark().toUpperCase().contains(searchWord) ||
                    product.getName().toUpperCase().contains(searchWord) ||
                    StringWorker.makeProductName(product.getMark(), product.getName()).toUpperCase().contains(searchWord)) {
                return true;
            }
        }
        return false;
    }
}