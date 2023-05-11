package com.shmakov.techfate.mytools.TextWatchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class CardDateTextWatcher implements TextWatcher {

    TextView cardDate;

    boolean nowEditting = false;

    StringBuilder sb = new StringBuilder();

    public CardDateTextWatcher(TextView cardDate) {
        this.cardDate = cardDate;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!nowEditting) {
            sb.delete(0, sb.length());
            for (int i = 0; i < s.length(); i++) {
                if (check(s.charAt(i)))
                    sb.append(s.charAt(i));
            }
            if (sb.length() >= 3)
                sb.insert(2, '/');
            nowEditting = true;
            s.replace(0, s.length(), sb);
            nowEditting = false;
            cardDate.post(new Runnable() {
                @Override
                public void run() {
                    cardDate.setText(s.toString());
                }
            });
        }
    }

    public boolean check(char a) {return '0' <= a && a <= '9';}
}
