package me.inao.dbbp.processing.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Inject {
    String field() default "";
    String function() default "";
    int sequenceNumber() default 0;
    boolean outside() default false;
}
