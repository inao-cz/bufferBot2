package me.inao.dbbp.processing.annotations;

import me.inao.dbbp.processing.enums.TaskType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Task {
    TaskType type() default TaskType.ONCE;
    int time() default 10;
    ChronoUnit unit() default ChronoUnit.MINUTES;
    String identifier() default "";

}
