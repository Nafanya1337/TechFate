package com.example.techfate;

import static androidx.core.app.NotificationCompat.PRIORITY_DEFAULT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class Done extends AppCompatActivity {

    private NotificationManager notificationManager;
    public TextView name, adress, summ_of, delivery_method;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        name = findViewById(R.id.name_of_user);
        adress = findViewById(R.id.adress_of_user);
        summ_of = findViewById(R.id.summ_of);
        delivery_method = findViewById(R.id.delivery_method);

        String temp = getIntent().getStringExtra("name");
        name.setText(temp);
        temp = getIntent().getStringExtra("adress");
        adress.setText(temp);
        int t = getIntent().getIntExtra("Delivery", -1);
        switch (t){
            case 0:
                delivery_method.setText("Почтой России" + " через " + getIntent().getStringExtra("date") + " д.");
                break;
            case 1:
                delivery_method.setText("в СДЭК" + " через " + getIntent().getStringExtra("date") + " д.");
                break;
            case 2:
                delivery_method.setText("Курьером" + " " + getIntent().getStringExtra("date"));
                break;
            case 3:
                delivery_method.setText("в Постмат" + " через " + getIntent().getStringExtra("date") + " д.");
                break;
        }

        t = getIntent().getIntExtra("sum", 0);
        summ_of.setText(Integer.toString(t) + " ₽");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }
}