package com.example.techfate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button cart;
    public ArrayList<Product> list;
    TextView sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<Product>();
        makeList();
        setContentView(R.layout.activity_main);
        sum = findViewById(R.id.sum);
        cart = findViewById(R.id.cart_open);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            ArrayList<String> names_of_products = bundle.getStringArrayList("names");
            ArrayList<Integer> prices = bundle.getIntegerArrayList("prices");
            for (int i = 0; i < names_of_products.size(); i++) {
                list.get(i).setName(names_of_products.get(i));
                list.get(i).setPrice(prices.get(i));
            }
        }
        sum.setText(Integer.toString(makeSum()) + " ₽");

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCart(v);
            }
        });
    }

    public void OpenCart(View view){
        if (!sum.getText().toString().equals("0 ₽")) {
            ArrayList<String> strings = makeNames();
            ArrayList<Integer> integers = makePrices();
            Intent mIntent = new Intent(this, Cart.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("names", strings);
            bundle.putIntegerArrayList("prices", integers);
            mIntent.putExtra("bundle", bundle);
            startActivity(mIntent);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Корзина пуста");
            builder.setMessage("Добавьте товары, чтобы перейти к оформлению");
            builder.setPositiveButton("Хорошо", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            (builder.create()).show();
        }
    }

    public ArrayList<String> makeNames(){
        ArrayList<String> arr = new ArrayList<String>();
        for (int i = 0; i<list.size(); i++){
            arr.add(list.get(i).getName());
        }
        return arr;
    }

    public ArrayList<Integer> makePrices(){
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (int i = 0; i<list.size(); i++){
            arr.add(list.get(i).getPrice());
        }
        return arr;
    }

    public void Add(View view){
        TextView text = findViewById(R.id.amount);
        TextView cost = findViewById(R.id.cost1);
        String str = "0";
        int i = 0;
        switch(view.getId()){
            case R.id.add1:
                break;
            case R.id.add2:
                i = 1;
                text = findViewById(R.id.amount2);
                cost = findViewById(R.id.cost2);
                break;
            case R.id.add3:
                i = 2;
                text = findViewById(R.id.amount3);
                cost = findViewById(R.id.cost3);
                break;
            case R.id.add4:
                i = 3;
                text = findViewById(R.id.amount4);
                cost = findViewById(R.id.cost4);
                break;
            case R.id.add5:
                i = 4;
                text = findViewById(R.id.amount5);
                cost = findViewById(R.id.cost5);
                break;
            case R.id.add6:
                i = 5;
                text = findViewById(R.id.amount6);
                cost = findViewById(R.id.cost6);
                break;
            case R.id.add7:
                i = 6;
                text = findViewById(R.id.amount7);
                cost = findViewById(R.id.cost7);
                break;
            case R.id.add8:
                i = 7;
                text = findViewById(R.id.amount8);
                cost = findViewById(R.id.cost8);
                break;
            case R.id.add9:
                i = 8;
                text = findViewById(R.id.amount9);
                cost = findViewById(R.id.cost9);
                break;
        }
        String cost_str = cost.getText().toString();
        cost_str = cost_str.replace(" ₽", "");
        cost_str = cost_str.replace(".","");
        list.get(i).setPrice(list.get(i).getPrice() + Integer.parseInt(cost_str));
        sum.setText(Integer.toString(makeSum()) + " ₽");
        str = Integer.toString((Integer.parseInt(text.getText().toString()) + 1)).toString();
        text.setText(str);
    }

    public void Sub(View view){
        TextView text = findViewById(R.id.amount);
        Button add = findViewById(R.id.add1);
        String str = "0";
        Button buy = findViewById(R.id.buy1);
        TextView cost = findViewById(R.id.cost1);
        int i = 0;
        switch(view.getId()){
            case R.id.sub:
                break;
            case R.id.sub2:
                i = 1;
                cost = findViewById(R.id.cost2);
                text = findViewById(R.id.amount2);
                add = findViewById(R.id.add2);
                buy = findViewById(R.id.buy2);
                break;
            case R.id.sub3:
                i = 2;
                cost = findViewById(R.id.cost3);
                text = findViewById(R.id.amount3);
                add = findViewById(R.id.add3);
                buy = findViewById(R.id.buy3);
                break;
            case R.id.sub4:
                i = 3;
                cost = findViewById(R.id.cost4);
                text = findViewById(R.id.amount4);
                add = findViewById(R.id.add4);
                buy = findViewById(R.id.buy4);
                break;
            case R.id.sub5:
                i = 4;
                cost = findViewById(R.id.cost5);
                text = findViewById(R.id.amount5);
                add = findViewById(R.id.add5);
                buy = findViewById(R.id.buy5);
                break;
            case R.id.sub6:
                i = 5;
                cost = findViewById(R.id.cost6);
                text = findViewById(R.id.amount6);
                add = findViewById(R.id.add6);
                buy = findViewById(R.id.buy6);
                break;
            case R.id.sub7:
                i = 6;
                cost = findViewById(R.id.cost7);
                text = findViewById(R.id.amount7);
                add = findViewById(R.id.add7);
                buy = findViewById(R.id.buy7);
                break;
            case R.id.sub8:
                i = 7;
                cost = findViewById(R.id.cost8);
                text = findViewById(R.id.amount8);
                add = findViewById(R.id.add8);
                buy = findViewById(R.id.buy8);
                break;
            case R.id.sub9:
                i = 8;
                cost = findViewById(R.id.cost9);
                text = findViewById(R.id.amount9);
                add = findViewById(R.id.add9);
                buy = findViewById(R.id.buy9);
                break;
        }
        if (!text.getText().toString().equals("1")) {
            String str_cost = cost.getText().toString();
            str_cost = str_cost.replace(" ₽", "");
            str_cost = str_cost.replace(".","");
            list.get(i).setPrice(list.get(i).getPrice() - Integer.parseInt(str_cost));
            str = Integer.toString((Integer.parseInt(text.getText().toString()) - 1)).toString();
            if (makeSum() > 0) {
                sum.setText(Integer.toString(makeSum()) + " ₽");
                text.setText(str);
            } else {
                sum.setText("0 ₽");
                list.get(i).setName("");
                text.setVisibility(View.GONE);
                add.setVisibility(View.GONE);
                findViewById(view.getId()).setVisibility(View.GONE);
                buy.setVisibility(View.VISIBLE);
            }
        }
        else{
            String str_cost = cost.getText().toString();
            str_cost = str_cost.replace(" ₽", "");
            list.get(i).setPrice(0);
            sum.setText(Integer.toString(makeSum()) + " ₽");
            list.get(i).setName("");
            text.setVisibility(View.GONE);
            add.setVisibility(View.GONE);
            findViewById(view.getId()).setVisibility(View.GONE);
            buy.setVisibility(View.VISIBLE);
        }
    }

    public void Buy(View view){
        TextView text = findViewById(R.id.amount);
        TextView name = findViewById(R.id.name1);
        TextView cost = findViewById(R.id.cost1);
        Button add = findViewById(R.id.add1);
        Button sub = findViewById(R.id.sub);
        int i = 0;
        switch(view.getId()){
            case R.id.buy1:
                break;
            case R.id.buy2:
                i = 1;
                name = findViewById(R.id.name2);
                cost = findViewById(R.id.cost2);
                text = findViewById(R.id.amount2);
                add = findViewById(R.id.add2);
                sub = findViewById(R.id.sub2);
                break;
            case R.id.buy3:
                i = 2;
                name = findViewById(R.id.name3);
                cost = findViewById(R.id.cost3);
                text = findViewById(R.id.amount3);
                add = findViewById(R.id.add3);
                sub = findViewById(R.id.sub3);
                break;
            case R.id.buy4:
                i = 3;
                name = findViewById(R.id.name4);
                cost = findViewById(R.id.cost4);
                text = findViewById(R.id.amount4);
                add = findViewById(R.id.add4);
                sub = findViewById(R.id.sub4);
                break;
            case R.id.buy5:
                i = 4;
                name = findViewById(R.id.name5);
                cost = findViewById(R.id.cost5);
                text = findViewById(R.id.amount5);
                add = findViewById(R.id.add5);
                sub = findViewById(R.id.sub5);
                break;
            case R.id.buy6:
                i = 5;
                name = findViewById(R.id.name6);
                cost = findViewById(R.id.cost6);
                text = findViewById(R.id.amount6);
                add = findViewById(R.id.add6);
                sub = findViewById(R.id.sub6);
                break;
            case R.id.buy7:
                i = 6;
                name = findViewById(R.id.name7);
                cost = findViewById(R.id.cost7);
                text = findViewById(R.id.amount7);
                add = findViewById(R.id.add7);
                sub = findViewById(R.id.sub7);
                break;
            case R.id.buy8:
                i = 7;
                name = findViewById(R.id.name8);
                cost = findViewById(R.id.cost8);
                text = findViewById(R.id.amount8);
                add = findViewById(R.id.add8);
                sub = findViewById(R.id.sub8);
                break;
            case R.id.buy9:
                i = 8;
                name = findViewById(R.id.name9);
                cost = findViewById(R.id.cost9);
                text = findViewById(R.id.amount9);
                add = findViewById(R.id.add9);
                sub = findViewById(R.id.sub9);
                break;
        }
        text.setVisibility(View.VISIBLE);
        text.setText("1");
        add.setVisibility(View.VISIBLE);
        sub.setVisibility(View.VISIBLE);
        String str = cost.getText().toString();
        str = str.replace(" ₽", "");
        this.list.get(i).setName(name.getText().toString());
        str = str.replace(".","");
        this.list.get(i).setPrice(Integer.parseInt(str));
        sum.setText(Integer.toString(makeSum()) + " ₽");
        findViewById(view.getId()).setVisibility(View.GONE);
        makeToast(name.getText().toString());
    }

    protected void makeList(){
        Product temp;
        for (int i = 0; i < 10; i++) {
            temp = new Product();
            list.add(temp);
        }
    }

    protected int makeSum(){
        int s = 0;
        for (int i = 0; i < list.size(); i++){
            s += list.get(i).getPrice();
        }
        return s;
    }

    public void makeToast(String text){
        Toast.makeText(this,"Товар " + (text) + " добавлен в корзину", Toast.LENGTH_SHORT).show();
    }
}