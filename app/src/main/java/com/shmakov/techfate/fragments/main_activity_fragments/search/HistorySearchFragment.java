package com.shmakov.techfate.fragments.main_activity_fragments.search;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.SearchHistoryAdapter;

import java.util.ArrayList;

public class HistorySearchFragment extends Fragment {
    private static ArrayList<String> search_history = new ArrayList<>();

    private SearchHistoryAdapter search_adapter;

    public void addToHistory(String txt){
        search_history.add(txt);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView search_list = view.findViewById(R.id.search_list);
        search_adapter = new SearchHistoryAdapter(getContext(), search_history);
        search_list.setAdapter(search_adapter);
    }
}