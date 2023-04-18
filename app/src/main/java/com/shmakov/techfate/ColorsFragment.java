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

import com.shmakov.techfate.adapters.ColorAdapter;

import java.util.ArrayList;

public class ColorsFragment extends Fragment {

    public static final String COLORS_ARRAY_TAG = "COLORS";

    private RecyclerView colors_container;
    private ColorAdapter colorAdapter;
    private ArrayList<String> colors;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle array = getArguments();
        colors = array.getStringArrayList(COLORS_ARRAY_TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_colors, container, false);
        Log.d("mymy", colors.toString());
        colors_container = view.findViewById(R.id.colors_container);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        colorAdapter = new ColorAdapter(getContext(), colors);
        colors_container.setAdapter(colorAdapter);
    }
}