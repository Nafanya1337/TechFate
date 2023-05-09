package com.shmakov.techfate.fragments.cart;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.shmakov.techfate.R;


public class AddressBottomSheetFragment extends BottomSheetDialogFragment {

    Button adressFragment_chose_delivery_btn;
    TextInputEditText adressFragment_city, adressFragment_street, adressFragment_houseNum, adressFragment_apartamentsNum, adressFragment_mailIndex;

    String address = "";

    public AddressBottomSheetFragment() {
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetStyle;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_bottom_sheet, container, false);
        adressFragment_chose_delivery_btn = view.findViewById(R.id.adressFragment_chose_delivery_btn);
        adressFragment_city = view.findViewById(R.id.adressFragment_city);
        adressFragment_street = view.findViewById(R.id.adressFragment_street);
        adressFragment_houseNum = view.findViewById(R.id.adressFragment_houseNum);
        adressFragment_apartamentsNum = view.findViewById(R.id.adressFragment_apartamentsNum);
        adressFragment_mailIndex = view.findViewById(R.id.adressFragment_mailIndex);

        if (getArguments() != null) {
            String[] parts = getArguments().getStringArray("parts");
            try {
                adressFragment_city.setText(parts[2]);
                adressFragment_street.setText(parts[0]);
                adressFragment_houseNum.setText(parts[1]);
                adressFragment_apartamentsNum.setText("");
                adressFragment_mailIndex.setText(parts[5]);
            } catch (IndexOutOfBoundsException error) {
            }
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adressFragment_chose_delivery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEmpty = false;

                if (!adressFragment_city.getText().toString().equals("")) {
                    if (!address.contains(adressFragment_city.getText().toString()))
                        address += adressFragment_city.getText().toString() + ",";
                } else {
                    adressFragment_city.setError("Поле не может быть пустым!");
                    isEmpty = true;
                }

                if (!adressFragment_street.getText().toString().equals("")) {
                    if (!address.contains(adressFragment_street.getText().toString()))
                        address += " " + adressFragment_street.getText().toString();
                } else {
                    adressFragment_street.setError("Поле не может быть пустым!");
                    isEmpty = true;
                }

                if (!adressFragment_houseNum.getText().toString().equals("")) {
                    if (!address.contains(adressFragment_houseNum.getText().toString())) {
                        if (!adressFragment_houseNum.getText().toString().contains("д"))
                            address += ", д.";
                        address += " " + adressFragment_houseNum.getText().toString();
                    }
                } else {
                    adressFragment_houseNum.setError("Поле не может быть пустым!");
                    isEmpty = true;
                }

                if (!adressFragment_apartamentsNum.getText().toString().equals("")) {
                    if (!address.contains(adressFragment_apartamentsNum.getText().toString()))
                        address += " кв. " + adressFragment_apartamentsNum.getText().toString();
                } else {
                    adressFragment_apartamentsNum.setError("Поле не может быть пустым!");
                    isEmpty = true;
                }
                if (!adressFragment_mailIndex.getText().toString().equals("")) {
                    if (!address.contains(adressFragment_mailIndex.getText().toString()))
                        address += " " + adressFragment_mailIndex.getText().toString();
                } else {
                    adressFragment_mailIndex.setError("Поле не может быть пустым!");
                    isEmpty = true;
                }
                if (!isEmpty) {
                    Bundle resultBundle = new Bundle();
                    resultBundle.putString("Address", address);
                    getParentFragmentManager().setFragmentResult("requestKey", resultBundle);
                    dismiss();
                } else
                    Toast.makeText(getContext(), "Не все обязательные поля были заполнены!", Toast.LENGTH_LONG).show();
            }
        });
    }
}