package com.example.davis.aspectdemo;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

    @Override
    protected void onResume() {
        super.onResume();
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

    public void doFunc3(View view) {
        try {
            AppItem appItem=null;
            appItem.number=2;
            SystemClock.sleep(1000);
        }catch (Exception e){

        }

        AA();
        Toast.makeText(this,"af",Toast.LENGTH_LONG).show();
    }

    public  void AA(){
        Log.e("doFuncaaa2","aaaaaa");
    }


}
