package com.shmakov.techfate.mytools.TextWatchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shmakov.techfate.fragments.cart.CardPaymentFragment;

public class CardNumTextWatcher implements TextWatcher {
    StringBuilder sb = new StringBuilder();

    TextView cardNum;

    public CardNumTextWatcher(TextView cardNum) {
        this.cardNum = cardNum;
    }

    boolean nowEditing;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        char last = s.charAt(s.length() - 1);
        if (checkForNum(last)) {

        }
        cardNum.post(new Runnable() {
            @Override
            public void run() {
                cardNum.setText(s.toString());
            }
        });
    }

    private boolean checkForNum(char a) {return ('0' <= a && a <= '9');}
}
