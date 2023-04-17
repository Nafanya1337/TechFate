package com.shmakov.techfate.mytools;

import com.shmakov.techfate.entities.Category;
import com.shmakov.techfate.entities.Product;

public class StringWorker {

    public static String makePriceString(int price) {
        StringBuilder price_str = new StringBuilder(Integer.toString(price));
        String str = price_str.reverse().toString();
        str = str.replaceAll("(.{3})", "$1 ");
        price_str = null;
        return new StringBuilder(str).reverse().toString() + " ₽";
    }

    public static String makeProductName(String mark, String name) {
        StringBuilder mark_and_name = new StringBuilder(mark + " " + name);
        if (mark_and_name.length() >= 32) {
            int i = mark_and_name.lastIndexOf(" ", 25);
            if (i == -1)
                i = 22;
            mark_and_name = mark_and_name.delete(i, mark_and_name.length());
            mark_and_name.append("...");
        }
        return mark_and_name.toString();
    }

    public static String pluralMaker(String category) {
        switch (category){
            case Category.SMARTPHONE_NAME_CATEGORY:
                return "Смартфон";
            case Category.CONSOLES_NAME_CATEGORY:
                return "Игровая приставка";
            case Category.TABLETS_NAME_CATEGORY:
                return"Планшет";
            case Category.MONITORS_NAME_CATEGORY:
                return "Монитор";
            case Category.LAPTOPS_NAME_CATEGORY:
                return "Ноутбук";
        }
        return category;
    }
}
