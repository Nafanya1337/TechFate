package com.shmakov.techfate;

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

import com.shmakov.techfate.adapters.ColorPickerAdapter;
import com.shmakov.techfate.mytools.ColorManager;

import java.util.ArrayList;

public class ColorCheckboxFragment extends Fragment {

    private RecyclerView checkbox_color_recycler;
    private ColorPickerAdapter colorPickerAdapter;

    private Context context;

    private ArrayList<String> colors;


    public ColorCheckboxFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_checkbox, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (CategoryActivity.selected_colors.size() == 0)
            colorPickerAdapter = new ColorPickerAdapter(getContext(), ColorManager.all_available_colors);
        else
            colorPickerAdapter = new ColorPickerAdapter(getContext(), ColorManager.all_available_colors, CategoryActivity.selected_colors);
        checkbox_color_recycler = view.findViewById(R.id.checkbox_color_recycler);
        checkbox_color_recycler.setAdapter(colorPickerAdapter);
        super.onViewCreated(view, savedInstanceState);
    }
}