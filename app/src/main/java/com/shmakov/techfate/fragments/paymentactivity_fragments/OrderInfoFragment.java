package com.shmakov.techfate.fragments.paymentactivity_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.Order;

public class OrderInfoFragment extends Fragment {

    public interface exitPayment{
        public void exitPayment(Order order);
    }

    Button fragmentOrderInfoNextBtn;
    Order order;

    TextView order_success;

    exitPayment exitPayment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        exitPayment = (exitPayment) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null)
            order = getArguments().getParcelable("Order");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_info, container, false);
        fragmentOrderInfoNextBtn = view.findViewById(R.id.fragmentOrderInfoNextBtn);
        order_success = view.findViewById(R.id.order_success);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentOrderInfoNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitPayment.exitPayment(order);
            }
        });
        order_success.setText("Заказ " + order.getName() + " успешно оплачен!");
    }
}