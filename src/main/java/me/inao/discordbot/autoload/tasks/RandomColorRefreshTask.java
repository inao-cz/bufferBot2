package me.inao.discordbot.autoload.tasks;

import me.inao.dbbp.annotations.Autoload;
import me.inao.dbbp.annotations.Inject;
import me.inao.dbbp.annotations.Task;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.enums.TaskType;
import me.inao.dbbp.interfaces.IScheduledTask;
import me.inao.discordbot.lentils.HttpDriver;
import me.inao.discordbot.lentils.LoggerLentil;
import me.inao.discordbot.lentils.QrngColorLentil;
import me.inao.discordbot.statics.Links;
import org.apache.logging.log4j.Level;

import java.util.Arrays;

@Autoload(type = AutoloadType.TASK)
@Task(time = 60, type = TaskType.REPEAT)
public class RandomColorRefreshTask implements IScheduledTask {

    @Inject
    private LoggerLentil logger;

    @Override
    public void run() {
        QrngColorLentil.parseColorFromQrng(new HttpDriver().getRequestWithoutResponse(Links.QRNG_RANDOM_HEXCOLOR));
        logger.log(this.getClass(), "New QRNG colors. Fetched: " + Arrays.toString(QrngColorLentil.getColors().toArray()), Level.INFO);
    }
}
