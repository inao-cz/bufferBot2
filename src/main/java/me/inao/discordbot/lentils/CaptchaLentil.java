package me.inao.discordbot.lentils;

import lombok.Getter;
import lombok.Setter;
import me.inao.dbbp.annotations.Stateful;

import java.util.ArrayList;

@Stateful
public class CaptchaLentil {

    @Getter
    @Setter
    private ArrayList<String> captchaList = new ArrayList<>();

    public void clearUsers(){
        captchaList.clear();
    }
}
