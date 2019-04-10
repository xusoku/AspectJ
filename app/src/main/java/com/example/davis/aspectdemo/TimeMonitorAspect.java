//package com.example.davis.aspectdemo;
//
//import android.util.Log;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//
///**
// * Created by davis on 2019/4/11.
// */
//@Aspect
//public class TimeMonitorAspect {
//
//    //横切项目中所有Activity的子类，以及它的子类的所有方法的执行
//    private static final String POINTCUT_METHOD =
//            "(execution(* android.app.Activity+.onResume(..)) && within(com.example.davis.aspectdemo.MainActivity.*)";
//
//    @Pointcut(POINTCUT_METHOD)
//    public void methodAnnotated() {
//    }
//
//    /**
//     * 截获原方法的执行，添加计时器，监控单个方法的耗时
//     *
//     * @throws Throwable
//     */
//    @Around("methodAnnotated()")
//    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint)
//            throws Throwable {
//    //初始化计时器
//        final TimeTool timeTool = new TimeTool();
//    //开始监听
//        timeTool.start();
//    //调用原方法的执行。
//        Object result = joinPoint.proceed();
//    //监听结束
//        timeTool.stop();
//    //日志打印
//        printLog(joinPoint, timeTool);
//        return result;
//    }
//
//    private void printLog(JoinPoint joinPoint, TimeTool stopWatch) {
//    //获取方法信息对象
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        String className;
//    //获取当前对象，通过反射获取类别详细信息
//        className = joinPoint.getThis().getClass().getName();
//        String methodName = methodSignature.getName();
//        String msg = buildLogMessage(methodName, stopWatch.getTotalTimeMillis());
//    //日志存储、打印
//        Log.e("aaa", msg);
//    }
//
//    /**
//     * 创建一个日志信息
//     *
//     * @param methodName     方法名
//     * @param methodDuration 执行时间
//     */
//    private static String buildLogMessage(String methodName, double methodDuration) {
//        StringBuilder message = new StringBuilder();
//        message.append(methodName);
//        message.append(" ­­> ");
//        message.append("[");
//        message.append(methodDuration);
//        message.append("] \n");
//        return message.toString();
//    }
//
//}
