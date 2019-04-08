package com.example.davis.aspectdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by davis on 2019/3/20.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.CONSTRUCTOR, ElementType.METHOD,ElementType.FIELD})
public @interface DebugTrace {
    String value();
    int type() default 1;
}