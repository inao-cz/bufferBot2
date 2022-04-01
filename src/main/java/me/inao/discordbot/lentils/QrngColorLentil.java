package me.inao.discordbot.lentils;

import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import me.inao.dbbp.annotations.Stateful;

import java.security.SecureRandom;
import java.util.ArrayList;

@Stateful
public class QrngColorLentil {
    @Getter
    @Setter
    private static ArrayList<String> colors = new ArrayList<>();

    public static void parseColorFromQrng(String input){
        colors.clear();
        JsonParser.parseString(input).getAsJsonObject().get("data").getAsJsonArray().forEach(entry -> colors.add("#" + entry.getAsString().trim()));
    }

    public static String getRandomQrngColor(){
        return colors.get(new SecureRandom().nextInt((colors.size() - 1)));
    }
}
