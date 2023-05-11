package com.shmakov.techfate.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.shmakov.techfate.R;

public class Card implements Parcelable {

    private String cardHolder, cardNum, cardDate, cardType, CVC;

    private int cardImg;


    public Card(String cardHolder, String cardNum, String cardDate, String cardType, String CVC) {
        this.cardDate = cardDate;
        this.cardNum = cardNum;
        this.cardHolder = cardHolder;
        this.cardType = cardType;
        this.CVC = CVC;
        this.cardImg = choosePaymentSystem(cardNum.charAt(0));
    }


    public String getCVC() {
        return CVC;
    }

    public void setCVC(String CVC) {
        this.CVC = CVC;
    }

    protected Card(Parcel in) {
        cardHolder = in.readString();
        cardNum = in.readString();
        cardDate = in.readString();
        cardType = in.readString();
        cardImg = in.readInt();
        CVC = in.readString();
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
        this.cardImg = choosePaymentSystem(cardNum.charAt(0));
    }

    public static int choosePaymentSystem(char a) {
        switch (a) {
            case '2':
                return R.drawable.card_mir;
            case '3':
                return R.drawable.american_express;
            case '4':
                return R.drawable.visa;
            case '5':
                return R.drawable.mastercard;
        }
        return 0;
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
        dest.writeString(CVC);
    }
}
