package com.shmakov.techfate.mytools.TextWatchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class CardHolderTextWatcher implements TextWatcher {

    TextView holder;

    public CardHolderTextWatcher(TextView holder) {
        this.holder = holder;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        holder.post(new Runnable() {
            @Override
            public void run() {
                holder.setText(s.toString());
            }
        });
    }
}
