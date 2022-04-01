package me.inao.dbbp.annotations;

import me.inao.dbbp.enums.AutoloadType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Autoload {
    AutoloadType type() default AutoloadType.NONE;
}
