package com.moreno.atmsimulator.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.moreno.atmsimulator.R;
import com.moreno.atmsimulator.controller.AtmController;
import com.moreno.atmsimulator.controller.MoneyObserver;
import com.moreno.atmsimulator.db.MoneyData;
import com.moreno.atmsimulator.model.BanknoteItem;
import com.moreno.atmsimulator.view.warning.WarningCallback;
import com.moreno.atmsimulator.view.warning.WarningDialog;

import java.util.List;

public class AtmActivity extends AppCompatActivity implements MoneyObserver, WarningCallback{
    private AtmController mController;
    private EditText mInput;
    private ListView mNominalsList;
    private BillsAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm);
        mInput = (EditText) findViewById(R.id.input_console);
        mListAdapter = new BillsAdapter();
        mNominalsList = (ListView) findViewById(R.id.bills_list);
        mNominalsList.setAdapter(mListAdapter);
        mController = new AtmController(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_atm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.fill_atm) {
            mController.fillAtm();
            mListAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getMoney(View acceptButton) {
        String input = mInput.getText().toString();
        int requestedMoney;
        try {
            requestedMoney = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            String titleTemplate = getResources().getString(R.string.warning_title_input_error);
            String title = String.format(titleTemplate, input);
            String message = getResources().getString(R.string.warning_message_not_decimal);
            try {
                double sum = Double.parseDouble(input);
            } catch (NumberFormatException e1) {
                message = getResources().getString(R.string.warning_message_not_number);
            }
            onError(title, message);
            return;
        }
        mController.subtractMoney(requestedMoney);
    }

    @Override
    public void onChanged(int index) {
        View listItem = mNominalsList.getChildAt(index);
        BillsAdapter.ViewHolder holder = (BillsAdapter.ViewHolder) listItem.getTag();
        List<BanknoteItem> data = MoneyData.getInstance().getData();
        holder.amountView.setText(String.valueOf(data.get(index).amount()));
    }

    @Override
    public void onError(String title, String message) {
        WarningDialog dialog = WarningDialog.createDialog(title, message);
        dialog.show(getFragmentManager(), WarningDialog.WARNING_FRAGMENT_TAG);
    }
}
