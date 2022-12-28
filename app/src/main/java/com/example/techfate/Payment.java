package com.example.techfate;

import static androidx.core.app.NotificationCompat.PRIORITY_DEFAULT;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Payment extends AppCompatActivity {

    public ConstraintLayout card, pay2;
    public TextView summary;
    public int sum, del;
    public String adress, delivery_days, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        card = (ConstraintLayout) findViewById(R.id.card_payment);
        pay2 = (ConstraintLayout) findViewById(R.id.payment_qiwi_u);
        summary = findViewById(R.id.to_pay);
        sum = getIntent().getIntExtra("sum", 0);
        summary.setText(Integer.toString(sum) + " ₽");
        del = getIntent().getIntExtra("delivery", -1);
        adress = getIntent().getStringExtra("adress");
        delivery_days = getIntent().getStringExtra("date");
        name = getIntent().getStringExtra("name");
    }

    public void Pay(View view){
        String str = "";
        if (view.getId() == R.id.payment){
            if (((EditText)findViewById(R.id.card_num)).getText().toString().length() < 16){
                str += " номера карты";
            }
            if (((EditText)findViewById(R.id.cvc)).getText().toString().length() < 3){
                if (str.length() != 0)
                    str += ",";
                str += " cvc кода";
            }
            if (((EditText)findViewById(R.id.dd)).getText().toString().length() < 2 || ((EditText)findViewById(R.id.mm)).getText().toString().length() < 2 || ((EditText)findViewById(R.id.gg)).getText().toString().length() < 2){
                if (str.length() != 0)
                    str += ",";
                str += " даты окончания действия карты";
            }
        }
        if (view.getId() == R.id.payment2){
            if (((EditText)findViewById(R.id.nomer_qiwi)).getText().toString().length() < 10)
                str += " номера кошелька";
        }
        if (str == "") {
            Intent intent = new Intent(getBaseContext(), Done.class);
            intent.putExtra("sum", sum);
            intent.putExtra("Delivery", del);
            intent.putExtra("adress", adress);
            intent.putExtra("date", delivery_days);
            intent.putExtra("name", name);
            startActivity(intent);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Невозможно оплатить заказ");
            builder.setMessage("Проверьте правильность" + str);
            builder.setPositiveButton("Хорошо", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            (builder.create()).show();
        }
    }

    public void ChoosePayment(View view){
        switch (view.getId()){
            case R.id.card:
                card.setVisibility(View.VISIBLE);
                pay2.setVisibility(View.GONE);
                break;
            case R.id.qiwi: case R.id.umoney:
                pay2.setVisibility(View.VISIBLE);
                card.setVisibility(View.GONE);
                break;
        }

    }
}