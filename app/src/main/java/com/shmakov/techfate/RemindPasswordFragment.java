package com.shmakov.techfate;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public class RemindPasswordFragment extends Fragment {

    public interface remindPasswordInterface {
        public boolean remindPasswordInterface(String Email);

        public void makeNotification(int code);
    }

    remindPasswordInterface remindPasswordInterface;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        remindPasswordInterface = (remindPasswordInterface) context;
    }

    Button buttonPasswordRemindEmail;

    TextInputEditText remindPasswordEmailText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_remind_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonPasswordRemindEmail = view.findViewById(R.id.buttonPasswordRemindEmail);
        remindPasswordEmailText = view.findViewById(R.id.remindPasswordEmailText);
    }

    @Override
    public void onResume() {
        super.onResume();
        Random random_num = new Random();
        int code = random_num.nextInt(8999) + 1000;


        buttonPasswordRemindEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = remindPasswordEmailText.getText().toString();
                if (remindPasswordInterface.remindPasswordInterface(Email)) {
                    remindPasswordInterface.makeNotification(code);
                    Navigation.findNavController(getView()).navigate(R.id.action_remindPasswordFragment_to_remindPasswordCodeFragment);
                } else {
                    Toast.makeText(getContext(), "Пользователь под данной электронной почтой не зарегистрирован", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}