package com.example.davis.aspectdemo;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Created by davis on 2019/3/20.
 */

@Aspect
public class TraceAspect {

    private static final String POINTCUT_METHOD =
            "execution(@com.example.davis.aspectdemo.DebugTrace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.example.davis.aspectdemo.DebugTrace *.new(..))";

    @Pointcut("execution(@com.example.davis.aspectdemo.DebugTrace * *(..))")
    public void methodAnnotatedWithDebugTrace() {}

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace() {}

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getName();
        String methodName = methodSignature.getName();
        Method method=methodSignature.getMethod();

        DebugTrace debugTrace=method.getAnnotation(DebugTrace.class);
        String value=debugTrace.value();
        int type=debugTrace.type();


        Object[] argsObject = joinPoint.getArgs();

        final TimeTool timeTool = new TimeTool();
        timeTool.start();
        Object result = joinPoint.proceed();
        timeTool.stop();

//        for (int i = 0; i < argsObject.length; i++) {
//            Log.e(className,"argsObject="+i+"="+argsObject[i].toString());
//        }

        if(type==1){ //点击
            if(value.equals("onCreate")){
                Log.e(className, className + "  被访问了");
            }else {
                Log.e(className, value + "  被点击了");
            }

        }else if(type==2){ //曝光

            Log.e(className,value+"  被曝光了");
        }else if(type==3){ //安装

            Log.e(className,value+"  被安装了");
        }
        Log.e(className,"methodName="+methodName);
        Log.e(className,"value="+value);
        Log.e(className,"type="+type);

//        Log.d(value, buildLogMessage(methodName, stopWatch.getTotalTimeMillis()));

        return result;
    }

    /**
     * Create a log message.
     *
     * @param methodName A string with the method name.
     * @param methodDuration Duration of the method in milliseconds.
     * @return A string representing message.
     */
    private static String buildLogMessage(String methodName, long methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append("Gintonic --> ");
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(methodDuration);
        message.append("ms");
        message.append("]");

        return message.toString();
    }
}