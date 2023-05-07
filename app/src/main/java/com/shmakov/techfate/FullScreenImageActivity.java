package com.shmakov.techfate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.shmakov.techfate.adapters.ImageAdapter;

public class FullScreenImageActivity extends AppCompatActivity {

    public static final String ARRAY_TAG = "CURRENT_ARRAY";
    public static final String POSITION_TAG = "CURRENT IMAGE";

    private ImageAdapter imageAdapter;
    private ViewPager full_image_container;
    private int[] imgs;
    private int img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        imgs = getIntent().getIntArrayExtra(ARRAY_TAG);
        img = getIntent().getIntExtra(POSITION_TAG, 0);
        full_image_container = findViewById(R.id.full_image_container);
        imageAdapter = new ImageAdapter(this, imgs);
        full_image_container.setAdapter(imageAdapter);
        full_image_container.postDelayed(new Runnable() {
            @Override
            public void run() {
                full_image_container.setCurrentItem(img);
            }
        }, 100);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        full_image_container.destroyDrawingCache();
        imageAdapter = null;
        full_image_container = null;
    }
}