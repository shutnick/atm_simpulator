package com.moreno.atmsimulator.model;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created on 16.06.2015.
 */
public class BanknoteItem implements Comparable<BanknoteItem>{
    private int mNominal;
    private int mAmount;

    public BanknoteItem(int nominal, int amount) {
        mNominal = nominal;
        mAmount = amount;
    }

    public void decreaseAmount(int decreaseAmount) {
        mAmount -= decreaseAmount;
        Log.i("BanknoteItem", "Nominal " + mNominal + " decreased by " + decreaseAmount);
    }

    public int nominal() {
        return mNominal;
    }

    public int amount() {
        return mAmount;
    }

    @Override
    public int compareTo(@NonNull BanknoteItem another) {
        return mNominal - another.nominal();
    }
}
