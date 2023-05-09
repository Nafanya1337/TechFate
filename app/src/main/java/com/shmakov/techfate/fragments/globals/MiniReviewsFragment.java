package com.shmakov.techfate.fragments.globals;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.shmakov.techfate.R;

public class MiniReviewsFragment extends Fragment {

    public static final String AVG_RATING = "AVG_RATING";
    public static final String REVIEWS_AMOUNT = "REVIEWS_AMOUNT";

    private TextView avgRating, reviews_count;
    private RatingBar ratingBar;
    private float avg_rating = 0;
    private int reviews_amount = 0;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        avg_rating = getArguments().getFloat(AVG_RATING);
        reviews_amount = getArguments().getInt(REVIEWS_AMOUNT);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mini_reviews, container, false);
        ratingBar = view.findViewById(R.id.mini_rating_bar);
        avgRating = view.findViewById(R.id.avgRating);
        reviews_count = view.findViewById(R.id.reviews_count);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        avgRating.setText(String.valueOf(avg_rating));
        reviews_count.setText("(" + reviews_amount + " " + getResources().getQuantityString(R.plurals.reviews, reviews_amount) + ")");
        ratingBar.setRating(avg_rating);
    }
}