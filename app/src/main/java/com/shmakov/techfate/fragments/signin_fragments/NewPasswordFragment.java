package com.shmakov.techfate.fragments.signin_fragments;

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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.shmakov.techfate.R;

public class NewPasswordFragment extends Fragment {

    public interface changePassword{
        public void changePassword(String Email, String password);
    }

    String Email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Email = getArguments().getString("Email");
    }

    changePassword changePassword;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        changePassword = (changePassword) context;
    }

    Button changePasswordBtn;

    TextInputEditText changePassword1, changePassword2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changePasswordBtn = view.findViewById(R.id.changePasswordBtn);
        changePassword1 = view.findViewById(R.id.changePassword1);
        changePassword2 = view.findViewById(R.id.changePassword2);
    }

    @Override
    public void onResume() {
        super.onResume();
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1 = changePassword1.getText().toString();
                String password2 = changePassword2.getText().toString();

                if (password1.equals(password2)) {
                    changePassword.changePassword(Email, password1);
                    NavController controller = Navigation.findNavController(getView());
                    controller.popBackStack();
                    controller.popBackStack();
                    controller.popBackStack();
                }
                else {
                    Toast.makeText(getContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}