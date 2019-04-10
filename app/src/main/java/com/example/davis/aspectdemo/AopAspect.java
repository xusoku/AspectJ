package com.example.davis.aspectdemo;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
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
    public void AopPiontF() {

    }

    //切面
    @Around("cutCall()")
    public Object dealPiont(ProceedingJoinPoint joinPoint) throws Throwable {


        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AopPoint aopPoin = signature.getMethod().getAnnotation(AopPoint.class);

//        String type=aopPoin.value();
//
//        TimeTool timeTool=new TimeTool();
//        timeTool.start();
        Object o = joinPoint.proceed();
//        timeTool.stop();
//        Log.e("type",timeTool.getTotalTimeMillis()+"  "+type);
        Log.e("aaa", "aaaa");

        return o;

    }

    @Before("AopPiontF()")
    public void before() {
        Log.e("before", "before");
    }

    @After("AopPiontF()")
    public void after() {
        Log.e("after", "after");
    }


//    //表示匹配 com.example.davis.aspectdemo.MainActivity类中所有被@Cut注解的public void方法
//    @Pointcut("call(@AopPiontF public void com.example.davis.aspectdemo.MainActivity * *(..))")
//    public void cutCall() {
//        Log.e("cutCall", "cutCall");
//    }


//    //1.所有实例方法调用截获
//    private static final String INSTANCE_METHOD_CALL =
//            "call(!static * com.example.davis.aspectdemo..*.*(..))&&target(Object)";
//
//    @Pointcut(INSTANCE_METHOD_CALL)
//    public void instanceMethodCall() {
//    }
//
//    //实例方法调用前后Advice
//    @Before("instanceMethodCall()")
//    public void beforInstanceCall(JoinPoint joinPoint) {
//        Log.e("333444", "beforInstanceCall");
//    }
//
//    @After("instanceMethodCall()")
//    public void afterInstanceCall(JoinPoint joinPoint) {
//        Log.e("111222", "afterInstanceCall");
//    }
//
//
//    //所有实例方法执行截获
//    private static final String INSTANCE_METHOD_EXECUTING = "execution(!static * com.example.davis.aspectdemo..*.*(..))&&target(Object)";
//    @Pointcut(INSTANCE_METHOD_EXECUTING)
//    public void instanceMethodExecuting() {
//    }
//
//    //实例方法执行
//    @Around("instanceMethodCall()")
//    public Object InstanceMethodExecutingAround(ProceedingJoinPoint joinPoint){
//        Log.e("123","InstanceMethodExecuting()");
//        Object result = printLog(joinPoint, "instanceexecuting");
//        return result;
//    }
//
//
//    private Object printLog(JoinPoint joinPoint, String describe) {
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        try {
//            if (joinPoint instanceof ProceedingJoinPoint) {
//                return ((ProceedingJoinPoint) joinPoint).proceed(joinPoint.getArgs());
//            }
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        } finally {
//            Log.e("456", describe + " : " + signature.toLongString());
//        }
//        return null;
//    }



}



//    //2横切项目中所有Activity的子类，以Layout命名、以及它的子类的所有方法的执行
//    private static final String POINTCUT_METHOD =
//            "(execution(* android.app.Activity+.*(..)) ||execution(* *..Layout+.*(..)))&& within(com.example.davis.aspectdemo.*)";
//
//    @Pointcut(POINTCUT_METHOD)
//    public void methodAnnotated() {
//    }
//
//    @Around("methodAnnotated()")
//    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
//        //调用原方法的执行。
//        Object result = joinPoint.proceed();
//        Log.e("weaveJoinPoint", "Activity的子类");
//        return result;
//
//    }
