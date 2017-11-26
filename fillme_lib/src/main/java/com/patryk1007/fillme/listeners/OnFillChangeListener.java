package com.patryk1007.fillme.listeners;

public interface OnFillChangeListener {

    /**
     * A callback that notifies clients when the fill horizontal level has been changed.
     * @param fillPercent Current fill lever in percent (0-1)
     * @param fillPx Current fill lever in pixels
     */
    void onHorizontalValueChange(float fillPercent, int fillPx);

    /**
     * A callback that notifies clients when the fill vertical level has been changed.
     * @param fillPercent Current fill lever in percent (0-1)
     * @param fillPx Current fill lever in pixels
     */
    void onVerticalValueChange(float fillPercent, int fillPx);
}
