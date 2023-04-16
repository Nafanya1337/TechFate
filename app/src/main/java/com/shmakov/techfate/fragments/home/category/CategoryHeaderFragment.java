package com.shmakov.techfate.fragments.home.category;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shmakov.techfate.R;



public class CategoryHeaderFragment extends Fragment {

    private ImageView goBackButton, categoryOptions, categoryBackground;
    private TextView categoryTittle;
    private String categoryTittleName;
    private int categoryBackgroundImage = 0;

    public interface goBack{
        public void goBack(View view);
    }

    goBack goBack;

    public void setCategoryTittle(String categoryTittle) {
        this.categoryTittleName = categoryTittle;
    }

    public void setCategoryBackgroundImage(int categoryBackgroundImage) {
        this.categoryBackgroundImage = categoryBackgroundImage;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.goBack = (CategoryHeaderFragment.goBack) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_header, container, false);
        goBackButton = view.findViewById(R.id.category_back_button);
        categoryOptions = view.findViewById(R.id.category_options);
        categoryBackground = view.findViewById(R.id.category_background);
        categoryTittle = view.findViewById(R.id.category_name);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack.goBack(view);
            }
        });
        categoryTittle.setText(this.categoryTittleName);
        categoryBackground.setImageResource(this.categoryBackgroundImage);
    }
}