package com.shmakov.techfate.fragments.main_activity_fragments.account;

import static com.yandex.runtime.Runtime.getApplicationContext;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.shmakov.techfate.R;


public class setOrderStatusFragment extends BottomSheetDialogFragment {

    RadioButton setStatus1, setStatus2, setStatus3, setStatus4;

    String Status;

    public setOrderStatusFragment(String Status) {
        this.Status = Status;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_order_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setStatus1 = view.findViewById(R.id.setStatus1);
        setStatus2 = view.findViewById(R.id.setStatus2);
        setStatus3 = view.findViewById(R.id.setStatus3);
        setStatus4 = view.findViewById(R.id.setStatus4);
        setStatus1.setOnClickListener(this::setStatusOnClick);
        setStatus2.setOnClickListener(this::setStatusOnClick);
        setStatus3.setOnClickListener(this::setStatusOnClick);
        setStatus4.setOnClickListener(this::setStatusOnClick);
        makeStatus();
    }

    public void setStatusOnClick(View view) {
        // Отключаем все кнопки
        setStatus1.setChecked(false);
        setStatus2.setChecked(false);
        setStatus3.setChecked(false);
        setStatus4.setChecked(false);

        switch (view.getId()) {
            case R.id.setStatus4:
                setStatus4.setChecked(true);
            case R.id.setStatus3:
                setStatus3.setChecked(true);
            case R.id.setStatus2:
                setStatus2.setChecked(true);
            case R.id.setStatus1:
                setStatus1.setChecked(true);
        }
    }

    public void makeStatus(){
        if (Status.equals("ЗАКАЗ ДОСТАВЛЕН"))
            setStatus4.callOnClick();
        else if (Status.equals("В ДОСТАВКЕ")) {
            setStatus3.callOnClick();
        } else if (Status.equals("В СБОРКЕ")) {
            setStatus2.callOnClick();
        }else {
            setStatus1.callOnClick();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        if (setStatus4.isChecked())
            Status = "ЗАКАЗ ДОСТАВЛЕН";
        else if (setStatus3.isChecked()) {
            Status = "В ДОСТАВКЕ";
        } else if (setStatus2.isChecked()) {
            Status = "В СБОРКЕ";
        }else {
            Status = "В ОБРАБОТКЕ";
        }
        intent.putExtra("Status", Status); // Поместите выбранный статус в Intent
        requireActivity().getSupportFragmentManager().setFragmentResult("requestKey", intent.getExtras()); // "requestKey" - ключ запроса
    }
}