package com.shmakov.techfate.fragments.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.DeliveryMethodAdapter;

public class DeliveryFragment extends Fragment {
    private String address;

    private String[] deliveryMethods;
    RecyclerView deliveryFragmentDeliveryMethodsRecyclerView;

    Button deliveryFragmentNextBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            address = getArguments().getString("Address");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery, container, false);
        deliveryMethods = getResources().getStringArray(R.array.delivery_methods);
        deliveryFragmentDeliveryMethodsRecyclerView = view.findViewById(R.id.deliveryFragmentDeliveryMethodsRecyclerView);
        deliveryFragmentNextBtn = view.findViewById(R.id.deliveryFragmentNextBtn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DeliveryMethodAdapter deliveryMethodAdapter = new DeliveryMethodAdapter(getContext(), deliveryMethods);
        deliveryFragmentDeliveryMethodsRecyclerView.setAdapter(deliveryMethodAdapter);
        deliveryFragmentNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                int i = deliveryMethodAdapter.getChosen_position();
                bundle.putString("DeliveryMethod", deliveryMethods[i]);
                bundle.putString("Address", address);
                Navigation.findNavController(view).navigate(R.id.action_deliveryFragment_to_cardPaymentFragment, bundle);
            }
        });
    }


}