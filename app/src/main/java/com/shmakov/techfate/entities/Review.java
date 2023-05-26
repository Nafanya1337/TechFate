package com.shmakov.techfate.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.shmakov.techfate.entities.User;

public class Review  implements Parcelable {
    private String user;
    private String text;
    private String date;
    private Float rating;

    public Review(String user, String text, String date, Float rating) {
        this.user = user;
        this.text = text;
        this.date = date;
        this.rating = rating;
    }

    protected Review(Parcel in) {
        user = in.readString();
        text = in.readString();
        date = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readFloat();
        }
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getUser() {
        return user;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(text);
        dest.writeString(date);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(rating);
        }
    }
}
