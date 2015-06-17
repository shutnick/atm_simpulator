package com.moreno.atmsimulator.view.warning;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created on 16.06.2015.
 */
public class WarningDialog extends DialogFragment {
    public static final String TITLE_KEY = "title";
    public static final String MESSAGE_KEY = "message";
    public static final String LOG_TAG = "warning dialog";
    public static final String WARNING_FRAGMENT_TAG = "warning";

    /**
     * Create dialog with appropriate data
     * @param title   Dialog title
     * @param message   Dialog message
     * @return  Prepared dialog
     */
    public static WarningDialog createDialog(String title, String message) {
        WarningDialog dialog = new WarningDialog();
        Bundle args = new Bundle();
        args.putString(TITLE_KEY, title);
        args.putString(MESSAGE_KEY, message);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        AlertDialog dialog = new AlertDialog
                .Builder(getActivity())
                .setTitle(args.getString(TITLE_KEY))
                .setMessage(args.getString(MESSAGE_KEY))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).create();
        return dialog;
    }

/*    @Override
    public int show(FragmentTransaction transaction, String tag) {
        Fragment warning = getFragmentManager().findFragmentByTag(WARNING_FRAGMENT_TAG);
        if (warning != null) {
            transaction.remove(warning);
        }

        return super.show(transaction, tag);
    }*/
}
