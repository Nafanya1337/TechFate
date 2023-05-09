package com.shmakov.techfate;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shmakov.techfate.adapters.SavedCardsAdapter;
import com.shmakov.techfate.entities.Card;

import java.util.ArrayList;

public class CardSwiperFragment extends Fragment {

    RecyclerView cardSwiperRecycler;

    SavedCardsAdapter savedCardsAdapter;

    Context context;

    ArrayList<Card> cards;

    public CardSwiperFragment(Context context, ArrayList<Card> cards) {
        this.context = context;
        this.cards = cards;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_swiper, container, false);
        cardSwiperRecycler = view.findViewById(R.id.cardSwiperRecycler);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        savedCardsAdapter = new SavedCardsAdapter(context, cards);
        cardSwiperRecycler.setAdapter(savedCardsAdapter);
    }
}