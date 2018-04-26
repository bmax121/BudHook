package bmax.budhook.demo;


import android.util.Log;

import bmax.budhook.BudCallBack;
import bmax.budhook.BudHelpers;

/**
 * Created by bmax on 2018/4/12.
 */



public class BudHookDemo {


    public static final String TAG = "budhook";


    public static void startHook(ClassLoader originClassLoader){


        //-------------start hook------------


        BudHelpers.findAndHookMethod("com.bmax.budhook.MainActivity", originClassLoader, "test1",
                int.class,double.class,char.class,
                new BudCallBack() {
                    @Override
                    public void beforeCall(MethodHookParams params) {
                        Log.d(TAG,"before call test1");
                        Log.d(TAG,params.method.toString());
                        if( params.thisObject == null){
                            Log.d(TAG,"static method,thisObject = null");
                        }

                        Log.d(TAG,Integer.toString((int)(params.args[0])));
                        Log.d(TAG,Double.toString((double)(params.args[1])));
                        params.args[1] = 5555d;
                        Log.d(TAG,Character.toString((char)(params.args[2])));
                        params.args[2] = 'd';
                    }

                    @Override
                    public void afterCall(MethodHookParams params) {
                        Log.d(TAG,"after call test1");
                    }
                });

        BudHelpers.prepareHookMethod("com.bmax.budhook.MainActivity", originClassLoader, "test2",
                String.class, String.class,String.class,String.class,String.class,"com.bmax.budhook.TestClass",
                new BudCallBack() {
                    @Override
                    public void beforeCall(MethodHookParams params) {
                        Log.d(TAG,"before call test2");
                        Log.d(TAG,params.thisObject.toString());
                        Log.d(TAG,params.args[5].toString());
                    }

                    @Override
                    public void afterCall(MethodHookParams params) {
                        params.result = 22222L;
                    }
                });


        BudHelpers.findAndHookMethod("com.bmax.budhook.MainActivity", originClassLoader, "test3",
                Object.class,
                new BudCallBack() {
                    @Override
                    public void beforeCall(MethodHookParams params) {
                        Log.d(TAG,"before call test3");
                    }

                    @Override
                    public void afterCall(MethodHookParams params) {
                        Log.d(TAG,"after call test3");
                        Log.d(TAG,params.throwable.toString());
                        params.throwable = new Exception("changed exception");
                    }
                });


        BudHelpers.prepareHookConstructor("com.bmax.budhook.TestClass", originClassLoader, String.class,
                new BudCallBack() {
                    @Override
                    public void beforeCall(MethodHookParams params) {
                        Log.d(TAG,"before TestClass Constructor arg:" + params.args[0].toString());
                    }

                    @Override
                    public void afterCall(MethodHookParams params) {
                        Log.d(TAG,"after TestClass Constructor ");
                    }
                });

        BudHelpers.hookAllPrepared();
    }

}
