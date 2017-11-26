package com.patryk1007.fillme.models;

public class FillLine {

    private int startX = -1;
    private int startY = -1;
    private int endX = -1;
    private int endY = -1;

    public FillLine() {
    }

    public FillLine(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartXY(int x, int y){
        this.startX = x;
        this.startY = y;
    }

    public void setEndXY(int x, int y){
        this.endX = x;
        this.endY = y;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }
}
