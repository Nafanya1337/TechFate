package com.shmakov.techfate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.shmakov.techfate.database.UserDatabaseHelper;
import com.shmakov.techfate.entities.User;

import java.io.IOException;

public class ChangeProfileInfo extends AppCompatActivity implements ProfilePhotosFragment.setNewPhoto {

    User user = MainActivity.user;

    public static int img;

    TextInputEditText emailTextInput, FIOtextInput, PasswordTextInput;

    ImageView userImg;

    UserDatabaseHelper userDatabaseHelper;

    Button btnSaveProfileInfo, changePhotonBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile_info);
        emailTextInput = findViewById(R.id.emailTextInput);
        FIOtextInput = findViewById(R.id.FIOtextInput);
        PasswordTextInput = findViewById(R.id.PasswordTextInput);
        userImg = findViewById(R.id.userImg);
        btnSaveProfileInfo = findViewById(R.id.btnSaveProfileInfo);
        changePhotonBtn = findViewById(R.id.changePhotonBtn);
        userDatabaseHelper = new UserDatabaseHelper(this);
        try {
            userDatabaseHelper.createDataBase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }
        img = user.getImg();
    }


    @Override
    protected void onResume() {
        super.onResume();
        emailTextInput.setText(user.getEmailAdress());
        FIOtextInput.setText(user.getName());
        PasswordTextInput.setText(user.getPassword());

        changePhotonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfilePhotosFragment profilePhotosFragment = new ProfilePhotosFragment();
                profilePhotosFragment.show(getSupportFragmentManager(), "photos");
            }
        });

        if (img != user.getImg()) {
            user.setImg(img);
        }

        userImg.setImageResource(user.getImg());
        userImg.setClipToOutline(true);

        btnSaveProfileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(FIOtextInput.getText().toString());
                user.setEmailAdress(emailTextInput.getText().toString());
                user.setPassword(PasswordTextInput.getText().toString());
                userDatabaseHelper.openDataBase();
                userDatabaseHelper.updateProfileInfo(user);
                userDatabaseHelper.close();
                MainActivity.user.setPassword(user.getPassword());
                MainActivity.user.setImg(user.getImg());
                MainActivity.user.setName(user.getName());
                MainActivity.user.setEmailAdress(user.getEmailAdress());
                finish();
            }
        });
    }

    @Override
    public void setNewPhoto(String photo) {
        int id = getResources().getIdentifier(photo, "drawable", getPackageName());
        user.setImg(id);
        userImg.setImageResource(id);
        userImg.setClipToOutline(true);
    }
}