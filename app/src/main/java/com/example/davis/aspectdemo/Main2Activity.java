package com.example.davis.aspectdemo;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }


    public void doFunc1(View view) {

            AppItem appItem=null;
            appItem.number=2;
            SystemClock.sleep(1000);
    }


    public void doFunc3(View view) {
        AA();
    }


    public  void AA(){
        Log.e("doFuncaaa2","aaaaaa");
    }
}
