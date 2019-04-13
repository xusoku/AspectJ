package com.example.davis.aspectdemo;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by xushengfu on 2019/4/11.
 */
@Aspect
public class AopAspect {

    @Pointcut("execution(@com.example.davis.aspectdemo.AopPoint * *(..))")
    public void aopCut(){

    }
    @Around("aopCut()")
    public Object dealMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature= (MethodSignature) joinPoint.getSignature();
        AopPoint aopPoint=signature.getMethod().getAnnotation(AopPoint.class);

        String type=aopPoint.value();


        TimeTool timeTool=new TimeTool();
        timeTool.start();

        Object result=joinPoint.proceed();

        timeTool.stop();
        Log.e("aopCut",timeTool.getTotalTimeMillis()+"==="+type);

        return result;
    }

}
