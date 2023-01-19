package me.inao.dbbp.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Argument {
    Class<?>[] type();
    String name();
    String usage();
    String[] aliases();
}
