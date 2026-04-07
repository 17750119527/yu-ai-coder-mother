package com.yupi.yuaicodermother.exception;

public class ThrowUtils {
    /*
    * 条件成立则抛出异常
    * @param condition
    * @param runtimeException
    * */
    public static void throwIf(boolean condition,BussinessException runtimeException){
        if (condition){
            throw runtimeException;
        }
    }
    /*
     * 条件成立则抛出异常
     * @param condition
     * @param errorCode
     * */
    public static void throwIf(boolean condition,ErrorCode errorCode){
        throwIf(condition,new BussinessException(errorCode));
    }

    /*
     * 条件成立则抛出异常
     * @param condition
     * @param errorCode
     * @parma message
     * */
    public static void throwIf(boolean condition,ErrorCode errorCode,String message){
        throwIf(condition, new BussinessException(errorCode,message));
    }
}
