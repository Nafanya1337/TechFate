package com.shmakov.techfate;



import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AddressAcceptionFragment extends MaterialAlertDialogBuilder {

    private Context context;
    private String address;
    public AddressAcceptionFragment(@NonNull Context context, String address) {
        super(context);
        this.context = context;
        this.address = address;
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);

    }

    @NonNull
    @Override
    public AlertDialog create() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setView(R.layout.fragment_address_acception);
        alertDialog.setTitle("Подтвердите корректность адреса");
        alertDialog.setNegativeButton("Изменить адрес", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setMessage(address);
        return alertDialog.create();
    }
}