package com.shmakov.techfate.fragments.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.shmakov.techfate.CardFragment;
import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.Card;

import java.util.ArrayList;


public class CardPaymentFragment extends Fragment {

    Button cardPaymentFragmentNextBtn;
    FrameLayout cardPaymentFragmentContainerCard;

    ArrayList<Card> cards = new ArrayList<>();

    CardFragment cardFragment;

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
        cardPaymentFragmentNextBtn = view.findViewById(R.id.cardPaymentFragmentNextBtn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        cardFragment = new CardFragment(getContext(), cards);
        ft.replace(cardPaymentFragmentContainerCard.getId(), cardFragment).commit();
        cardPaymentFragmentNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_cardPaymentFragment_to_makeOrderFragment);
            }
        });
    }
}