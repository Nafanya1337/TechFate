package com.shmakov.techfate.fragments.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.shmakov.techfate.R;
import com.shmakov.techfate.mytools.TextWatchers.CardDateTextWatcher;
import com.shmakov.techfate.mytools.TextWatchers.CardHolderTextWatcher;
import com.shmakov.techfate.mytools.TextWatchers.CardNumTextWatcher;

public class AddingCardFragment extends Fragment {

    TextInputEditText addingCardFragment_CVC, addingCardFragment_Date, addingCardFragment_CardNum, addingCardFragment_Holder;

    View newCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding_card, container, false);
        addingCardFragment_CVC = view.findViewById(R.id.addingCardFragment_CVC);
        addingCardFragment_Date = view.findViewById(R.id.addingCardFragment_Date);
        addingCardFragment_CardNum = view.findViewById(R.id.addingCardFragment_CardNum);
        addingCardFragment_Holder = view.findViewById(R.id.addingCardFragment_Holder);
        newCard = view.findViewById(R.id.newCard);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addingCardFragment_CardNum.addTextChangedListener(new CardNumTextWatcher(view.findViewById(R.id.new_card_num)));
        addingCardFragment_Holder.addTextChangedListener(new CardHolderTextWatcher(view.findViewById(R.id.new_card_holder)));
        addingCardFragment_Date.addTextChangedListener(new CardDateTextWatcher(view.findViewById(R.id.new_card_data)));
    }
}