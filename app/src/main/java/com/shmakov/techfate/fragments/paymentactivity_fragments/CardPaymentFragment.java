package com.shmakov.techfate.fragments.paymentactivity_fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.shmakov.techfate.MainActivity;
import com.shmakov.techfate.PaymentActivity;
import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.PaymentMethodAdapter;
import com.shmakov.techfate.entities.Card;

import java.util.ArrayList;


public class CardPaymentFragment extends Fragment {

    Button cardPaymentFragmentNextBtn;
    FrameLayout cardPaymentFragmentContainerCard;
    RecyclerView payment_methods_listView;

    ArrayList<Card> cards = PaymentActivity.user.getCards();

    CardFragment cardFragment;

    String[] methods;

    PaymentMethodAdapter paymentMethodAdapter;

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
        payment_methods_listView = view.findViewById(R.id.payment_methods_listView);
        methods = getResources().getStringArray(R.array.payment_methods);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        paymentMethodAdapter = new PaymentMethodAdapter(getContext(), methods);
        payment_methods_listView.setAdapter(paymentMethodAdapter);

        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        cards = MainActivity.user.getCards();
        cardFragment = new CardFragment(getContext(), cards);
        ft.replace(cardPaymentFragmentContainerCard.getId(), cardFragment).commit();
        cardPaymentFragmentNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = null;
                String PaymentMethod = methods[paymentMethodAdapter.getSelected()];
                if (paymentMethodAdapter.getSelected() == 0) {
                    if (cardFragment.getCurrentCard() != -1) {
                        card = cards.get(cardFragment.getCurrentCard());
                    } else {
                        Toast.makeText(getContext(), "Не выбрана ни одна карта", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("Card", card);
                bundle.putString("DeliveryMethod", DeliveryMethod);
                bundle.putString("Address", Address);
                bundle.putString("PaymentMethod", PaymentMethod);
                Navigation.findNavController(view).navigate(R.id.action_cardPaymentFragment_to_makeOrderFragment, bundle);
            }
        });
    }


}