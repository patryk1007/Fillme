package com.patryk1007.fillme.listeners;


import com.patryk1007.fillme.models.FillLine;

import java.util.ArrayList;

public interface OnFillLineCalculationListener {

    void onCompleted(ArrayList<FillLine> points);

}
