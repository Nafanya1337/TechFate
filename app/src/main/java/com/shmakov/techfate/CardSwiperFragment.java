package com.shmakov.techfate;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shmakov.techfate.Helpers.SwipeHelper;
import com.shmakov.techfate.adapters.SavedCardsAdapter;
import com.shmakov.techfate.entities.Card;

import java.util.ArrayList;

public class CardSwiperFragment extends Fragment {

    RecyclerView cardSwiperRecycler;

    SavedCardsAdapter savedCardsAdapter;

    Context context;

    TextView swiper_zero_cards_text;

    CardFragment cardFragment;

    SwipeHelper linearSnapHelper;

    ArrayList<Card> cards;

    public void replaceCard(int position, Card card) {
        this.cards.set(position, card);
        if (savedCardsAdapter != null)
            savedCardsAdapter.setCard(position, card);
        else
            savedCardsAdapter = new SavedCardsAdapter(context, cards, cardFragment);
    }

    public CardSwiperFragment(Context context, ArrayList<Card> cards, CardFragment cardFragment) {
        this.context = context;
        this.cards = cards;
        this.cardFragment = cardFragment;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
        if (swiper_zero_cards_text != null && swiper_zero_cards_text.getVisibility() == View.VISIBLE) {
            swiper_zero_cards_text.setVisibility(View.GONE);
        }
        if (savedCardsAdapter != null)
            savedCardsAdapter.setCards(cards);
        else
            savedCardsAdapter = new SavedCardsAdapter(context, cards, cardFragment);
        card_position = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_swiper, container, false);
        cardSwiperRecycler = view.findViewById(R.id.cardSwiperRecycler);
        swiper_zero_cards_text = view.findViewById(R.id.swiper_zero_cards_text);
        return view;
    }

    int card_position = -1;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        savedCardsAdapter = new SavedCardsAdapter(context, cards, cardFragment);
        cardSwiperRecycler.setAdapter(savedCardsAdapter);
        if (cards.isEmpty()) {
            swiper_zero_cards_text.setVisibility(View.VISIBLE);
        }
        else {
            if (swiper_zero_cards_text != null && swiper_zero_cards_text.getVisibility() == View.VISIBLE)
                swiper_zero_cards_text.setVisibility(View.GONE);
            card_position = 0;
        }


        linearSnapHelper = new SwipeHelper();
        linearSnapHelper.attachToRecyclerView(cardSwiperRecycler);
    }

    public int getFocusableCardPosition(){
        if (linearSnapHelper.getCurrent_pos() == -1) {
            return card_position;
        }
        return linearSnapHelper.getCurrent_pos();
    }
}