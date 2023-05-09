package com.shmakov.techfate;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shmakov.techfate.adapters.SavedCardsAdapter;
import com.shmakov.techfate.entities.Card;
import com.shmakov.techfate.fragments.cart.AddingCardFragment;

import java.util.ArrayList;

public class CardFragment extends Fragment {

    LinearLayout cardContainer;

    SavedCardsAdapter savedCardsAdapter;

    Button cardFragmentAddingCardBtn;
    Context context;
    ArrayList<Card> cards;

    public CardFragment(Context context, ArrayList<Card> cards) {
        this.context = context;
        this.cards = cards;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        cardContainer = view.findViewById(R.id.cardContainer);
        cardFragmentAddingCardBtn = view.findViewById(R.id.cardFragmentAddingCardBtn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (cards.isEmpty()) {
            TextView textView = new TextView(context);
            textView.setText("Не найдено ни одной сохраненной карты \uD83D\uDCB3");
            textView.setTextSize(16f);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(25, 0, 25, 0);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor( "#000000"));
            cardContainer.addView(textView);
        }
        else {
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            CardSwiperFragment cardSwiperFragment = new CardSwiperFragment(context, cards);
            ft.replace(cardContainer.getId(), cardSwiperFragment).commit();
        }

        cardFragmentAddingCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_cardPaymentFragment_to_addingCardFragment);
            }
        });
    }
}