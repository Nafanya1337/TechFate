package com.shmakov.techfate.fragments.cart;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.shmakov.techfate.R;
import com.shmakov.techfate.adapters.SavedAdressesAdapter;

import java.util.ArrayList;

public class SavedAddressesFragment extends Fragment {

    RecyclerView savedAddresses_listView;
    ArrayList<String> addresses;

    TextView noAddressesMessage;

    Context context;

    SavedAdressesAdapter savedAdressesAdapter;

    public SavedAddressesFragment(Context context, ArrayList<String> addresses) {
        this.addresses = addresses;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_addresses, container, false);
        savedAddresses_listView = view.findViewById(R.id.savedAddresses_listView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (addresses.size() != 0) {
            savedAdressesAdapter = new SavedAdressesAdapter(context, addresses);
            savedAddresses_listView.setAdapter(savedAdressesAdapter);
        }
        else {
            noAddressesMessage = view.findViewById(R.id.noAddressesMessage);
            noAddressesMessage.setVisibility(View.VISIBLE);
            savedAddresses_listView.setVisibility(View.GONE);
        }
    }

    public String getChosenAddress() {
        if (savedAdressesAdapter == null)
            return null;
        int i =savedAdressesAdapter.getChosen_position();
        return addresses.get(i);
    }

    public void setAddresses(ArrayList<String> addresses) {
        this.addresses = addresses;
        noAddressesMessage.setVisibility(View.GONE);
        savedAddresses_listView.setVisibility(View.VISIBLE);
        if (savedAdressesAdapter == null) {
            savedAdressesAdapter = new SavedAdressesAdapter(context, addresses);
            savedAddresses_listView.setAdapter(savedAdressesAdapter);
        }
        else {
            savedAdressesAdapter.setAddresses(addresses);
            savedAdressesAdapter.notifyDataSetChanged();
        }
    }
}