package ru.shurshavchiki.Helpers;

import java.awt.*;

public class ChannelColorHelper {
    public static Color map(String channel){
        return switch (channel) {
            case "RED" -> new Color(170, 0, 0);
            case "BLUE" -> new Color(12, 38, 166);
            case "GREEN" -> new Color(10, 173, 10);
            case "HUE" -> new Color(0, 255, 255);
            case "SATURATION" -> new Color(77, 0, 0);
            case "LIGHTNESS" -> new Color(247, 123, 123);
            case "BRIGHTNESS" -> new Color(157, 0, 0);
            case "Y_VALUE" -> new Color(123, 123, 123);
            case "CHROMATIC_BLUE" -> new Color(43, 66, 116);
            case "CHROMATIC_RED" -> new Color(213, 46, 113);
            case "CHROMATIC_GREEN" -> new Color(97, 149, 97);
            case "CHROMATIC_ORANGE" -> new Color(198, 120, 39);
            case "CYAN" -> new Color(0, 255, 252);
            case "MAGENTA" -> new Color(221, 22, 227);
            case "YELLOW" -> new Color(247, 255, 0);
            default -> Color.BLACK;
        };
    }
}
