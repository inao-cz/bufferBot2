package me.inao.discordbot.autoload.tasks;

import me.inao.dbbp.annotations.Inject;
import me.inao.dbbp.annotations.Task;
import me.inao.dbbp.interfaces.IScheduledTask;
import me.inao.discordbot.lentils.CaptchaLentil;

@Task
public class CaptchaClearTask implements IScheduledTask {

    @Inject
    private CaptchaLentil captcha;

    @Override
    public void run() {
        captcha.setUsersCount(0);
        captcha.clearUsers();
    }
}
