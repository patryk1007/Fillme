package com.patryk1007.fillme.calculations;

import android.graphics.Bitmap;
import android.util.Log;

import com.patryk1007.fillme.models.FillLine;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Queue;

public class AllShapesLinesCalculations {


    private native boolean[] invokeNativeFunction(int[] points, int width, int height, int alphaLevel);

    public ArrayList<FillLine> calculate(Bitmap image, int alphaLevel){
        long startTime = Calendar.getInstance().getTimeInMillis();
        int width = image.getWidth();
        int height = image.getHeight();

        int pointsSize = width * height;
        int[] pixels = new int[pointsSize];
        image.getPixels(pixels, 0, width, 0, 0, width, height);

        Queue<Integer> pointQueue = new ArrayDeque<>();
        pointQueue.add(0);


        long startCalculation = Calendar.getInstance().getTimeInMillis();

        System.loadLibrary("native-lib");
        boolean[] filledPoints = invokeNativeFunction(pixels, width, height, alphaLevel);

        long endCalculation = Calendar.getInstance().getTimeInMillis();

        ArrayList<FillLine> points = new ArrayList<>();
        boolean lastState = false;
        boolean state;
        FillLine fillLine = new FillLine();
        int pointsLength = filledPoints.length;
        for (int x = 0; x < pointsLength; x++) {
            state = filledPoints[x];
            if (lastState != state) {
                if (!state) {
                    fillLine.setStartX(x % width);
                    fillLine.setStartY(x / width);
                } else {
                    if (fillLine.getStartX() != -1) {
                        fillLine.setEndX(x % width);
                        fillLine.setEndY(x / width);
                        points.add(fillLine);
                        fillLine = new FillLine();
                    }
                }
                lastState = state;
            }
        }
        Log.d("ASYNCCALCULATION", " Total: " + (Calendar.getInstance().getTimeInMillis() - startTime)
                + " Calculation: " + (endCalculation - startCalculation)
                + " Points: " + (Calendar.getInstance().getTimeInMillis() - endCalculation)
                + " " + points.size());
        return points;
    }

}
