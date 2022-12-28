package com.lin.storage.util;

public class StringUtil {

    public static String CombineKey(String serviceName,String key){
        return "[" + serviceName + "]" + "-" + key;
    }

}
