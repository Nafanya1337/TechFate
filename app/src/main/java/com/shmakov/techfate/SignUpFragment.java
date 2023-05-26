package com.shmakov.techfate;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SignUpFragment extends Fragment {


    public interface SignUpInterface {
        public void SignUpInterface(String Name, String Email, String Password);

        public boolean checkEmail(String Email);

        public void signUpOnBackPressed(NavController controller);
    }

    TextView signUpText, textUserAgreement;

    Button buttonSignUp;

    TextInputEditText signupEmailText, signupNameText, signupPasswordText;

    CheckBox UserAgreementCheckBox;

    SignUpInterface SignUpInterface;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        SignUpInterface = (SignUpInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signUpText = view.findViewById(R.id.signUpText);
        textUserAgreement = view.findViewById(R.id.textUserAgreement);
        buttonSignUp = view.findViewById(R.id.buttonSignUp);
        signupEmailText = view.findViewById(R.id.signupEmailText);
        signupNameText = view.findViewById(R.id.signupNameText);
        signupPasswordText = view.findViewById(R.id.signupPasswordText);
        UserAgreementCheckBox = view.findViewById(R.id.UserAgreementCheckBox);
    }



    @Override
    public void onResume() {
        super.onResume();
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });

        textUserAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_signUpFragment_to_userAgreementFragment);
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserAgreementCheckBox.isChecked()) {
                    String Email, Name, Password;
                    Email = signupEmailText.getText().toString();
                    if (!SignUpInterface.checkEmail(Email)) {
                        Name = signupNameText.getText().toString();
                        Password = signupPasswordText.getText().toString();
                        SignUpInterface.SignUpInterface(Name, Email, Password);
                    }
                    else {
                        Toast.makeText(getContext(), "Пользователь под почтовым ящиком " + Email + " уже зарегистрирован", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Необходимо согласиться с пользовательским соглашением", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}