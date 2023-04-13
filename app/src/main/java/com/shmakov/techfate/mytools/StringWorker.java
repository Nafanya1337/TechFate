package com.shmakov.techfate.mytools;

import com.shmakov.techfate.entities.Product;

public class StringWorker {

    public static String makePriceString(int price) {
        StringBuilder price_str = new StringBuilder(Integer.toString(price));
        String str = price_str.reverse().toString();
        str = str.replaceAll("(.{3})", "$1 ");
        return new StringBuilder(str).reverse().toString() + " ₽";
    }

}
