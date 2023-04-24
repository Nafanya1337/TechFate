package com.shmakov.techfate.mytools;

import com.shmakov.techfate.entities.inner.Product;

import java.util.Comparator;

public class MyComparator {

    public static class AmountOfWatches implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            int amount1 = o1.getAmountOfWatches();
            int amount2 = o2.getAmountOfWatches();
            if (amount1 < amount2) {
                return 1;
            } else if (amount1 > amount2) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public static class CostMinToHigh implements Comparator<Product> {

        @Override
        public int compare(Product o1, Product o2) {
            int amount1 = o1.getCost();
            int amount2 = o2.getCost();
            if (amount1 < amount2) {
                return -1;
            } else if (amount1 > amount2) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static class CostMaxToMin implements Comparator<Product> {

        @Override
        public int compare(Product o1, Product o2) {
            int amount1 = o1.getCost();
            int amount2 = o2.getCost();
            if (amount1 < amount2) {
                return 1;
            } else if (amount1 > amount2) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public static class SortByRating implements Comparator<Product> {

        @Override
        public int compare(Product o1, Product o2) {
            float amount1 = o1.getAvgReviewsRating();
            float amount2 = o2.getAvgReviewsRating();
            if (amount1 < amount2) {
                return 1;
            } else if (amount1 > amount2) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}
