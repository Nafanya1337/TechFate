package com.shmakov.techfate.fragments.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.SearchHistoryAdapter;

public class SearchFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    private String[] search_history = {
      "iPhone 13 Pro Max",
            "Realme 9 Pro",
            "Samsung Gear Sport"
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView search_list = view.findViewById(R.id.search_list);
        SearchHistoryAdapter search_adapter = new SearchHistoryAdapter(getContext(), search_history);
        search_list.setAdapter(search_adapter);
    }
}