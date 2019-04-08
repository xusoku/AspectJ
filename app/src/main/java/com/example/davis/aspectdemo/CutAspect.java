package com.example.davis.aspectdemo;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by davis on 2019/3/26.
 */
@Aspect
public class CutAspect {

    @Pointcut("execution(@com.example.davis.aspectdemo.Cut * *(..))")
    public void cutMethod(){

    }

    @Around("cutMethod()")
    public Object dealCut(ProceedingJoinPoint joinPoint) throws Throwable {


        MethodSignature signature= (MethodSignature) joinPoint.getSignature();


        Method method=signature.getMethod();

        Cut cut=method.getAnnotation(Cut.class);

        Object o=joinPoint.proceed();

        Log.e("cut","Around");
        return o;
    }


    @Before("cutMethod()")
    public void beforeCut(){
        Log.e("cut","Before");
    }
    @After("cutMethod()")
    public void afterCut() throws Throwable {
        Log.e("cut","After");
    }
}
