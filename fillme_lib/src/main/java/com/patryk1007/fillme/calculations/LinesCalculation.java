package com.patryk1007.fillme.calculations;

import android.graphics.Bitmap;

import com.patryk1007.fillme.listeners.OnFillLineCalculationListener;

public interface LinesCalculation {

    void startCalculation(Bitmap image, int alphaLevel, OnFillLineCalculationListener listener);

}
