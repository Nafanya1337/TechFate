package com.shmakov.techfate.fragments.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.shmakov.techfate.R;
import com.shmakov.techfate.fragments.globals.ItemsFragment;

public class SearchFragment extends Fragment {

    private EditText search_bar;
    private FrameLayout search_container;
    private HistorySearchFragment historySearchFragment;

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
            ItemsFragment itemsFragment = new ItemsFragment(search_text);
            ft.replace(search_container.getId(), itemsFragment).commit();
        }
    }
}