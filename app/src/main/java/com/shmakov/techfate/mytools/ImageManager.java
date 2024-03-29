package com.shmakov.techfate.mytools;

import static com.shmakov.techfate.entities.inner.Category.CONSOLES_NAME_CATEGORY;
import static com.shmakov.techfate.entities.inner.Category.HEADPHONES_NAME_CATEGORY;
import static com.shmakov.techfate.entities.inner.Category.LAPTOPS_NAME_CATEGORY;
import static com.shmakov.techfate.entities.inner.Category.MONITORS_NAME_CATEGORY;
import static com.shmakov.techfate.entities.inner.Category.SMARTPHONE_NAME_CATEGORY;
import static com.shmakov.techfate.entities.inner.Category.TABLETS_NAME_CATEGORY;
import static com.shmakov.techfate.entities.inner.Category.WATCHES_NAME_CATEGORY;

import com.shmakov.techfate.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageManager {
    public static int findCategoryMiniIMG(String name) {
        switch (name) {
            case SMARTPHONE_NAME_CATEGORY:
                return R.drawable.smartphones_img;
            case WATCHES_NAME_CATEGORY:
                return R.drawable.watches_img;
            case TABLETS_NAME_CATEGORY:
                return R.drawable.tablets_img;
            case HEADPHONES_NAME_CATEGORY:
                return R.drawable.headphones_img;
            case CONSOLES_NAME_CATEGORY:
                return R.drawable.consoles_img;
            case LAPTOPS_NAME_CATEGORY:
                return R.drawable.laptop_img;
            case MONITORS_NAME_CATEGORY:
                return R.drawable.monitors_img;
        }
        return 0;
    }

    public static int findCategoryBackgroundIMG(String name) {
        switch (name) {
            case SMARTPHONE_NAME_CATEGORY:
                return R.drawable.category_smartphones_back;
            case WATCHES_NAME_CATEGORY:
                return R.drawable.category_watches_back;
            case TABLETS_NAME_CATEGORY:
                return R.drawable.category_tablets_back;
            case HEADPHONES_NAME_CATEGORY:
                return R.drawable.category_headphones_back;
            case CONSOLES_NAME_CATEGORY:
                return R.drawable.category_consoles_back;
            case LAPTOPS_NAME_CATEGORY:
                return R.drawable.category_laptops_back;
            case MONITORS_NAME_CATEGORY:
                return R.drawable.category_monitors_back;
        }
        return 0;
    }

    public static int findDeliveryIMG(String name) {
        Pattern pattern = Pattern.compile("\\s\\((.+)");
        Matcher matcher = pattern.matcher(name);
        do {
            name = matcher.replaceAll("");
            matcher = pattern.matcher(name);
        } while (matcher.find());
        switch (name) {
            case "Курьером":
                return R.drawable.baseline_face_24;
            case "Почтой":
                return R.drawable.pochta_rossii;
            case "Курьерской службой СДЭК":
                return R.drawable.sdek;
        }
        return 0;
    }

    public static int findPaymentIMG(String name) {
        switch (name) {
            case "Через СБП":
                return R.drawable.sbp;
            case "Картой":
                return R.drawable.baseline_credit_card_24;
            case "QIWI Wallet":
                return R.drawable.qiwi;
            case "ЮMoney":
                return R.drawable.umoney;
        }
        if (name.contains("Картой"))
            return R.drawable.baseline_credit_card_24;
        return 0;
    }
}
