package com.patryk1007.fillme.enums;

public enum FillMode {
    NONE(0), HORIZONTAL(1), VERTICAL(2), BOTH(3);

    private final int id;

    FillMode(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }

    public static FillMode fromId(int id) {
        for (FillMode mode : values()) {
            if (mode.getValue() == id) {
                return mode;
            }
        }
        return NONE;
    }
}