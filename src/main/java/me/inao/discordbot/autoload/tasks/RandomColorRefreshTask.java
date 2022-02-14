package me.inao.discordbot.autoload.tasks;

import me.inao.dbbp.processing.annotations.Task;
import me.inao.dbbp.processing.enums.TaskType;
import me.inao.dbbp.processing.interfaces.IScheduledTask;

@Task(time = 15, type = TaskType.REPEAT)
public class RandomColorRefreshTask implements IScheduledTask {

    @Override
    public void run() {

    }
}
