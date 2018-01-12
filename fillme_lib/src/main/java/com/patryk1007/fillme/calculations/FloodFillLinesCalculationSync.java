package com.patryk1007.fillme.calculations;

import android.graphics.Bitmap;

import com.patryk1007.fillme.listeners.OnFillLineCalculationListener;

public class FloodFillLinesCalculationSync implements LinesCalculation {

    @Override
    public void startCalculation(Bitmap image, int alphaLevel, OnFillLineCalculationListener listener) {
        listener.onCompleted(new AllShapesLinesCalculations().calculate(image, alphaLevel));
    }
}