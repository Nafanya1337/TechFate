package com.shmakov.techfate.fragments.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.shmakov.techfate.CardFragment;
import com.shmakov.techfate.R;

import java.util.ArrayList;


public class CardPaymentFragment extends Fragment {


    FrameLayout cardPaymentFragmentContainerCard;

    private String DeliveryMethod, Address;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            DeliveryMethod = getArguments().getString("DeliveryMethod");
            Address = getArguments().getString("Address");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_payment, container, false);
        cardPaymentFragmentContainerCard = view.findViewById(R.id.cardPaymentFragmentContainerCard);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        CardFragment cardFragment = new CardFragment(getContext(), new ArrayList<>());
        ft.replace(cardPaymentFragmentContainerCard.getId(), cardFragment).commit();
    }
}