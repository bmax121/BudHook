package bmax.budhook;


import android.content.Context;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public final class BudHelpers {

    public static void init(Context context){
        BudBridge.init(context);
    }

    public static Class findClass(String className,ClassLoader loader){
        Class clazz = null;
        try {
            clazz = Class.forName(className,false,loader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    private static Class<?>[] getParameterClasses(ClassLoader classLoader, Object[] parameterTypesAndCallback) {

        Class[] parameterClasses = new Class[parameterTypesAndCallback.length - 1];

        for(int i = parameterTypesAndCallback.length - 2; i >= 0; --i) {
            Object type = parameterTypesAndCallback[i];

                if(type instanceof Class) {
                    parameterClasses[i] = (Class)type;
                } else if(type instanceof String ) {
                    try {
                        parameterClasses[i] = Class.forName((String)type,false, classLoader);
                    } catch (ClassNotFoundException e) {
                        BudLog.e("can not find class " + (String)type);
                        return null;
                    }
                } else {
                    BudLog.e("parameter type must either be specified as Class or String");
                    return null;
                }
            }

        if(parameterClasses == null) {
            parameterClasses = new Class[0];
        }
        return parameterClasses;
    }

    public static void findAndHookMethod(Class clazz, String methodName, Object... parameterTypesAndCallback) {

        if(parameterTypesAndCallback.length == 0 || ! (parameterTypesAndCallback[parameterTypesAndCallback.length - 1] instanceof BudCallBack ) ){
            BudLog.e("findAndHookMethod parameters must include BudCallBack class");
            return;
        }

        BudCallBack callback = (BudCallBack) parameterTypesAndCallback[parameterTypesAndCallback.length - 1];
        Class<?>[] parameterClasses = getParameterClasses(clazz.getClassLoader(),parameterTypesAndCallback);

        if( parameterClasses == null) return;

        Method hookMethod = null;
        try {
            hookMethod = clazz.getDeclaredMethod(methodName,parameterClasses);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if( hookMethod == null){
            BudLog.e("can not find method:"+ clazz.toString() + "#" + methodName);
            return;
        }

        BudBridge.hookMethod(hookMethod,callback);
    }

    public static void findAndHookMethod(String className,ClassLoader loader, String methodName,Object... parameterTypesAndCallback){

        Class clazz = findClass(className,loader);
        if(  clazz == null){
            BudLog.e("can not find class:" + className);
            return;
        }

        findAndHookMethod(clazz,methodName,parameterTypesAndCallback);

    }

    public static void findAndHookConstructor(Class clazz ,Object... parameterTypesAndCallback){
        if(parameterTypesAndCallback.length == 0 || ! (parameterTypesAndCallback[parameterTypesAndCallback.length - 1] instanceof BudCallBack ) ){
            BudLog.e("findAndHookMethod parameters must include BudCallBack class");
            return;
        }

        BudCallBack callback = (BudCallBack) parameterTypesAndCallback[parameterTypesAndCallback.length - 1];

        Class<?>[] parameterClasses = getParameterClasses(clazz.getClassLoader(),parameterTypesAndCallback);
        if( parameterClasses == null) return;

        Constructor constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor(parameterClasses);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if( constructor == null){
            BudLog.e("can not find specified constructor in class:"+ clazz.toString() );
            return;
        }

        BudBridge.hookMethod(constructor,callback);
    }

    public static void findAndHookConstructor(String className,ClassLoader loader,Object... parameterTypesAndCallback){
        Class clazz = findClass(className,loader);
        if(  clazz == null){
            BudLog.e("can not find class:" + className);
            return;
        }
        findAndHookConstructor(clazz,parameterTypesAndCallback);
    }

    public static void prepareHookMethod(Class clazz, String methodName, Object... parameterTypesAndCallback){
        if(parameterTypesAndCallback.length == 0 || ! (parameterTypesAndCallback[parameterTypesAndCallback.length - 1] instanceof BudCallBack ) ){
            BudLog.e("findAndHookMethod parameters must include BudCallBack class");
            return;
        }

        BudCallBack callback = (BudCallBack) parameterTypesAndCallback[parameterTypesAndCallback.length - 1];
        Class<?>[] parameterClasses = getParameterClasses(clazz.getClassLoader(),parameterTypesAndCallback);
        if( parameterClasses == null) return;

        Method hookMethod = null;
        try {
            hookMethod = clazz.getDeclaredMethod(methodName,parameterClasses);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if( hookMethod == null){
            BudLog.e("can not find method:"+ clazz.toString() + "#" + methodName);
            return;
        }
        BudBridge.addPrepareHook(hookMethod,callback);
    }

    public static void prepareHookMethod(String className,ClassLoader loader, String methodName,Object... parameterTypesAndCallback){
        Class clazz = findClass(className,loader);
        if(  clazz == null){
            BudLog.e("can not find class:" + className);
            return;
        }

        prepareHookMethod(clazz,methodName,parameterTypesAndCallback);
    }

    public static void prepareHookConstructor(Class clazz ,Object... parameterTypesAndCallback){
        if(parameterTypesAndCallback.length == 0 || ! (parameterTypesAndCallback[parameterTypesAndCallback.length - 1] instanceof BudCallBack ) ){
            BudLog.e("findAndHookMethod parameters must include BudCallBack class");
            return;
        }

        BudCallBack callback = (BudCallBack) parameterTypesAndCallback[parameterTypesAndCallback.length - 1];
        Class<?>[] parameterClasses = getParameterClasses(clazz.getClassLoader(),parameterTypesAndCallback);
        if( parameterClasses == null) return;

        Constructor constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor(parameterClasses);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if( constructor == null){
            BudLog.e("can not find specified constructor in class:"+ clazz.toString() );
            return;
        }

        BudBridge.addPrepareHook(constructor,callback);
    }
    public static void prepareHookConstructor(String className,ClassLoader loader,Object... parameterTypesAndCallback){
        Class clazz = findClass(className,loader);
        if(  clazz == null){
            BudLog.e("can not find class:" + className);
            return;
        }
        prepareHookConstructor(clazz,parameterTypesAndCallback);
    }

    public static void hookAllPrepared(){
        BudBridge.hookAllPrepared();
    }

}