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

    String mask = "XX/XX";

    @Override
    public void afterTextChanged(Editable s) {
        if (!nowEditting) {

            for (int i_s = 0, i_m = 0; i_s < s.length() && i_m < mask.length(); i_s++, i_m++) {
                if (mask.charAt(i_m) == 'X' && check(s.charAt(i_s))) {

                    sb.append(s.charAt(i_s));
                }
                if (mask.charAt(i_m) == '/' && check(s.charAt(i_s))) {
                    sb.append('/');
                    sb.append(s.charAt(i_s));
                }
            }
            nowEditting = true;
            s.clear();
            s.append(sb.toString());
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
