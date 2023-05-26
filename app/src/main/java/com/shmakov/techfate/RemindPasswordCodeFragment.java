package com.shmakov.techfate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.shmakov.techfate.mytools.TextWatchers.CardNumTextWatcher;


public class RemindPasswordCodeFragment extends Fragment {

    TextInputEditText firstDigitCode, secondDigitCode, thirdDigitCode, fourthDigitCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_remind_password_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstDigitCode = view.findViewById(R.id.firstDigitCode);
        secondDigitCode = view.findViewById(R.id.secondDigitCode);
        thirdDigitCode = view.findViewById(R.id.thirdDigitCode);
        fourthDigitCode = view.findViewById(R.id.fourthDigitCode);
    }

    @Override
    public void onResume() {
        super.onResume();
        firstDigitCode.requestFocus();
        firstDigitCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) secondDigitCode.requestFocus();
            }
        });

        secondDigitCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) thirdDigitCode.requestFocus();
                if (s.length() == 0) firstDigitCode.requestFocus();
            }
        });

        thirdDigitCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) fourthDigitCode.requestFocus();
                if (s.length() == 0) secondDigitCode.requestFocus();
            }
        });

        fourthDigitCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) thirdDigitCode.requestFocus();
            }
        });
    }
}