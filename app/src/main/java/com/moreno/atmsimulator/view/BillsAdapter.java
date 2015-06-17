package com.moreno.atmsimulator.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.moreno.atmsimulator.R;
import com.moreno.atmsimulator.controller.MoneyObserver;
import com.moreno.atmsimulator.db.MoneyData;
import com.moreno.atmsimulator.model.BanknoteItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created on 16.06.2015.
 */
public class BillsAdapter extends BaseAdapter {
    public static final String MONEY_SIGN = "$";
    private List<BanknoteItem> mBanknotes;

    public BillsAdapter() {
        mBanknotes = MoneyData.getInstance().getData();
    }

    private void fillBanknotes(Collection<BanknoteItem> banknotes) {
        Set<BanknoteItem> set = new TreeSet<>(banknotes);
        mBanknotes = new ArrayList<>(set);
    }

    @Override
    public int getCount() {
        return mBanknotes.size();
    }

    @Override
    public Object getItem(int position) {
        return mBanknotes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = initNewView(parent);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        BanknoteItem item = mBanknotes.get(position);
        holder.nominalView.setText(MONEY_SIGN + item.nominal());
        holder.amountView.setText(String.valueOf(item.amount()));

        return convertView;
    }

    private View initNewView(ViewGroup parent) {
        View newView = LayoutInflater.from(parent.getContext()).inflate(R.layout.banknote_item, parent, false);
        TextView nominalView = (TextView) newView.findViewById(R.id.nominal_view);
        TextView amountView = (TextView) newView.findViewById(R.id.amount_view);
        ViewHolder holder = new ViewHolder();
        holder.nominalView = nominalView;
        holder.amountView = amountView;
        newView.setTag(holder);
        return newView;
    }

    public void swapData(Collection<BanknoteItem> newData) {
        fillBanknotes(newData);
        notifyDataSetChanged();

    }

    static class ViewHolder {
        TextView nominalView;
        TextView amountView;
    }
}
