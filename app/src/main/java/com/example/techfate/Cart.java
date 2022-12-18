package com.example.techfate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Cart extends AppCompatActivity {

    public CalendarView calendarView;
    public ArrayList<String> names_of_products;
    public ArrayList<Integer> prices;
    public String names = "", costs = "", delivery_days = "";
    public TextView sum, order_names, order_costs, courier_date, adress, summary;
    public RadioGroup delivery;
    public RadioButton rus_post, sdek, courier, postmat;
    public int del = -1;

    public EditText Name, Adress, Phone, Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        names_of_products = (ArrayList<String>) bundle.getStringArrayList("names");
        prices = (ArrayList<Integer>) bundle.getIntegerArrayList("prices");
        sum = findViewById(R.id.sum_cost);
        order_costs = findViewById(R.id.order_costs);
        order_names = findViewById(R.id.order_names);
        ListToOrder();
        order_names.setText(names);
        order_costs.setText(costs);
        sum.setText(Integer.toString(makeSum()) + " ₽");
        calendarView = findViewById(R.id.calendar);
        calendarView.setVisibility(View.GONE);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                delivery_days = Integer.toString(dayOfMonth) + "/" + Integer.toString(month + 1) + "/" + Integer.toString(year);
            }
        });
        delivery = (RadioGroup) findViewById(R.id.delivery_method);
        courier_date = findViewById(R.id.courier_date);
        rus_post = findViewById(R.id.rus_post);
        sdek = findViewById(R.id.sdek);
        courier = findViewById(R.id.courier);
        postmat = findViewById(R.id.postmat);
        adress = findViewById(R.id.adress);
        SetTexts();
        summary = findViewById(R.id.summary);
        summary.setText(sum.getText().toString());
        Name = findViewById(R.id.Name);
        Adress = findViewById(R.id.Adress);
        Phone = findViewById(R.id.Phone);
        Email = findViewById(R.id.Email);
    }

    public void Payment(View view){
        String str = "";
        if (Name.getText().toString().length() == 0){
            str += " имя";
        }
        if (Adress.getText().toString().length() == 0){
            if (str.length() != 0)
                str += ",";
            str += " адрес";
        }
        if (Phone.getText().toString().length() == 0){
            if (str.length() != 0)
                str += ",";
            str += " номер телефона";
        }
        if (del == -1) {
            if (str.length() != 0)
                str += ",";
            str += " способ доставки";
        }
        if (str == "") {
            int sum = Integer.parseInt(summary.getText().toString().replace(" ₽", ""));
            String adress = Adress.getText().toString();
            String temp = "";
            if (del == 0) temp = rus_post.getText().toString();
            if (del == 1) temp = sdek.getText().toString();
            if (del == 3) temp = postmat.getText().toString();
            int i = 0;
            if (del != 2) {
                delivery_days = "";
                while (temp.charAt(i) != '(') {
                    i++;
                }
                i++;
                while (temp.charAt(i) != ' ') {
                    delivery_days += temp.charAt(i);
                    i++;
                }
            }
            Intent mIntent = new Intent(getBaseContext(), Payment.class);
            mIntent.putExtra("sum", sum);
            mIntent.putExtra("name", Name.getText().toString());
            mIntent.putExtra("adress", adress);
            mIntent.putExtra("delivery", del);
            mIntent.putExtra("date", delivery_days);
            startActivity(mIntent);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Невозможно оформить заказ");
            builder.setMessage("Введите" + str);
            builder.setPositiveButton("Хорошо", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            (builder.create()).show();
        }
    }

    public void SetTexts(){
        int i = (new Random()).nextInt(20) + 1;
        if (i <= 5) i += 10;
        else if (i <= 9) i += 3;
        rus_post.setText("Почта Росии " + "0 ₽ (" + (Integer.toString(i)) + " д.)");
        sdek.setText("СДЭК " + "300 ₽ (" + (Integer.toString(i - 3)) + " д.)");
        courier.setText("Курьером " + "500 ₽ (" + (Integer.toString(i - 9)) + " д.)");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, i - 9);
        calendarView.setMinDate(calendar.getTimeInMillis());
        postmat.setText("Постмат " + "400 ₽ (" + (Integer.toString(i - 5)) + " д.)");
    }

    public void deletePreviousDelivery(){
        if (del == 1) summary.setText(Integer.toString(Integer.parseInt(summary.getText().toString()) - 300));
        if (del == 2) summary.setText(Integer.toString(Integer.parseInt(summary.getText().toString()) - 500));
        if (del == 3) summary.setText(Integer.toString(Integer.parseInt(summary.getText().toString()) - 400));
    }

    public void ChooseDelivery(View view){
        summary.setText(summary.getText().toString().replace(" ₽", ""));
        deletePreviousDelivery();
        switch (view.getId()){
            case R.id.rus_post:
                del = 0;
                adress.setText("Укажите адрес почты");
                calendarView.setVisibility(View.GONE);
                courier_date.setVisibility(View.GONE);
                break;
            case R.id.sdek:
                del = 1;
                adress.setText("Укажите адрес доставки");
                calendarView.setVisibility(View.GONE);
                courier_date.setVisibility(View.GONE);
                summary.setText(Integer.toString(Integer.parseInt(summary.getText().toString()) + 300) + " ₽");
                break;
            case R.id.courier:
                del = 2;
                adress.setText("Укажите адрес доставки");
                calendarView.setVisibility(View.VISIBLE);
                courier_date.setVisibility(View.VISIBLE);
                summary.setText(Integer.toString(Integer.parseInt(summary.getText().toString()) + 500) + " ₽");
                break;
            case R.id.postmat:
                del = 3;
                adress.setText("Укажите адрес постмата");
                calendarView.setVisibility(View.GONE);
                courier_date.setVisibility(View.GONE);
                summary.setText(Integer.toString(Integer.parseInt(summary.getText().toString()) + 400) + " ₽");
                break;
        }
    }

    protected void setCalendar(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        calendarView.setMinDate(Long.parseLong(formattedDate));
    }

    protected void Back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("names", names_of_products);
        bundle.putIntegerArrayList("prices", prices);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    protected void ListToOrder(){
        for (int i = 0; i < names_of_products.size(); i++) {
            String name = names_of_products.get(i);
            String price = Integer.toString(prices.get(i));
            if (!name.equals("")) {
                if (!names.equals("")) names += "\n\n";
                names += name;
                if (!costs.equals("")) costs += "\n\n";
                costs += price + " ₽";
            }
        }
    }

    protected int makeSum(){
        int s = 0;
        for (int i = 0; i<prices.size(); i++){
            s += prices.get(i);
        }
        return s;
    }


}