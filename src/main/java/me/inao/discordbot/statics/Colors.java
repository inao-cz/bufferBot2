package me.inao.discordbot.statics;

import me.inao.discordbot.lentils.QrngColorLentil;

import java.awt.*;

/**
 * To get more colors, I can recommend using:
 * - <a href="https://color.adobe.com/create/color-wheel">Color wheel</a>
 * - <a href="https://materializecss.com/color.html">Materialize colors</a>
 * - <a href="https://material.io/resources/color/#!/?view.left=0&view.right=0">Material.io colors</a>
 */
public class Colors {
    public static final String COLOR_SUCCESS = "#38FA6F";
    public static final String COLOR_INFORM = "#64B5F6";
    public static final String COLOR_ERROR = "#D32F2F";
    public static final String COLOR_WARNING = "#F9A825";

    public static Color regenerateRandomColor(){
        return Color.decode(QrngColorLentil.getRandomQrngColor());
    }
}
