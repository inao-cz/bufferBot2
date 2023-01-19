package me.inao.dbbp.connectors.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Http {
    MethodType method() default MethodType.GET;
    boolean fetch() default false;

    String url();

    int connectionTimeout() default 5000;
    int readTimeout() default 15000;
}
