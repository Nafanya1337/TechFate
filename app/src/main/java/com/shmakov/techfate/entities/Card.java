package com.shmakov.techfate.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.shmakov.techfate.R;

public class Card implements Parcelable {

    private String cardHolder, cardNum, cardDate, cardType;

    private int cardImg;


    public Card(String cardHolder, String cardNum, String cardDate, String cardType) {
        this.cardDate = cardDate;
        this.cardNum = cardNum;
        this.cardHolder = cardHolder;
        this.cardType = cardType;
        switch (cardNum.charAt(0)){
            case 2:
                this.cardImg = R.drawable.card_mir;
                break;
            default:
                this.cardImg = 0;
        }
    }


    protected Card(Parcel in) {
        cardHolder = in.readString();
        cardNum = in.readString();
        cardDate = in.readString();
        cardType = in.readString();
        cardImg = in.readInt();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setCardImg(int cardImg) {
        this.cardImg = cardImg;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getCardImg() {
        return cardImg;
    }

    public String getCardDate() {
        return cardDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getCardNum() {
        return cardNum;
    }

    public String getCardType() {
        return cardType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(cardHolder);
        dest.writeString(cardNum);
        dest.writeString(cardDate);
        dest.writeString(cardType);
        dest.writeInt(cardImg);
    }
}
