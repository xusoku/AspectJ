package com.example.davis.aspectdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        AppItem appItem = new AppItem();
        appItem.downloadUrl = "www.baidu.com";
        appItem.number = 123;
        appItem.packageName = "asdfsdf";

    }

    public void doFunc1(View view) {


    }
    public void doFunc2(View view) {

    }
    public void doFunc3(View view) {

    }
}
