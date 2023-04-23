package com.shmakov.techfate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shmakov.techfate.adapters.FullReviewAdapter;
import com.shmakov.techfate.entities.Review;

import java.util.ArrayList;


public class ReviewsFragment extends Fragment {

    private int last_index = 3;

    public void showMore(){
        last_index += Math.min(reviews.size(), 3);
        fullReviewAdapter = new FullReviewAdapter(getContext(), new ArrayList<Review>(reviews.subList(0, last_index)));
        fullReviewAdapter.addNewReviews(new ArrayList<Review>(reviews.subList(0, last_index)));
        fullReviewAdapter.notifyDataSetChanged();
    }

    public static final String REVIEWS_TAG = "REVIEWS";

    private FullReviewAdapter fullReviewAdapter;
    private ArrayList<Review> reviews;
    private RecyclerView reviews_recycler;

    private ProgressBar progress_1, progress_2, progress_3, progress_4, progress_5;

    private TextView percent_1, percent_2, percent_3, percent_4, percent_5;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviews = getArguments().getParcelableArrayList(REVIEWS_TAG);
        last_index = Math.min(reviews.size(), 3);
        reviews = new ArrayList<Review>(reviews.subList(0, last_index));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        reviews_recycler = view.findViewById(R.id.reviews_recycler);
        reviews_recycler.setNestedScrollingEnabled(false);
        progress_1 = view.findViewById(R.id.progress_1);
        progress_2 = view.findViewById(R.id.progress_2);
        progress_3 = view.findViewById(R.id.progress_3);
        progress_4 = view.findViewById(R.id.progress_4);
        progress_5 = view.findViewById(R.id.progress_5);
        percent_1 = view.findViewById(R.id.percent_1);
        percent_2 = view.findViewById(R.id.percent_2);
        percent_3 = view.findViewById(R.id.percent_3);
        percent_4 = view.findViewById(R.id.percent_4);
        percent_5 = view.findViewById(R.id.percent_5);
        progress_1.setProgress(makePercent(1));
        progress_2.setProgress(makePercent(2));
        progress_3.setProgress(makePercent(3));
        progress_4.setProgress(makePercent(4));
        progress_5.setProgress(makePercent(5));
        percent_1.setText(String.valueOf(makePercent(1)) + "%");
        percent_2.setText(String.valueOf(makePercent(2)) + "%");
        percent_3.setText(String.valueOf(makePercent(3)) + "%");
        percent_4.setText(String.valueOf(makePercent(4)) + "%");
        percent_5.setText(String.valueOf(makePercent(5)) + "%");
        return view;
    }

    public int makePercent(int i){
        int sum = 0;
        for (Review review: reviews) {
            int rating = (Math.round(review.getRating()));
            if (rating == i)
                sum ++;
        }
        return (int)(((float)sum / reviews.size()) * 100);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fullReviewAdapter = new FullReviewAdapter(getContext(), reviews);
        reviews_recycler.setAdapter(fullReviewAdapter);
    }
}