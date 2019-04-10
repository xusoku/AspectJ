package com.example.davis.aspectdemo;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Field;

/**
 * Created by davis on 2019/4/11.
 */

@Aspect
public class ToastAspect {
    private static final String TAG = "toastAspect";

    @Pointcut("call(* android.widget.Toast+.show(..)) && (within(com.example.davis..*))")
    public void toastShow() {
    }

    @Pointcut("toastShow() && !cflowbelow(toastShow())")
    public void realToastShow() throws Throwable {
    }

    @Around("realToastShow()")
    public void toastShow(ProceedingJoinPoint point) {
        Log.e("aa","fefe");
        try {
            Toast toast = (Toast) point.getTarget();
            Context context = (Context) getValue(toast, "mContext");
//如果当前没有context意味着可能页面被回收，或者的版本在19以上且通知可用，执行系统的Toast方案交给系统处理
            if (context == null || Build.VERSION.SDK_INT >= 19 && NotificationManagerCompat.from(context).areNotificationsEnabled()) {
//use system function
                point.proceed(point.getArgs());
            } else {//如果context存在，并且通知不可用，则使用自定义的Toast
//Toast params
                int mDuration = toast.getDuration();
                View mNextView = toast.getView();
                int mGravity = toast.getGravity();
                int mX = toast.getXOffset();
                int mY = toast.getYOffset();
                float mHorizontalMargin = toast.getHorizontalMargin();
                float mVerticalMargin = toast.getVerticalMargin();

                ToastCustom toastCustom = ToastCustom.makeText(context, "自定义Toast显示风格", Toast.LENGTH_LONG);
                toastCustom.setText(R.string.app_name);
                toastCustom.setGravity(Gravity.CENTER, 0, 0);
                toastCustom.show();
            }
        } catch (Throwable exception)

        {
//ignore
        }

    }
// TODO: 2016/12/14 toast.cancel() can't be work with MToast

    /**
     * 通过字段名从对象或对象的父类中得到字段的值
     *
     * @param object    对象实例
     * @param fieldName 字段名
     * @return 字段对应的值
     * @throws Exception
     */
    public static Object getValue(Object object, String fieldName) throws Exception {
        if (object == null || TextUtils.isEmpty(fieldName)) {
            return null;
        }
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(object);
            } catch (Exception e) {
//ignore
            }
        }
        return null;
    }


}
