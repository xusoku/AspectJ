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
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }




    public void doFunc1(View view) {
        try {
            AppItem appItem=null;
            appItem.number=2;
        }catch (Exception e){}

    }


    public void doFunc3(View view) {
        AA();
    }


    public  void AA(){
        Log.e("doFuncaaa2","aaaaaa");
    }
}
