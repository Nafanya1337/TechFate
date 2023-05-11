package com.shmakov.techfate.mytools.TextWatchers;

import static com.shmakov.techfate.entities.Card.choosePaymentSystem;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.shmakov.techfate.R;

public class CardNumTextWatcher implements TextWatcher {
    StringBuilder sb = new StringBuilder();

    TextView cardNum;

    ImageView img;

    public CardNumTextWatcher(TextView cardNum, ImageView img) {
        this.cardNum = cardNum;
        this.img = img;
    }

    boolean nowEditting;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!nowEditting) {
            if (s.length() == 0)
                this.img.setImageResource(0);
            if (s.length() >= 1)
                this.img.setImageResource(choosePaymentSystem(s.charAt(0)));
            sb.delete(0, sb.length());
            for (int i = 0; i < s.length(); i++) {
                if (check(s.charAt(i)))
                    sb.append(s.charAt(i));
            }
            if (sb.length() >= 5) {
                if (sb.charAt(4) != ' ') sb.insert(4, ' ');
                if (sb.length() >= 10 && sb.charAt(9) != ' ') sb.insert(9, ' ');
                if (sb.length() >= 15 && sb.charAt(14) != ' ') sb.insert(14, ' ');
            }
            nowEditting = true;
            s.replace(0, s.length(), sb);
            nowEditting = false;
            cardNum.post(new Runnable() {
                @Override
                public void run() {
                    cardNum.setText(s.toString());
                }
            });
        }
    }

    private boolean check(char a) {return ('0' <= a && a <= '9');}
}
