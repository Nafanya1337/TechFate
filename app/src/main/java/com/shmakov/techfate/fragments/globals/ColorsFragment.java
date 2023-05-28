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
        colorAdapter = new ColorAdapter(getContext(), colors, amount);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_colors, container, false);
        colors_container = view.findViewById(R.id.colors_container);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        colors_container.setAdapter(colorAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void updateColorsAvailable(int[] available,int position){
        colorAdapter.updateAvailable(available, position);
    }

    public int selectedColor(){
        if (colorAdapter != null)
            return colorAdapter.getPicked();
        return 0;
    }

}