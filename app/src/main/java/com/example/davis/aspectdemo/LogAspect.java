//package com.example.davis.aspectdemo;
//
//import android.util.Log;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//
///**
// * Created by davis on 2019/4/11.
// */
//@Aspect
//public class LogAspect {
//    //所有静态方法调用截获
//    private static final String STATIC_METHOD_CALL =
//            "call(static * com.example.davis.aspectdemo.MainActivity * *(..))";
//    @Pointcut(STATIC_METHOD_CALL) public void staticMethodCutting() {
//    }
//    @Before("staticMethodCutting()") public void beforStaticCall(JoinPoint joinPoint) {
//        printLog(joinPoint, "before static call");
//    }
//    @After("staticMethodCutting()") public void afterStaticCall(JoinPoint joinPoint) {
//        printLog(joinPoint, "after static call");
//    }
//    //所有实例方法调用截获
//    private static final String INSTANCE_METHOD_CALL =
//            "call(!static * com.example.davis.aspectdemo.MainActivity * *(..))&&target(Object)";
//    @Pointcut(INSTANCE_METHOD_CALL) public void instanceMethodCall() {
//    }
//    //实例方法调用前后Advice
//    @Before("instanceMethodCall()") public void beforInstanceCall(JoinPoint joinPoint) {
//        printLog(joinPoint, "before instance call");
//    }
//    @After("instanceMethodCall()") public void afterInstanceCall(JoinPoint joinPoint) {
//        printLog(joinPoint, "after instance call");
//    }
//    //所有静态方法执行截获
//    private static final String STATIC_METHOD_EXECUTING =
//            "execution(static * com.example.davis.aspectdemo.MainActivity * *(..))";
//    @Pointcut(STATIC_METHOD_EXECUTING) public void staticExecutionCutting() {
//    }
//    //所有实例方法执行截获
//    private static final String INSTANCE_METHOD_EXECUTING =
//            "execution(!static * com.example.davis.aspectdemo.MainActivity * *(..))&&target(Object)";
//    @Pointcut(INSTANCE_METHOD_EXECUTING) public void instanceMethodExecuting() {
//    }
//    //静态方法执行Advice
//    @Around("staticExecutionCutting()") public Object staticMethodExecuting(
//            ProceedingJoinPoint joinPoint) {
//        Log.e(getClass().getSimpleName(), "staticMethodExecuting()");
//        Object result = printLog(joinPoint, "static executing");
//        return result;
//    }
//    //实例方法执行Advice
//    @Around("instanceMethodExecuting()") public Object InstanceMethodExecuting(
//            ProceedingJoinPoint joinPoint) {
//        Log.e(getClass().getSimpleName(), "InstanceMethodExecuting()");
//        Object result = printLog(joinPoint, "instance executing");
//        return result;
//    }
//    /**
//     * 日志打印和统计
//     * @param joinPoint
//     * @param describe
//     * @return
//     */
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
//            Log.e(getClass().getSimpleName(), describe + " : " + signature.toLongString());
//        }
//        return null;
//    }
//
//}
