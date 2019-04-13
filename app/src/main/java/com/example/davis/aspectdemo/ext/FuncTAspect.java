package com.example.davis.aspectdemo.ext;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by xushengfu on 2019/4/11.
 */

@Aspect
public class FuncTAspect {

    @Before("execution(* android.app.Activity.*(..))")
    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();

        Log.e("FuncTAspect", "onActivityMethodBefore: " + key+"\n"+joinPoint.getThis());
    }


}
