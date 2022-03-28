package me.inao.dbbp.processing.loader;

import lombok.RequiredArgsConstructor;
import me.inao.dbbp.processing.annotations.Task;
import me.inao.dbbp.processing.injectors.InjectorHandler;
import me.inao.dbbp.processing.interfaces.IScheduledTask;
import me.inao.dbbp.processing.persistant.StorageUnit;
import org.reflections.Reflections;

import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class TaskAutoloader {
    private final StorageUnit unit;
    private final String prefix;

    public Runnable load() {
        return () -> {
            Reflections reflections = new Reflections(prefix);
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Task.class);
            classes.forEach(taskClass -> {
                if(IScheduledTask.class.isAssignableFrom(taskClass)) {
                    Task taskData = taskClass.getAnnotation(Task.class);
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            try{
                                Object taskInstance = taskClass.getDeclaredConstructor().newInstance();
                                new InjectorHandler(unit).injectionHandler(taskInstance, null);
                                ((IScheduledTask)taskInstance).run();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    switch (taskData.type()) {
                        case ONCE:
                            unit.getExecutorService().schedule(task, taskData.time(), TimeUnit.of(taskData.unit()));
                            break;
                        case REPEAT:
                            unit.getExecutorService().scheduleAtFixedRate(task, 0, taskData.time(), TimeUnit.of(taskData.unit()));
                            break;
                    }
                }
            });
        };
    }
}
