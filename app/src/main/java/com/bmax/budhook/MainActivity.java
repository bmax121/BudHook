package com.bmax.budhook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Method;

import bmax.budhook.BudHelpers;
import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "budhook";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    private Button btn_test ;
    private Button btn_hook ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_hook = (Button) findViewById(R.id.btn_hook);
        btn_test = (Button) findViewById(R.id.btn_test);

        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            //如果应用程序没有权限，则会提示用户授予权限
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }



        BudHelpers.init(getBaseContext());


        ClassLoader classLoader = getClassLoader();

        final DexClassLoader dexClassLoader = new DexClassLoader("/sdcard/appplugin-debug.apk",
                getCodeCacheDir().getAbsolutePath(), null, classLoader);

        Log.d(TAG,dexClassLoader.toString());

        btn_hook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Class demo = Class.forName("bmax.budhook.demo.BudHookDemo", true, dexClassLoader);

                    Method startHook = demo.getMethod("startHook",ClassLoader.class);

                    startHook.invoke(null,getClassLoader());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runTestFun(MainActivity.this);
            }
        });

    }

    public static void runTestFun(MainActivity thiz){


        test1(1,2,'c');


        long ret = thiz.test2("test2 arg1","test2 arg2","test2 arg3","test2 arg4","test2 arg5",new TestClass("constructor test"));
        Log.d(TAG,"test2 ret" + Long.toString(ret));

        try {
            thiz.test3("def");
        } catch (Throwable e) {
            Log.d(TAG,e.toString());
        }
    }

    public static void test1(int  arg1,double arg2,char arg3){
        Log.d(TAG,"arg1:" + arg1 + " arg2:" + arg2 +" arg3:" + arg3);
    }

    public long  test2(String arg1,String arg2,String arg3,String arg4,String arg5,TestClass arg6){

        return 1111;
    }

    public  void test3(Object a) throws Exception{
        Log.d(TAG,"origin call test3" );
        throw new Exception("origin exception");
    }

}
