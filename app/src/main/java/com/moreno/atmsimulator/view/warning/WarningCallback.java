package com.moreno.atmsimulator.view.warning;

/**
 * Created on 17.06.2015.
 */
public interface WarningCallback {
    /**
     * Called to show warning
     * @param title Dialog title
     * @param message   Dialog message
     */
    void onError(String title, String message);
}
