package com.shmakov.techfate.mytools;

import android.graphics.Color;

import com.shmakov.techfate.ColorsFragment;

public class ColorManager {

    public static final String BLACK_COLOR = "black";
    public static final String WHITE_COLOR = "white";
    public static final String YELLOW_COLOR = "yellow";
    public static final String GRAY_COLOR = "gray";
    public static final String BLUE_COLOR = "blue";
    public static final String GREEN_COLOR = "green";

    public static int nameColorToInt(String colorName) {
        switch (colorName) {
            case "black":
                return Color.BLACK;
            case "white":
                return Color.WHITE;
            case "yellow":
                return Color.YELLOW;
            case "gray":
                return Color.GRAY;
            case "blue":
                return Color.BLUE;
            case "green":
                return Color.GREEN;
        }
        return Color.LTGRAY;
    }
}
