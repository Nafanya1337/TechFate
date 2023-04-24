package com.shmakov.techfate;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.RangeSlider;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.mytools.ColorManager;
import com.shmakov.techfate.mytools.MyComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class FilterFragment extends BottomSheetDialogFragment {

    public interface makeFilters{
        public void makeFilters(int minCost, int maxCost);
    }

    private Context context;
    private RangeSlider CostRangeSlider;

    private ArrayList<Product> products;

    private EditText min, max;


    private Integer min_val = -1, max_val = -1;

    private makeFilters makeFilters;
    public FilterFragment(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getTheme() {
        return R.style.FilterStyle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fragment_filter, container, false);
        CostRangeSlider = view.findViewById(R.id.CostRangeSlider);
        min = view.findViewById(R.id.editTextMin);
        max = view.findViewById(R.id.editTextMax);
        makeFilters = (makeFilters) context;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        makeSlider();
        CostRangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> val = slider.getValues();
                min_val = Math.round(val.get(0));
                max_val = Math.round(val.get(val.size() - 1));

                min.setText(String.valueOf(min_val));
                max.setText(String.valueOf(max_val));
            }
        });
//        min.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                makeSlider();
//
//            }
//        });
//        max.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                makeSlider();
//
//            }
//        });
    }

    private void makeSlider () {
        if (max_val != -1 | min_val != -1) {
            List<Float> val = CostRangeSlider.getValues();
            if (max_val != -1) {
                if (Math.round(val.get(val.size()  -1)) != max_val)
                    CostRangeSlider.setValueTo((float)max_val);
            }
            if (min_val != -1) {
                if (Math.round(val.get(0)) != min_val)
                    CostRangeSlider.setValueFrom((float)min_val);
            }
        }
        if (products.size() >= 1) {
            CostRangeSlider.setValueFrom(0f);
            float valueTo = products.stream().max(Comparator.comparing(product -> product.getCost())).get().getCost();
            CostRangeSlider.setValueTo(valueTo);
            max_val = Math.round(valueTo);
            max.setText(String.valueOf(Math.round(valueTo)));
            CostRangeSlider.setStepSize(500f);
            CostRangeSlider.setValues(0f, valueTo);
            min_val = 0;
        }
        else {
            CostRangeSlider.setValueFrom(0f);
            CostRangeSlider.setStepSize(0f);
            CostRangeSlider.setValueTo(0f);
            min_val = 0;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        makeFilters.makeFilters(min_val, max_val);
    }
}