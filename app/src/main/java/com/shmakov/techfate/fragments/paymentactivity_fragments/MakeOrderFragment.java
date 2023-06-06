package com.shmakov.techfate.fragments.paymentactivity_fragments;

import static com.yandex.runtime.Runtime.getApplicationContext;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.shmakov.techfate.PaymentActivity;
import com.shmakov.techfate.fragments.globals.ProductCardDialog;
import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.MiniProductInCardAdapter;
import com.shmakov.techfate.entities.Card;
import com.shmakov.techfate.entities.Cart;
import com.shmakov.techfate.entities.Order;
import com.shmakov.techfate.entities.ProductInCart;
import com.shmakov.techfate.mytools.ImageManager;

import java.util.Objects;
import java.util.Random;


public class MakeOrderFragment extends Fragment implements MiniProductInCardAdapter.showProductInfo {


    boolean isAlreadyMade = false;
    RecyclerView products_in_cart_recycler;

    String Address = "", DeliveryMethod = "", PaymentMethod = "", PromocodeName = "", Status = "";

    MiniProductInCardAdapter miniProductInCardAdapter;
    Card card;

    Cart cart = null;

    int total_cost = 0;

    int delivery_cost = 0;

    float PromocodeRate = 0f;

    TextView make_order_delivery_cost, make_order_total_cost, make_order_promocode, make_order_promocode_name, status1Text, status2Text, status3Text, status4Text;

    View selectedAddress, selectedDelivery, selectedPaymentMethod;

    Button makeOrderBtnNext;

    LinearLayout orderStatusLayout;

