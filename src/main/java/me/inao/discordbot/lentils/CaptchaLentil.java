package me.inao.discordbot.lentils;

import lombok.Getter;
import lombok.Setter;
import me.inao.dbbp.annotations.Stateful;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;

@Stateful
public class CaptchaLentil {

    @Getter
    @Setter
    private ScheduledFuture<?> scheduledTask = null;

    @Getter
    @Setter
    private int usersCount = 0;

    @Getter
    @Setter
    private ArrayList<String> captchaList = new ArrayList<>();

    public void clearUsers(){
        captchaList.clear();
    }

    public void addUser(){
        usersCount += 1;
    }
}
