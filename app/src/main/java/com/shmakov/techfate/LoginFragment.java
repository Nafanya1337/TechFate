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
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {
    public interface loginInterface{
        public void OnButtonClickLogin(String email, String password);

        public void loginOnBackPressed();
    }

    TextView signUpText, remind_password_text;

    Button buttonLogin;
    loginInterface loginInterface;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        loginInterface = (loginInterface) context;
    }

    TextInputEditText loginEmailText, loginPasswordText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginEmailText = view.findViewById(R.id.loginEmailText);
        loginPasswordText = view.findViewById(R.id.loginPasswordText);
        buttonLogin = view.findViewById(R.id.buttonLogin);
        signUpText = view.findViewById(R.id.signUpText);
        remind_password_text = view.findViewById(R.id.remind_password_text);
    }


    @Override
    public void onDestroy() {
        loginInterface.loginOnBackPressed();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmailText.getText().toString();
                String password = loginPasswordText.getText().toString();
                loginInterface.OnButtonClickLogin(email, password);
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

        remind_password_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_remindPasswordFragment);
            }
        });
    }


}