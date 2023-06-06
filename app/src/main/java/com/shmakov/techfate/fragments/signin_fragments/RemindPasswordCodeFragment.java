package com.shmakov.techfate.fragments.signin_fragments;

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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.shmakov.techfate.R;
import com.shmakov.techfate.mytools.TextWatchers.CardNumTextWatcher;


public class RemindPasswordCodeFragment extends Fragment {

    TextInputEditText firstDigitCode, secondDigitCode, thirdDigitCode, fourthDigitCode;

    String Email, Code;

    Button changePasswordCodeNextBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("Email")) {
            Email = getArguments().getString("Email");
            Code = getArguments().getString("Code");
        }
    }

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
        changePasswordCodeNextBtn = view.findViewById(R.id.changePasswordCodeNextBtn);
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


        changePasswordCodeNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = firstDigitCode.getText().toString() + secondDigitCode.getText() + thirdDigitCode.getText() + fourthDigitCode.getText();
                if (code.equals(Code)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Email", Email);
                    Navigation.findNavController(getView()).navigate(R.id.action_remindPasswordCodeFragment_to_newPasswordFragment, bundle);
                }
                else {
                    Toast.makeText(getContext(), "Неверный код подтверждения", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}