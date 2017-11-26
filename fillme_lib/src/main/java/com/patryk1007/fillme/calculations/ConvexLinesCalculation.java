package com.patryk1007.fillme.calculations;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.patryk1007.fillme.listeners.OnFillLineCalculationListener;
import com.patryk1007.fillme.models.FillLine;

import java.util.ArrayList;

public class ConvexLinesCalculation implements LinesCalculation {

    private OnFillLineCalculationListener listener;
    private int alphaLevel;

    @Override
    public void startCalculation(Bitmap image, int alphaLevel, OnFillLineCalculationListener listener) {
        this.listener = listener;
        this.alphaLevel = alphaLevel;
        if(image!=null) {
            calculateConvexFigure(image);
        }
    }

    private void calculateConvexFigure(Bitmap bitmap) {
        boolean line;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        ArrayList<FillLine> fillLines = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            line = false;
            FillLine fillLine = new FillLine();
            for (int x = 0; x < width; x++) {
                int currentPixel = pixels[x + width * y];
                boolean currentPix = Color.alpha(currentPixel) > alphaLevel;
                if (line != currentPix) {
                    if (currentPix) {
                        if (fillLine.getStartX() == -1) {
                            fillLine.setStartX(x);
                            fillLine.setStartY(y);
                        } else {
                            fillLine.setEndX(x);
                            fillLine.setEndY(y);
                        }
                    }
                }
                line = currentPix;
            }
            if (fillLine.getEndX() != -1) {
                fillLines.add(fillLine);
            }
        }
        listener.onCompleted(fillLines);
    }

}