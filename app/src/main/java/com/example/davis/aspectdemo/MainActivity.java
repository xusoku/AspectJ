package com.example.davis.aspectdemo;

import android.content.Intent;
import android.os.SystemClock;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends BaseActivity {

    public String tag = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @AopPoint(value = "首页点击",type = 1)
    public void doFunc1(View view) {
        SystemClock.sleep(1000);

    }


    @AopPoint("分类点击")
    public void doFunc2(View view) {
        SystemClock.sleep(1000);

    }

    public void doFunc3(View view) {

        startActivity(new Intent(this,Main2Activity.class));

    }

}
