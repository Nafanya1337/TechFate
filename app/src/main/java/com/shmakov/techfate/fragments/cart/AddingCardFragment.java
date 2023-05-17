package com.shmakov.techfate.fragments.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.Card;
import com.shmakov.techfate.mytools.TextWatchers.CardDateTextWatcher;
import com.shmakov.techfate.mytools.TextWatchers.CardHolderTextWatcher;
import com.shmakov.techfate.mytools.TextWatchers.CardNumTextWatcher;

public class AddingCardFragment extends Fragment {

    TextInputEditText addingCardFragment_CVC, addingCardFragment_Date, addingCardFragment_CardNum, addingCardFragment_Holder;

    View newCard;

    Button addingCardFragmentAddCardBtn;

    Card card;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            card = getArguments().getParcelable("Card");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding_card, container, false);
        addingCardFragment_CVC = view.findViewById(R.id.addingCardFragment_CVC);
        addingCardFragment_Date = view.findViewById(R.id.addingCardFragment_Date);
        addingCardFragment_CardNum = view.findViewById(R.id.addingCardFragment_CardNum);
        addingCardFragment_Holder = view.findViewById(R.id.addingCardFragment_Holder);
        addingCardFragment_Holder.setSelected(true);
        newCard = view.findViewById(R.id.newCard);
        addingCardFragmentAddCardBtn = view.findViewById(R.id.addingCardFragmentAddCardBtn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addingCardFragment_CardNum.addTextChangedListener(new CardNumTextWatcher(view.findViewById(R.id.new_card_num), view.findViewById(R.id.new_card_img)));
        addingCardFragment_Holder.addTextChangedListener(new CardHolderTextWatcher(view.findViewById(R.id.new_card_holder)));
        addingCardFragment_Date.addTextChangedListener(new CardDateTextWatcher(view.findViewById(R.id.new_card_data)));

        if (card != null) {
            TextView addingCardFragmentHeader = view.findViewById(R.id.addingCardFragmentHeader);
            addingCardFragmentHeader.setText("Карта №" + card.getCardNum().substring(15, 19));
            addingCardFragment_CardNum.setText(card.getCardNum());
            addingCardFragment_Holder.setText(card.getCardHolder());
            addingCardFragment_Date.setText(card.getCardDate());
            addingCardFragment_CVC.setText(card.getCVC());
            addingCardFragmentAddCardBtn.setText("Сохранить данные");
        }
        addingCardFragmentAddCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addingCardFragment_Holder.getText().length() == 0 || addingCardFragment_CardNum.getText().length() != 19 ||
                        addingCardFragment_Date.getText().length() != 5 || addingCardFragment_CVC.getText().length() != 3)
                {
                    Toast.makeText(getContext(), "Не все поля были заполнены", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle resultBundle = new Bundle();
                if (card == null) {
                    card = new Card(addingCardFragment_Holder.getText().toString(),
                            addingCardFragment_CardNum.getText().toString(),
                            addingCardFragment_Date.getText().toString(),
                            "Дебетовая карта",
                            addingCardFragment_CVC.getText().toString());
                }
                else {
                    card.setCardHolder(addingCardFragment_Holder.getText().toString());
                    card.setCardNum(addingCardFragment_CardNum.getText().toString());
                    card.setCardDate(addingCardFragment_Date.getText().toString());
                    card.setCVC(addingCardFragment_CVC.getText().toString());
                }
                resultBundle.putParcelable("Card", card);
                if (addingCardFragmentAddCardBtn.getText().toString().equals("Добавить карту"))
                    getParentFragmentManager().setFragmentResult("AddingCard", resultBundle);
                else
                    getParentFragmentManager().setFragmentResult("EdittingCard", resultBundle);
                Navigation.findNavController(view).popBackStack();
            }
        });
    }
}