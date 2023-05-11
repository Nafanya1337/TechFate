package com.shmakov.techfate;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
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

public class CardFragment extends Fragment implements SavedCardsAdapter.openCard {

    LinearLayout cardContainer;

    SavedCardsAdapter savedCardsAdapter;

    Button cardFragmentAddingCardBtn;
    Context context;
    ArrayList<Card> cards;

    CardSwiperFragment cardSwiperFragment;

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
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        cardSwiperFragment = new CardSwiperFragment(context, cards, this);
        ft.replace(cardContainer.getId(), cardSwiperFragment).commit();

        cardFragmentAddingCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_cardPaymentFragment_to_addingCardFragment);
            }
        });

        getParentFragmentManager().setFragmentResultListener("AddingCard", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (requestKey.equals("AddingCard")) {
                    if (result.containsKey("Card")) {
                        Card card = result.getParcelable("Card");
                        cards.add(card);
                        cardSwiperFragment.setCards(cards);
                    }
                }
            }
        });
    }

    @Override
    public void openCard(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Card", cards.get(position));
        Navigation.findNavController(getView()).navigate(R.id.action_cardPaymentFragment_to_addingCardFragment, bundle);
        getParentFragmentManager().setFragmentResultListener("EdittingCard", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (requestKey.equals("EdittingCard")) {
                    if (result.containsKey("Card")) {
                        Card card = result.getParcelable("Card");
                        cards.set(position, card);
                        cardSwiperFragment.setCards(cards);
                    }
                }
            }
        });
    }
}