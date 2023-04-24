package com.shmakov.techfate.adapters;

import android.content.Context;
import android.icu.util.Freezable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.animation.AnimationUtils;
import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.Review;
import com.shmakov.techfate.entities.User;

import java.util.ArrayList;

public class FullReviewAdapter extends RecyclerView.Adapter<FullReviewAdapter.MyViewHolder> {

    private ArrayList<Review> reviews;
    private LayoutInflater inflater;


    public FullReviewAdapter(Context context, ArrayList<Review> reviews) {
        inflater = LayoutInflater.from(context);
        this.reviews = reviews;
    }

    public void addNewReviews(ArrayList<Review> reviews){
        this.reviews.addAll(reviews);
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.full_review_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Review review = reviews.get(position);
        User user = review.getUser();
        holder.avatar.setImageResource(user.getImg());
        holder.reviewer_name.setText(user.getName());
        holder.date.setText(review.getDate());
        String txt = review.getText();
        if (txt.length() > 200) {
            Button showFullReviewButton = holder.itemView.findViewById(R.id.show_full_review_button);
            txt = txt.substring(0, 200) + "...";
            showFullReviewButton.setVisibility(View.VISIBLE);
            showFullReviewButton.setOnClickListener(v -> {
                if (showFullReviewButton.getText().equals("Показать полный комментарий")) {
                    holder.review_text.setText(review.getText());
                    showFullReviewButton.setText("Скрыть комментарий");
                }
                else {
                    holder.review_text.setText(review.getText().substring(0, 200) + "...");
                    showFullReviewButton.setText("Показать полный комментарий");
                }
            });
        }
        holder.review_text.setText(txt);
        holder.ratingBar.setRating(review.getRating());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView reviewer_name, date, review_text;
        private RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            reviewer_name = itemView.findViewById(R.id.reviewer_name);
            date = itemView.findViewById(R.id.review_date);
            review_text = itemView.findViewById(R.id.review_text);
            ratingBar = itemView.findViewById(R.id.full_review_bar);
        }
    }
}