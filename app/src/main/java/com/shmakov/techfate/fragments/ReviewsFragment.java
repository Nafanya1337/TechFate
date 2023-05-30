package com.shmakov.techfate;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shmakov.techfate.adapters.FullReviewAdapter;
import com.shmakov.techfate.entities.Review;
import com.shmakov.techfate.entities.inner.Product;
import com.shmakov.techfate.mytools.ProgressBarAnimation;

import java.util.ArrayList;


public class ReviewsFragment extends Fragment implements ReviewAddingFragment.makeReview {

    private int last_index = 3;

    @Override
    public void makeReview(Review review) {
        addReview(review);
    }

    public interface addReview{
        public void addReview(Review review);
    }

    addReview addReview;

    public static final String REVIEWS_TAG = "REVIEWS";

    private FullReviewAdapter fullReviewAdapter;
    private ArrayList<Review> reviews;
    private RecyclerView reviews_recycler;

    private Button btn_showMore, addReviewBtn;

    private ProgressBar progress_1, progress_2, progress_3, progress_4, progress_5;

    private TextView percent_1, percent_2, percent_3, percent_4, percent_5;

    public void showMore(View view) {
        if (((Button) view).getText().equals("Показать больше")) {
            fullReviewAdapter.addNewReviews(new ArrayList<Review>(reviews.subList(last_index, last_index + Math.min(reviews.size() - last_index, 3))));
            last_index += Math.min(reviews.size() - last_index, 3);
            fullReviewAdapter.notifyDataSetChanged();
            if (last_index == reviews.size()) {
                ((Button) view).setText("Скрыть комментарии");
            }
            reviews_recycler.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT * last_index));
        }
        else {
            ((Button) view).setText("Показать больше");
            last_index = Math.min(reviews.size(), 3);
            fullReviewAdapter.setReviews(new ArrayList<Review>(reviews.subList(0, last_index )));
            fullReviewAdapter.notifyDataSetChanged();
            reviews_recycler.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        addReview = (addReview) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviews = getArguments().getParcelableArrayList(REVIEWS_TAG);
        last_index = Math.min(reviews.size(), 3);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        reviews_recycler = view.findViewById(R.id.reviews_recycler);
        reviews_recycler.setNestedScrollingEnabled(false);
        btn_showMore = view.findViewById(R.id.btn_showMore);
        if (reviews.size() <= 3)
            btn_showMore.setVisibility(View.GONE);
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

        addReviewBtn = view.findViewById(R.id.addReviewBtn);

        registerForContextMenu(progress_1);
        registerForContextMenu(progress_2);
        registerForContextMenu(progress_3);
        registerForContextMenu(progress_4);
        registerForContextMenu(progress_5);
        percent_1.setText(String.valueOf(makePercent(1)) + "%");
        percent_2.setText(String.valueOf(makePercent(2)) + "%");
        percent_3.setText(String.valueOf(makePercent(3)) + "%");
        percent_4.setText(String.valueOf(makePercent(4)) + "%");
        percent_5.setText(String.valueOf(makePercent(5)) + "%");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ProgressBarAnimation anim = new ProgressBarAnimation(progress_1, 0, makePercent(1));
        anim.setDuration(500);
        progress_1.startAnimation(anim);
        anim = new ProgressBarAnimation(progress_2, 0, makePercent(2));
        anim.setDuration(500);
        progress_2.startAnimation(anim);
        anim = new ProgressBarAnimation(progress_3, 0, makePercent(3));
        anim.setDuration(500);
        progress_3.startAnimation(anim);
        anim = new ProgressBarAnimation(progress_4, 0, makePercent(4));
        anim.setDuration(500);
        progress_4.startAnimation(anim);
        anim = new ProgressBarAnimation(progress_5, 0, makePercent(5));
        anim.setDuration(500);
        progress_5.startAnimation(anim);
    }

    public int makePercent(int i){
        int sum = 0;
        for (Review review: reviews) {
            int rating = (Math.round(review.getRating()));
            if (rating == i)
                sum ++;
        }
        return Math.round((((float)sum / reviews.size()) * 100));
    }

    @Override
    public void onResume() {
        super.onResume();
        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewAddingFragment reviewAddingFragment = new ReviewAddingFragment();
                reviewAddingFragment.show(getChildFragmentManager(), "addFragment");
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fullReviewAdapter = new FullReviewAdapter(getContext(), new ArrayList<Review>(reviews.subList(0, last_index)));
        reviews_recycler.setAdapter(fullReviewAdapter);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

    }
}