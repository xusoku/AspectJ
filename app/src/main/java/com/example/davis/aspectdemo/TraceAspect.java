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

    @Pointcut(POINTCUT_METHOD)
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

        return result;
    }

}