package me.inao.discordbot.statics;

import me.inao.discordbot.lentils.HttpDriver;
import me.inao.discordbot.lentils.QrngColorLentil;

import java.util.ArrayList;

/**
 * To get more colors, I can recommend using:
 * - https://color.adobe.com/create/color-wheel
 * - https://materializecss.com/color.html
 * - https://material.io/resources/color/#!/?view.left=0&view.right=0
 */
public class Colors {
    public static final String COLOR_SUCCESS = "#38FA6F";
    public static final String COLOR_INFORM = "#64B5F6";
    public static final String COLOR_ERROR = "#D32F2F";
    public static final String COLOR_WARNING = "#F9A825";

    public static String regenerateRandomColor(){
        return QrngColorLentil.getRandomQrngColor();
    }
}
