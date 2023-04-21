package com.shmakov.techfate.fragments.globals;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.ConfigurationsAdapter;


public class ConfigurationFragment extends Fragment {

    public static final String CONF_KEY = "CONFIGURATIONS";
    public static final String AMOUNT_KEY = "AMOUNT";

    public String getConfiguration() {
        return conf[configurationsAdapter.selected];
    }

    private RecyclerView recyclerView;
    private String[] conf;
    private ConfigurationsAdapter configurationsAdapter;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        conf = getArguments().getStringArray(CONF_KEY);
        configurationsAdapter = new ConfigurationsAdapter(context, conf);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuration, container, false);
        recyclerView = view.findViewById(R.id.ConfigurationsRecyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setAdapter(configurationsAdapter);
    }
}