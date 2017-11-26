package com.patryk1007.fillme.calculations

import android.graphics.Bitmap
import android.graphics.Color
import android.os.AsyncTask
import android.util.Log
import com.patryk1007.fillme.listeners.OnFillLineCalculationListener
import com.patryk1007.fillme.models.FillLine
import java.util.*

class FloodFillLinesAsyncKotlin : AsyncTask<Bitmap, Void, ArrayList<FillLine>>(), LinesCalculation {

    internal val TYPE_LINE = true

    private var asyncListener: OnFillLineCalculationListener? = null
    private var alphaLevel: Int = 0

    override fun startCalculation(image: Bitmap?, alphaLevel: Int, listener: OnFillLineCalculationListener?) {
        asyncListener = listener
        this.alphaLevel = alphaLevel
        if (image != null) {
            this.execute(image)
        }
    }

    override fun doInBackground(vararg images: Bitmap?): ArrayList<FillLine> {
        val startTime = Calendar.getInstance().timeInMillis
        val image = images[0]
        val width = image?.width ?: 0
        val height = image?.height ?: 0

        val pixels = IntArray(width * height)
        val filledPoints = BooleanArray(width * height)
        image?.getPixels(pixels, 0, width, 0, 0, width, height)

        val pointQueue = ArrayDeque<Int>()
        pointQueue.add(0)

        var currentPixIndex: Int
        val pointsSize = pixels.size
        var newPixIndex: Int
        while (!pointQueue.isEmpty()) {
            currentPixIndex = pointQueue.poll()

            newPixIndex = currentPixIndex + 1
            if (newPixIndex < pointsSize && !filledPoints[newPixIndex]) {
                filledPoints[newPixIndex] = TYPE_LINE
                if (Color.alpha(pixels[newPixIndex]) <= alphaLevel || currentPixIndex < width) {
                    pointQueue.add(newPixIndex)
                }
            }

            newPixIndex = currentPixIndex - 1
            if (newPixIndex > 0 && !filledPoints[newPixIndex]) {
                filledPoints[newPixIndex] = TYPE_LINE
                if (Color.alpha(pixels[newPixIndex]) <= alphaLevel) {
                    pointQueue.add(newPixIndex)
                }
            }

            newPixIndex = currentPixIndex + width
            if (newPixIndex < pointsSize && !filledPoints[newPixIndex]) {
                filledPoints[newPixIndex] = TYPE_LINE
                if (Color.alpha(pixels[newPixIndex]) <= alphaLevel || currentPixIndex % width == 0) {
                    pointQueue.add(newPixIndex)
                }
            }

            newPixIndex = currentPixIndex - width
            if (newPixIndex > 0 && !filledPoints[newPixIndex]) {
                filledPoints[newPixIndex] = TYPE_LINE
                if (Color.alpha(pixels[newPixIndex]) <= alphaLevel) {
                    pointQueue.add(newPixIndex)
                }
            }

        }

        val endCalculation = Calendar.getInstance().timeInMillis

        val points = ArrayList<FillLine>()
        var lastState = false
        var state: Boolean
        var fillLine = FillLine()
        val pointsLength = filledPoints.size
        for (x in 0 until pointsLength) {
            state = filledPoints[x]
            if (lastState != state) {
                if (!state) {
                    fillLine.startX = x % width
                    fillLine.startY = x / width
                } else {
                    if (fillLine.startX != -1) {
                        fillLine.endX = x % width
                        fillLine.endY = x / width
                        points.add(fillLine)
                        fillLine = FillLine()
                    }
                }
                lastState = state
            }
        }
        Log.d("ASYNCCALCULATION", " Total: " + (Calendar.getInstance().timeInMillis - startTime)
                + " Calculation: " + (endCalculation - startTime)
                + " Points: " + (Calendar.getInstance().timeInMillis - endCalculation)
                + " " + points.size)
        return points
    }

    override fun onPostExecute(result: ArrayList<FillLine>?) {
        super.onPostExecute(result)
        asyncListener?.onCompleted(result)
    }


}
