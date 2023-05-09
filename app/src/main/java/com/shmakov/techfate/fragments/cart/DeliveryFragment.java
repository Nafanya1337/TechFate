package com.shmakov.techfate.fragments.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shmakov.techfate.R;

public class DeliveryFragment extends Fragment {

    private TextView temptemptemp;
    private String msg = "1";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery, container, false);
        temptemptemp = view.findViewById(R.id.temptemptemp);
        if (getArguments() != null)
            msg = getArguments().getString("Adress");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        temptemptemp.setText(msg);
    }


}