    RadioButton status1, status2, status3, status4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Address = getArguments().getString("Address");
            DeliveryMethod = getArguments().getString("DeliveryMethod");
            PaymentMethod = getArguments().getString("PaymentMethod");
            card = getArguments().getParcelable("Card");
            isAlreadyMade = getArguments().getBoolean("IsAlreadyMade");
            if (!isAlreadyMade) {
                PromocodeName = PaymentActivity.PromocodeName;
                PromocodeRate = PaymentActivity.PromocodeRate;
                cart = PaymentActivity.cart;
            }
            else {
                PromocodeName = getArguments().getString("PromocodeName");
                PromocodeRate = getArguments().getFloat("PromocodeRate");
                cart = getArguments().getParcelable("Cart");
                delivery_cost = getArguments().getInt("DeliveryCost");
                Status = getArguments().getString("Status");
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_make_order, container, false);
        selectedAddress = view.findViewById(R.id.selectedAddress);
        selectedDelivery = view.findViewById(R.id.selectedDelivery);
        selectedPaymentMethod = view.findViewById(R.id.selectedPaymentMethod);
        make_order_delivery_cost = view.findViewById(R.id.make_order_delivery_cost);
        make_order_total_cost = view.findViewById(R.id.make_order_total_cost);
        make_order_promocode = view.findViewById(R.id.make_order_promocode);
        make_order_promocode_name = view.findViewById(R.id.make_order_promocode_name);
        products_in_cart_recycler = view.findViewById(R.id.products_in_cart_recycler);
        miniProductInCardAdapter = new MiniProductInCardAdapter(this, cart.getProducts().toArray(new ProductInCart[0]));
        products_in_cart_recycler.setAdapter(miniProductInCardAdapter);
        makeOrderBtnNext = view.findViewById(R.id.makeOrderBtnNext);
        orderStatusLayout = view.findViewById(R.id.orderStatusLayout);
        status1 = view.findViewById(R.id.status1);
        status2 = view.findViewById(R.id.status2);
        status3 = view.findViewById(R.id.status3);
        status4 = view.findViewById(R.id.status4);
        status1Text = view.findViewById(R.id.status1Text);
        status2Text = view.findViewById(R.id.status2Text);
        status3Text = view.findViewById(R.id.status3Text);
        status4Text = view.findViewById(R.id.status4Text);
        if (isAlreadyMade)
            makeStatus();
        return view;
    }

    public void makeStatus(){
        String temp = status1Text.getText().toString();
        Animation blinkAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);


        if (Status.equals(temp)) {
            status1.startAnimation(blinkAnimation);
            status1Text.setTextColor(Color.parseColor("#02C596"));
            status1.setChecked(true);
        }

        temp = status2Text.getText().toString();
        if (Status.equals(temp)) {
            status1Text.setTextColor(Color.parseColor("#02C596"));
            status1.setChecked(true);

            status2.startAnimation(blinkAnimation);
            status2Text.setTextColor(Color.parseColor("#02C596"));
            status2.setChecked(true);
        }

        temp = status3Text.getText().toString();
        if (Status.equals(temp)) {
            status1Text.setTextColor(Color.parseColor("#02C596"));
            status1.setChecked(true);

            status2Text.setTextColor(Color.parseColor("#02C596"));
            status2.setChecked(true);

            status3.startAnimation(blinkAnimation);
            status3Text.setTextColor(Color.parseColor("#02C596"));
            status3.setChecked(true);
        }

        temp = status4Text.getText().toString();
        if (Status.equals(temp)) {
            status1Text.setTextColor(Color.parseColor("#02C596"));
            status1.setChecked(true);

            status2Text.setTextColor(Color.parseColor("#02C596"));
            status2.setChecked(true);

            status3Text.setTextColor(Color.parseColor("#02C596"));
            status3.setChecked(true);

            status4.setChecked(true);
            status4Text.setTextColor(Color.parseColor("#02C596"));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView address = selectedAddress.findViewById(R.id.itemSelectedAdressText);
        address.setText(Address);
        TextView delivery = selectedDelivery.findViewById(R.id.item_selected_delivery_text);
        String[] parts = DeliveryMethod.split("\\)");
        delivery.setText(parts[0] + ')');
        ImageView delivery_img = selectedDelivery.findViewById(R.id.item_selected_delivery_img);
        delivery_img.setImageResource(ImageManager.findDeliveryIMG(parts[0] + ')'));
        TextView payment = selectedPaymentMethod.findViewById(R.id.item_selected_payment_text);
        ImageView payment_img = selectedPaymentMethod.findViewById(R.id.item_selected_payment_img);
        payment.setText(PaymentMethod);
        if (card != null)
            payment.append(" " + card.getCardNum().substring(15, 19));
        payment_img.setImageResource(ImageManager.findPaymentIMG(PaymentMethod));
        if (PromocodeName != null && !Objects.equals(PromocodeName, "")) {
            make_order_promocode_name.setText("Промокод " + PromocodeName);
            make_order_promocode.setText("-" + (int)PromocodeRate + "%");
        }
        else
            make_order_promocode.setText("-");
        int delivery_cost;
        if (!isAlreadyMade) {
            delivery_cost = Integer.parseInt(parts[1].replaceAll(" ", "").replace("₽", ""));
            this.delivery_cost = delivery_cost;
            cart.addSum(delivery_cost);
        }
            else {
            delivery_cost = this.delivery_cost;
        }
        make_order_delivery_cost.setText(delivery_cost + " ₽");
        make_order_total_cost.setText(cart.getTotal_cost() + " ₽");

        if (!isAlreadyMade) {
            orderStatusLayout.setVisibility(View.GONE);
            makeOrderBtnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Random random_num = new Random();
                    StringBuilder order_name = new StringBuilder();
                    order_name.append((char) (random_num.nextInt(26) + 65));
                    order_name.append((char) (random_num.nextInt(26) + 65));
                    order_name.append("#");
                    order_name.append(String.valueOf(random_num.nextInt(8999) + 1000));
                    String promoName = "";
                    Float promoRate = 0f;
                    if (!make_order_promocode_name.getText().toString().equals("Промокод")) {
                        promoName = make_order_promocode_name.getText().toString().replaceAll("Промокод ", "");
                        promoRate = Float.valueOf(make_order_promocode.getText().toString().replaceAll("-", "").replaceAll("%", ""));
                    }
                    Order order = new Order(order_name.toString(), cart, address.getText().toString(), delivery.getText().toString(), payment.getText().toString(), cart.getTotal_cost(), delivery_cost, promoName, promoRate);
                    Bundle bundle = new Bundle();
                    order.setStatus("В ОБРАБОТКЕ");
                    bundle.putParcelable("Order", order);
                    Navigation.findNavController(view).navigate(R.id.action_makeOrderFragment_to_orderInfoFragment, bundle);
                }
            });
        }
        else {
            makeOrderBtnNext.setText("Закрыть");
            makeOrderBtnNext.setCompoundDrawables(null,null,null,null);
            makeOrderBtnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(view).popBackStack();
                }
            });
        }
    }

    @Override
    public void showProductInfo(int position) {
        ProductCardDialog productCardDialog = new ProductCardDialog(cart.getProducts().get(position));
        productCardDialog.show(getParentFragmentManager(), "ProductInfo");
    }
}