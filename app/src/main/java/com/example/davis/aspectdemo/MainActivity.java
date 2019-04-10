package com.example.davis.aspectdemo;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public String tag = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


    }
    //执行时间性能统计

    public void doFunc1(View view) {

        TimeTool timeTool=new TimeTool();
        timeTool.start();
        SystemClock.sleep(1000);
        timeTool.stop();
        Log.e("doFunc1",timeTool.getTotalTimeMillis()+"");

    }
    public void doFunc2(View view) {

        TimeTool timeTool=new TimeTool();
        timeTool.start();
        SystemClock.sleep(1000);
        timeTool.stop();
        Log.e("doFunc2",timeTool.getTotalTimeMillis()+"");

    }
    @AopPoint("func3")
    public void doFunc3(View view) {
        SystemClock.sleep(1000);
    }


}
