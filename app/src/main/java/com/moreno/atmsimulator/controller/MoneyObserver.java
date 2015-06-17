package com.moreno.atmsimulator.controller;

/**
 * Created on 16.06.2015.
 */
public interface MoneyObserver {
    /**
     * Called when amount of one banknote changed
     * @param index Index of banknote in db
     */
    void onChanged(int index);
}
