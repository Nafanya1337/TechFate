package com.shmakov.techfate.mytools;

import com.shmakov.techfate.entities.Product;

import java.util.Comparator;

public class WatchesComparator implements Comparator<Product> {
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
