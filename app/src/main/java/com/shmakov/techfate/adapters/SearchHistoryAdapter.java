package com.shmakov.techfate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.shmakov.techfate.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchHistoryAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> history;

    public SearchHistoryAdapter(@NonNull Context context, ArrayList<String> resource) {
        super(context, R.layout.item_search, resource);
        this.context = context;
        history = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        TextView name = convertView.findViewById(R.id.history_search_text);
        name.setText(history.get(position));
        ImageView delete = convertView.findViewById(R.id.close_search_item);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(history.get(position));
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
