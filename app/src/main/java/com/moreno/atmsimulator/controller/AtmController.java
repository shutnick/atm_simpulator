package com.moreno.atmsimulator.controller;

import android.app.Activity;

import com.moreno.atmsimulator.R;
import com.moreno.atmsimulator.db.MoneyData;
import com.moreno.atmsimulator.model.BanknoteItem;
import com.moreno.atmsimulator.view.warning.WarningCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created on 16.06.2015.
 */
public class AtmController {

    public static final int DEFAULT_AMOUNT = 10;
    private List<BanknoteItem> mMoney;
    private Activity mActivity;

    public AtmController(Activity activity) {
        mActivity = activity;
        fillAtm();
        mMoney = MoneyData.getInstance().getData();
    }

    public void subtractMoney(int amount) {
        List<Integer> requiredBanknotes = new ArrayList<>();
        int availableSum = 0;
        for (int i = mMoney.size() - 1; i >= 0; i--) {
            BanknoteItem item = mMoney.get(i);
            int banknoteAmount = item.amount();
            while (banknoteAmount > 0 && (availableSum + item.nominal() <= amount)) {
                availableSum += item.nominal();
                banknoteAmount--;
            }
            requiredBanknotes.add(0, item.amount() - banknoteAmount);
        }

        if (amount == availableSum) {
            pullMoney(requiredBanknotes);
        } else {
            showAtmError(amount, availableSum);
        }
    }

    private void pullMoney(List<Integer> extractedBanknotes) {
        for (int i = 0; i < mMoney.size(); i++) {
            BanknoteItem item = mMoney.get(i);
            int decrease = extractedBanknotes.get(i);
            if (decrease != 0) {
                item.decreaseAmount(decrease);
                ((MoneyObserver) mActivity).onChanged(i);
            }
        }
    }

    private void showAtmError(int amount, int available) {
        String title = mActivity.getResources().getString(R.string.warning_title_atm_error);
        String messageTemplate = mActivity.getResources().getString(R.string.warning_message_atm_error);
        String message = String.format(messageTemplate, amount, available);
        ((WarningCallback) mActivity).onError(title, message);
    }

    public void fillAtm() {
        List<BanknoteItem> banknotes = MoneyData.getInstance().getData();
        banknotes.clear();
        banknotes.add(new BanknoteItem(1, DEFAULT_AMOUNT));
        banknotes.add(new BanknoteItem(50, DEFAULT_AMOUNT));
        banknotes.add(new BanknoteItem(10, DEFAULT_AMOUNT));
        banknotes.add(new BanknoteItem(5, DEFAULT_AMOUNT));
        banknotes.add(new BanknoteItem(20, DEFAULT_AMOUNT));
        banknotes.add(new BanknoteItem(100, DEFAULT_AMOUNT));
        banknotes.add(new BanknoteItem(2, DEFAULT_AMOUNT));
        Collections.sort(banknotes);
    }
}
