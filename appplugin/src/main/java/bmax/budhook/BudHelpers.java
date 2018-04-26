package bmax.budhook;

public final class BudHelpers {


    public static Class findClass(String className,ClassLoader loader){
        return null;
    }


    public static void findAndHookMethod(Class clazz, String methodName, Object... parameterTypesAndCallback) {

    }

    public static void findAndHookMethod(String className,ClassLoader loader, String methodName,Object... parameterTypesAndCallback){


    }

    public static void findAndHookConstructor(Class clazz ,Object... parameterTypesAndCallback){

    }

    public static void findAndHookConstructor(String className,ClassLoader loader,Object... parameterTypesAndCallback){

    }

    public static void prepareHookMethod(Class clazz, String methodName, Object... parameterTypesAndCallback){

    }

    public static void prepareHookMethod(String className,ClassLoader loader, String methodName,Object... parameterTypesAndCallback){

    }

    public static void prepareHookConstructor(Class clazz ,Object... parameterTypesAndCallback){

    }
    public static void prepareHookConstructor(String className,ClassLoader loader,Object... parameterTypesAndCallback){

    }

    public static void hookAllPrepared(){
        BudBridge.hookAllPrepared();
    }

}