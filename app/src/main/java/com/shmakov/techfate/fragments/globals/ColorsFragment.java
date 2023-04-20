package com.shmakov.techfate.fragments.globals;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.ColorAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class ColorsFragment extends Fragment {

    public static final String COLORS_ARRAY_TAG = "COLORS";

    public int[] amount;
    private RecyclerView colors_container;
    private ColorAdapter colorAdapter;
    private ArrayList<String> colors = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public ColorsFragment(int[] amount){
        this.amount = amount;
    }

    public ColorsFragment(){
        this.amount = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] temp = getArguments().getStringArray(COLORS_ARRAY_TAG);
        colors.addAll(Arrays.asList(temp));
        if (amount == null) {
            amount = new int[colors.size()];
            for (int i = 0; i < amount.length; i++) {
                amount[i] = 1;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_colors, container, false);
        colors_container = view.findViewById(R.id.colors_container);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        colorAdapter = new ColorAdapter(getContext(), colors, amount);
        colors_container.setAdapter(colorAdapter);
    }

    public void updateColorsAvailable(int[] available){
        colorAdapter.updateAvailable(available);
    }
}