package com.patryk1007.fillme.calculations;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.patryk1007.fillme.listeners.OnFillLineCalculationListener;
import com.patryk1007.fillme.models.FillLine;

import java.util.ArrayList;

public class FloodFillLinesCalculationAsync extends AsyncTask<Bitmap, Void, ArrayList<FillLine>> implements LinesCalculation {

    private OnFillLineCalculationListener asyncListener;
    private int alphaLevel;

    @Override
    public void startCalculation(Bitmap image, int alphaLevel, OnFillLineCalculationListener listener) {
        asyncListener = listener;
        this.alphaLevel = alphaLevel;
        if (image != null) {
            this.execute(image);
        }
    }


    @Override
    protected ArrayList<FillLine> doInBackground(Bitmap... images) {
        Bitmap image = images[0];
        return new AllShapesLinesCalculations().calculate(image, alphaLevel);
    }

    @Override
    protected void onPostExecute(ArrayList<FillLine> result) {
        asyncListener.onCompleted(result);
    }
}