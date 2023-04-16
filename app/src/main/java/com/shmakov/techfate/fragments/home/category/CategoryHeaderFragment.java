package com.shmakov.techfate.fragments.home.category;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shmakov.techfate.R;



public class CategoryHeaderFragment extends Fragment {

    public ImageView goBackButton;

    public interface goBack{
        public void goBack(View view);
    }

    goBack goBack;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.goBack = (CategoryHeaderFragment.goBack) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_header, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goBackButton = view.findViewById(R.id.category_back_button);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack.goBack(view);
            }
        });
    }
}