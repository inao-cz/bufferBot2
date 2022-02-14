package me.inao.discordbot.lentils;

import com.google.gson.JsonParser;
import me.inao.dbbp.processing.annotations.Stateful;

import java.security.SecureRandom;
import java.util.ArrayList;

@Stateful
public class ParserUtil {

    public void parseColorFromQrng(ArrayList<String> qrngColors, String input){
        JsonParser.parseString(input).getAsJsonObject().get("data").getAsJsonArray().forEach(entry -> qrngColors.add(entry.getAsString()));
    }

    public String getRandomQrngColor(ArrayList<String> qrngColors){
        return qrngColors.get(new SecureRandom().nextInt((qrngColors.size() - 1)));
    }
}
