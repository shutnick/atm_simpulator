package com.moreno.atmsimulator.db;

import com.moreno.atmsimulator.model.BanknoteItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16.06.2015.
 */
public class MoneyData {
    private List<BanknoteItem> mMoney;
    private static MoneyData sInstance;

    private MoneyData() {
        mMoney = new ArrayList<>();
    }

    public static MoneyData getInstance() {
        if (sInstance == null) {
            sInstance = new MoneyData();
        }
        return sInstance;
    }

    public List<BanknoteItem> getData() {
        return mMoney;
    }
}
