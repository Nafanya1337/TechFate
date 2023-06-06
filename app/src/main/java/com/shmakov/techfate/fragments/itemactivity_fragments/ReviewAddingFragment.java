package com.shmakov.techfate.fragments.itemactivity_fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.shmakov.techfate.MainActivity;
import com.shmakov.techfate.R;
import com.shmakov.techfate.entities.Review;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ReviewAddingFragment extends BottomSheetDialogFragment {

    public interface makeReview{
        public void makeReview(Review review);
    }

    makeReview makeReview;

    TextInputEditText addingReviewNameEditText, addingReviewTextEditText;

    Button saveReviewBtn;

    RatingBar addingReviewRatingBar;

    public ReviewAddingFragment(com.shmakov.techfate.ReviewsFragment reviewsFragment) {
        makeReview = (makeReview) reviewsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review_adding, container, false);
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addingReviewRatingBar = view.findViewById(R.id.addingReviewRatingBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            addingReviewRatingBar.setMin(1);
        }
        addingReviewNameEditText = view.findViewById(R.id.addingReviewNameEditText);
        addingReviewNameEditText.setText(MainActivity.user.getName());
        addingReviewTextEditText = view.findViewById(R.id.addingReviewTextEditText);
        saveReviewBtn = view.findViewById(R.id.saveReviewBtn);
        LayerDrawable stars = (LayerDrawable) addingReviewRatingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.hint_mini), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onResume() {
        super.onResume();
        saveReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = addingReviewNameEditText.getText().toString();
                String text = addingReviewTextEditText.getText().toString();
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                String date = dateFormat.format(currentDate);
                Float rating = addingReviewRatingBar.getRating();
                makeReview.makeReview(new Review(user, text, date, rating));
            }
        });
    }
}