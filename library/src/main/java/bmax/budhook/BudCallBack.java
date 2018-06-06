package bmax.budhook;

import java.lang.reflect.Member;


/**
 * Created by bmax on 2018/4/8.
 */

public abstract class BudCallBack {

    public static final String desc = "Lbmax/budhook/BudCallBack;";
    public static final String beforeCallMethodName = "beforeCall";
    public static final String afterCallMethodName = "afterCall";

    public void beforeCall(MethodHookParams params) {


    }

    public void afterCall(MethodHookParams params) {

    }

    //public void replaceCall(MethodHookParams params){
        // setResult()
    //}
    
    public static class MethodHookParams {

        public static final String desc = "Lbmax/budhook/BudCallBack$MethodHookParams;";

        public static final String methodFieldName = "method";
        public static final String thisObjectFieldName = "thisObject";
    	public static final String argsFieldName = "args";
    	public static final String resultFieldName = "result";
    	public static final String throwableFieldName = "throwable";
        public static final String isEarlyReturnMethodName = "isEarlyReturn";

        public Member method = null;
        public Object thisObject = null;
        public Object[] args = null;
        public Object result = null;
        public Throwable throwable = null;
        private boolean earlyReturn = false;

        public void setResult(Object result) {
            this.result = result;
            this.earlyReturn = true;
        }
        public boolean isEarlyReturn() {
            return this.earlyReturn;
        }
    }
}
