package bmax.budhook.classfactory;

import bmax.budhook.BudCallBack;
import bmax.budhook.BudLog;

/**
 * Created by bmax on 2018/4/9.
 */

public class GenedClassHelper {

	public static final String desc = "Lbmax/budhook/classfactory/GenedClassHelper;";
	public static final String getMethodHookParamsMethodName = "getMethodHookParams";
	public static final String getBudCallBackMethodName = "getBudCallBack";

	public static BudCallBack.MethodHookParams getMethodHookParams(Class<?> clazz) {


        try {
            return (BudCallBack.MethodHookParams) clazz.getField(GenedClassInfo.paramsFieldName).get(null);
        } catch (Exception e) {
            BudLog.e("unexpect error",e);
        }
        return null;

    }

	public static BudCallBack getBudCallBack(Class<?> clazz) {

		try {
			return (BudCallBack) clazz.getField(GenedClassInfo.callBackFieldName).get(null);
		} catch (Exception e) {
			BudLog.e("unexpect error",e);
		}

		return null;
	}

}
