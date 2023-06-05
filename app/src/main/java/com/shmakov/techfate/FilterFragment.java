package com.shmakov.techfate;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import android.os.VibrationEffect;
import android.os.Vibrator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.RangeSlider;
import com.shmakov.techfate.entities.inner.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;



public class FilterFragment extends BottomSheetDialogFragment {

    public interface makeFilters{
        public void makeFilters(int minCost, int maxCost, Integer valueFrom, Integer valueTo);
    }

    private Context context;

    private List<Float> val;
    private RangeSlider CostRangeSlider;

    private ArrayList<Product> products;

    private EditText min, max;

    private Integer min_val = -1, max_val = -1, valueFrom = -1, valueTo = -1;

    private ArrayList<String> addedColors = new ArrayList<>();
    public static final String MIN_MAX_COST_KEY = "MinMax";
    public static final String COLORS_KEY = "AddedColors";

    public static final String AVAILABLE = "AVAILABLE_STATUS";
    private makeFilters makeFilters;

    private float step = 500f;

    LayoutInflater inflater;

    public FilterFragment(Context context, ArrayList<Product> products, HashMap<String, ArrayList<String>> filters) {
        this.context = context;
        this.products = products;
        if (filters.containsKey(MIN_MAX_COST_KEY)) {
            min_val = Integer.valueOf(filters.get(MIN_MAX_COST_KEY).get(0));
            max_val = Integer.valueOf(filters.get(MIN_MAX_COST_KEY).get(1));
            valueFrom = Integer.valueOf(filters.get(MIN_MAX_COST_KEY).get(2));
            valueTo = Integer.valueOf(filters.get(MIN_MAX_COST_KEY).get(3));
        }
        if (filters.containsKey(COLORS_KEY)) {
            addedColors = filters.get(COLORS_KEY);
        }
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_filter, container, false);
        min = view.findViewById(R.id.editTextMin);
        max = view.findViewById(R.id.editTextMax);
        makeFilters = (makeFilters) context;
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        makeSlider();
    }

    private void makeSlider() {
        if (valueFrom.equals(-1)) {
            valueFrom = 0;
        }
        if (valueTo.equals(-1) && products.size() > 0)
            valueTo = products.stream().max(Comparator.comparing(product -> product.getCost())).get().getCost();
        else if (valueTo.equals(-1))
            valueTo = 100;
        if (max_val.equals(-1))
            max_val = Math.round(valueTo);
        if (min_val.equals(-1))
            min_val = 0;
        min.setText(String.valueOf(min_val));
        max.setText(String.valueOf(max_val));
    }

    @Override
    public void onStop() {
        super.onStop();
        makeFilters.makeFilters(min_val, max_val, valueFrom, valueTo);
    }
}