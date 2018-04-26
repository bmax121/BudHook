package com.bmax.budhook;

import android.util.Log;

/**
 * Created by bmax on 2018/4/13.
 */

public class TestClass {

    public TestClass(String s){
        Log.d("budhook","origin call constructor");
    }
    @Override
    public String toString(){
        return "origin TestClass toString()";
    }
}
