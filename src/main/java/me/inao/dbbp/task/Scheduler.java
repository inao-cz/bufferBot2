package me.inao.dbbp.task;

import lombok.RequiredArgsConstructor;
import me.inao.dbbp.injectors.InjectorHandler;
import me.inao.dbbp.interfaces.IScheduledTask;
import me.inao.dbbp.persistant.StorageUnit;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class Scheduler {
    private StorageUnit storageUnit;
    public void scheduleTask(Class<? extends IScheduledTask> taskClass, long time, ChronoUnit timeUnit){
        storageUnit.getExecutorService().schedule(() -> {
            try{
                IScheduledTask taskInstance = taskClass.getDeclaredConstructor().newInstance();
                new InjectorHandler(storageUnit).injectionHandler(taskInstance, null);
                taskInstance.run();
            }catch (Exception e){
                e.printStackTrace();
            }
        }, time, TimeUnit.of(timeUnit));
    }
}
