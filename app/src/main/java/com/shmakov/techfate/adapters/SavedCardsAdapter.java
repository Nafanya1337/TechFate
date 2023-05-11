package com.shmakov.techfate.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.RecyclerView;

import com.shmakov.techfate.CardFragment;
import com.shmakov.techfate.CardSwiperFragment;
import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.Card;

import java.util.ArrayList;

public class SavedCardsAdapter extends RecyclerView.Adapter<SavedCardsAdapter.MyViewHolder> {

    ArrayList<Card> cards = new ArrayList<>();

    public interface openCard {
        public void openCard(int position);
    }

    openCard openCard;
    Context context;

    public void setCard(int position, Card card) {
        cards.set(position, card);
        notifyItemChanged(position);
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    public SavedCardsAdapter(Context context, ArrayList<Card> cards, CardFragment cardSwiperFragment) {
        this.context = context;
        this.cards = cards;
        openCard = (openCard) cardSwiperFragment;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saved_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Card card = cards.get(position);
        String num = "**** " + card.getCardNum().substring(15, 19).toString();
        holder.card_num.setText(num);
        holder.card_holder.setText(card.getCardHolder());
        holder.card_data.setText(card.getCardDate());
        holder.card_type.setText(card.getCardType());
        holder.card_img.setImageResource(card.getCardImg());

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openCard.openCard(position);
                return true;
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return cards.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView card_num, card_data, card_type, card_holder;

        ImageView card_img;

        ConstraintLayout view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_num = itemView.findViewById(R.id.card_num);
            card_data = itemView.findViewById(R.id.card_data);
            card_type = itemView.findViewById(R.id.card_type);
            card_holder = itemView.findViewById(R.id.card_holder);
            card_img = itemView.findViewById(R.id.card_img);
            view = itemView.findViewById(R.id.saved_card_view);
        }
    }
}
