package com.shmakov.techfate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmakov.techfate.R;

import java.util.ArrayList;

public class SavedAdressesAdapter extends RecyclerView.Adapter<SavedAdressesAdapter.MyViewHolder> {

    int chosen_position = -1;
    ArrayList<String> addresses = new ArrayList<>();
    Context context;

    public void setAddresses(ArrayList<String> addresses) {
        this.addresses = addresses;
    }

    public SavedAdressesAdapter(Context context, ArrayList<String> addresses) {
        this.context = context;
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saved_address,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String allAddress = addresses.get(position);
        String[] places = allAddress.split(", ");
        allAddress = allAddress.replace(places[0] + ", ", "");
        holder.item_saved_addresses_address.setText(allAddress.replaceAll("(\\d)\\s", "$1, "));
        holder.item_saved_addresses_city.setText(places[0]);
        if (chosen_position == -1)
            chosen_position = position;
        holder.checkBox.setChecked(chosen_position==position);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notifyItemChanged(chosen_position);
                    chosen_position = position;
                }
            }
        });
        if (addresses.size() > 1)
            holder.item_saved_addresses_address_divider.setVisibility(View.VISIBLE);
    }

    public int getChosen_position() {
        return chosen_position;
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView item_saved_addresses_city, item_saved_addresses_address;

        RadioButton checkBox;

        ImageView item_saved_addresses_address_divider;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.address_checkbox);
            item_saved_addresses_city = itemView.findViewById(R.id.item_saved_addresses_city);
            item_saved_addresses_address = itemView.findViewById(R.id.item_saved_addresses_address);
            item_saved_addresses_address_divider = itemView.findViewById(R.id.item_saved_addresses_address_divider);
        }
    }
}
