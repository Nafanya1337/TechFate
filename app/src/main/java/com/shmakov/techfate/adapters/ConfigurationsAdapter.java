package com.shmakov.techfate.adapters;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.shmakov.techfate.R;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ConfigurationsAdapter extends RecyclerView.Adapter<ConfigurationsAdapter.MyViewHolder> {

    private int selected = 0;
    protected String[] configurations;
    protected LayoutInflater inflater;


    public ConfigurationsAdapter(Context context, String[] configurations) {
        inflater = LayoutInflater.from(context);
        this.configurations = configurations;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = inflater.inflate(R.layout.item_configuration_field, parent, false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.radioButton.setText(configurations[position]);
        holder.radioButton.setChecked(position == selected);
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notifyItemChanged(selected);
                    notifyItemChanged(position);
                    selected = position;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return configurations.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final RadioButton radioButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.configuration_rb);
        }
    }

}
