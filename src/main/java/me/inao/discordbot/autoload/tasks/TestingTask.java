package me.inao.discordbot.autoload.tasks;

import me.inao.dbbp.processing.annotations.Inject;
import me.inao.dbbp.processing.annotations.Task;
import me.inao.dbbp.processing.enums.TaskType;
import me.inao.dbbp.processing.interfaces.IScheduledTask;
import me.inao.discordbot.lentils.LoggerLentil;
import org.apache.logging.log4j.Level;

import java.time.temporal.ChronoUnit;

@Task(time = 15, type = TaskType.REPEAT, unit = ChronoUnit.SECONDS)
public class TestingTask implements IScheduledTask {

    @Inject
    private LoggerLentil logger;

    @Override
    public void run() {
        logger.log(this.getClass(), "TestingTask Execution. Launch: " + System.currentTimeMillis(), Level.INFO);
    }
}
