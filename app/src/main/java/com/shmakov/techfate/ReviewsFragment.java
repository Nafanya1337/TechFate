package com.shmakov.techfate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shmakov.techfate.adapters.FullReviewAdapter;
import com.shmakov.techfate.entities.Review;

import java.util.ArrayList;


public class ReviewsFragment extends Fragment {

    public static final String REVIEWS_TAG = "REVIEWS";

    private FullReviewAdapter fullReviewAdapter;
    private ArrayList<Review> reviews;
    private RecyclerView reviews_recycler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviews = getArguments().getParcelableArrayList(REVIEWS_TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        reviews_recycler = view.findViewById(R.id.reviews_recycler);
        reviews_recycler.setNestedScrollingEnabled(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fullReviewAdapter = new FullReviewAdapter(getContext(), reviews);
        reviews_recycler.setAdapter(fullReviewAdapter);
    }
}