package com.shmakov.techfate.fragments.profile_editting_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shmakov.techfate.R;

public class ProfilePhotosFragment extends DialogFragment {

    public interface setNewPhoto{
        public void setNewPhoto(String photo);
    }

    setNewPhoto setNewPhoto;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setNewPhoto = (setNewPhoto) context;
    }

    ImageView usr_img1, usr_img2, usr_img3, usr_img4, usr_img5, usr_img6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_photos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usr_img1 = view.findViewById(R.id.usr_img1);
        usr_img2 = view.findViewById(R.id.usr_img2);
        usr_img3 = view.findViewById(R.id.usr_img3);
        usr_img4 = view.findViewById(R.id.usr_img4);
        usr_img5 = view.findViewById(R.id.usr_img5);
        usr_img6 = view.findViewById(R.id.usr_img6);
        usr_img1.setClipToOutline(true);
        usr_img2.setClipToOutline(true);
        usr_img3.setClipToOutline(true);
        usr_img4.setClipToOutline(true);
        usr_img5.setClipToOutline(true);
        usr_img6.setClipToOutline(true);

        usr_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewPhoto.setNewPhoto("ava1");
                dismiss();
            }
        });

        usr_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewPhoto.setNewPhoto("ava2");
                dismiss();
            }
        });

        usr_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewPhoto.setNewPhoto("ava3");
                dismiss();
            }
        });

        usr_img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewPhoto.setNewPhoto("ava4");
                dismiss();
            }
        });

        usr_img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewPhoto.setNewPhoto("ava5");
                dismiss();
            }
        });

        usr_img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewPhoto.setNewPhoto("ava6");
                dismiss();
            }
        });
    }
}