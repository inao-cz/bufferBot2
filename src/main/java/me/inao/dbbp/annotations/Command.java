package me.inao.dbbp.annotations;

import me.inao.dbbp.interfaces.IArgument;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String name();
    String description();
    Class<? extends IArgument>[] requiredArguments() default {};
    Class<? extends IArgument>[] optionalArguments() default {};
}
