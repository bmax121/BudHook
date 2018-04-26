package bmax.budhook;

import android.content.Context;

import com.lody.turbodex.TurboDex;

import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bmax.budhook.classfactory.GenClasses;
import bmax.budhook.classfactory.GenedClassInfo;
import bmax.budhook.classfactory.HookedMethodInfo;
import dalvik.system.DexClassLoader;

public final class BudBridge {

	private static HashMap<Member,GenedClassInfo> hookedInfo = new HashMap<>();
	private static HashMap<Member,BudCallBack> prepareHookCache = new HashMap<>();

	private static final String prefix = "LBudHook/GenedClass_";
	private static int suffix = 0;

	private static Context context = null;

	public static void init(Context context){
		BudBridge.context = context;
    }

	static {
	    System.loadLibrary("yahfa");
        yahfaInit(android.os.Build.VERSION.SDK_INT);
	    System.loadLibrary("turbo-dex");
        TurboDex.enableTurboDex();
	}

	public static void addPrepareHook(Member member,BudCallBack budCallBack){
	    prepareHookCache.put(member,budCallBack);
    }

    public static void hookAllPrepared(){
	    hookManyMethod(prepareHookCache);
	    prepareHookCache.clear();
    }

	public static synchronized void hookManyMethod(HashMap<Member,BudCallBack> membersAndCallBacks){

        ArrayList<GenedClassInfo> genedClassInfos = new ArrayList<>();
	    for(Map.Entry<Member,BudCallBack> entry : membersAndCallBacks.entrySet()){
            if( hookedInfo.containsKey(entry.getKey())){
                BudLog.v("already hook method:" + entry.getKey().toString());
                continue;
            }
            if( ! checkMember(entry.getKey())) continue;
            HookedMethodInfo hookedMethodInfo = new HookedMethodInfo(entry.getKey(),entry.getValue());
            GenedClassInfo genedClassInfo = new GenedClassInfo(prefix + suffix ++ + ";",hookedMethodInfo);
            genedClassInfos.add(genedClassInfo);
        }

        byte[] bytes = GenClasses.genManyClassesDexBytes(genedClassInfos);
        String dexFileName = "BudHook" + suffix + ".dex";
        String dexPath = "/data/data/" + context.getPackageName() + "/files/" + dexFileName;


        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(dexFileName, Context.MODE_PRIVATE);
            fos.write(bytes);
        } catch (Exception e) {
            BudLog.e("error occur when write dex");
            return;
        }


        DexClassLoader dexClassLoader = new DexClassLoader(dexPath,context.getCodeCacheDir().getAbsolutePath(),null,context.getClassLoader() );
        for(GenedClassInfo genedClassInfo : genedClassInfos){
            if( ! genedClassInfo.initGenedClass(dexClassLoader) ){
                BudLog.e("can not load or init generated class:" + genedClassInfo.toString());
                continue;
            }

            Member m = genedClassInfo.getHookedMethodInfo().getMember();
            Method replace = genedClassInfo.getReplace();
            Method backup = genedClassInfo.getBackup();

            yahfaHook(m,replace,backup);

            hookedInfo.put(m,genedClassInfo);
        }

	}

	public static synchronized void hookMethod(Member hookMethod, BudCallBack callBack) {

        if( ! checkMember(hookMethod)) return;

		if( hookedInfo.containsKey(hookMethod)){
            BudLog.v("already hook method:" + hookMethod.toString());
            return;
       	}

        HookedMethodInfo hookedMethodInfo = new HookedMethodInfo(hookMethod,callBack);
        GenedClassInfo genedClassInfo = new GenedClassInfo(prefix + suffix ++ + ";",hookedMethodInfo);

        byte[] dexbytes = GenClasses.genOneClassDexBytes(genedClassInfo);
        String dexFileName = "BudHook" + suffix + ".dex";
        String dexPath = "/data/data/" + context.getPackageName() + "/files/" + dexFileName;


        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(dexFileName, Context.MODE_PRIVATE);
            fos.write(dexbytes);
        } catch (Exception e) {
            BudLog.e("error occur when write dex");
            return;
        }

        DexClassLoader dexClassLoader = new DexClassLoader(dexPath,context.getCodeCacheDir().getAbsolutePath(),null,context.getClassLoader() );
        if( ! genedClassInfo.initGenedClass(dexClassLoader) ){
            BudLog.e("error occured while load class:" + genedClassInfo.toString());
            return;
        }

        Method replace = genedClassInfo.getReplace();
        Method backup = genedClassInfo.getBackup();

        yahfaHook(hookMethod,replace,backup);

        hookedInfo.put(hookMethod,genedClassInfo);
    }

    private static boolean checkMember(Member member){

        if( member instanceof Method) {
            return true;
        } else if( member instanceof Constructor<?>) {
            return true;
        } else if (member.getDeclaringClass().isInterface()) {
            BudLog.e("Cannot hook interfaces: " + member.toString());
            return false;
        } else if (Modifier.isAbstract(member.getModifiers())) {
            BudLog.e("Cannot hook abstract methods: " + member.toString());
            return false;
        } else {
            BudLog.e("Only methods and constructors can be hooked: " + member.toString());
            return false;
        }
    }

	private static native void yahfaHook(Member hooked, Method replace, Method backup);
    private static native void yahfaInit(int sdkVersion);
}


