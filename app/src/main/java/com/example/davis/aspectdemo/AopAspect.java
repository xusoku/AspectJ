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
 * Created by davis on 2019/4/10.
 */
@Aspect
public class AopAspect {

//    2个方法

    //切点
    @Pointcut("execution(@com.example.davis.aspectdemo.AopPoint * *(..))")
    public void AopPiontF(){

    }

    //切面
    @Around("AopPiontF()")
    public Object dealPiont(ProceedingJoinPoint joinPoint) throws Throwable {


        MethodSignature signature= (MethodSignature) joinPoint.getSignature();
        AopPoint aopPoin=signature.getMethod().getAnnotation(AopPoint.class);

        String type=aopPoin.value();

        TimeTool timeTool=new TimeTool();
        timeTool.start();
        Object o=joinPoint.proceed();
        timeTool.stop();
        Log.e("type",timeTool.getTotalTimeMillis()+"  "+type);

        return o;

    }

    @Before("AopPiontF()")
    public void before(){
        Log.e("before","before");
    }
    @After("AopPiontF()")
    public void after(){
        Log.e("after","after");
    }

}
