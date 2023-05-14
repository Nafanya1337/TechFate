package com.shmakov.techfate.fragments.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shmakov.techfate.PaymentActivity;
import com.shmakov.techfate.ProductCardDialog;
import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.MiniProductInCardAdapter;
import com.shmakov.techfate.entities.Card;
import com.shmakov.techfate.entities.Cart;
import com.shmakov.techfate.entities.Order;
import com.shmakov.techfate.entities.ProductInCart;
import com.shmakov.techfate.mytools.ImageManager;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class MakeOrderFragment extends Fragment implements MiniProductInCardAdapter.showProductInfo {

    RecyclerView products_in_cart_recycler;

    String Address, DeliveryMethod, PaymentMethod, PromocodeName;

    MiniProductInCardAdapter miniProductInCardAdapter;
    Card card;

    Cart cart;

    int total_cost = 0;

    int delivery_cost = 0;

    float PromocodeRate;

    TextView make_order_delivery_cost, make_order_total_cost, make_order_promocode, make_order_promocode_name;

    View selectedAddress, selectedDelivery, selectedPaymentMethod;

    Button makeOrderBtnNext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Address = getArguments().getString("Address");
            DeliveryMethod = getArguments().getString("DeliveryMethod");
            PaymentMethod = getArguments().getString("PaymentMethod");
            card = getArguments().getParcelable("Card");
            PromocodeName = PaymentActivity.PromocodeName;
            PromocodeRate = PaymentActivity.PromocodeRate;
            cart = PaymentActivity.cart;
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
        return view;
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
        if (!Objects.equals(PromocodeName, "")) {
            make_order_promocode_name.setText("Промокод " + PromocodeName);
            make_order_promocode.setText("-" + (int)PromocodeRate + "%");
        }
        else
            make_order_promocode.setText("-");
        int delivery_cost = Integer.parseInt(parts[1].replaceAll(" ", "").replace("₽", ""));
        make_order_delivery_cost.setText(delivery_cost + " ₽");
        cart.addSum(delivery_cost);
        make_order_total_cost.setText(cart.getTotal_cost() + " ₽");

        makeOrderBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random_num = new Random();
                StringBuilder order_name = new StringBuilder();
                order_name.append((char) (random_num.nextInt(26) + 65));
                order_name.append((char) (random_num.nextInt(26) + 65));
                order_name.append("#");
                order_name.append(String.valueOf(random_num.nextInt(8999) + 1000));
                Order order = new Order(order_name.toString(), cart, address.getText().toString(), delivery.getText().toString(), cart.getTotal_cost());
                Bundle bundle = new Bundle();
                bundle.putParcelable("Order", order);
                Navigation.findNavController(view).navigate(R.id.action_makeOrderFragment_to_orderInfoFragment, bundle);
            }
        });
    }

    @Override
    public void showProductInfo(int position) {
        ProductCardDialog productCardDialog = new ProductCardDialog(cart.getProducts().get(position));
        productCardDialog.show(getParentFragmentManager(), "ProductInfo");
    }
}