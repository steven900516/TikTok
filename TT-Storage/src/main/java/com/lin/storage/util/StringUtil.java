package com.lin.storage.util;

import com.lin.storage.constant.KeyType;

public class StringUtil {

    public static String CombineKey(String serviceName,String key,Integer type){
        if (type == KeyType.Storage_Int_type){
            return GenerateStorageKey(serviceName,key);
        }else if (type == KeyType.Record_Int_type){
            return GenerateRecordKey(serviceName,key);
        }
        return GenerateStorageKey(serviceName, key);
    }


    public static String GenerateStorageKey(String serviceName,String key){
        return "[" + serviceName + "]" + "-" + KeyType.Storage_Type + "-" +  key;
    }


    public static String GenerateRecordKey(String serviceName,String key){
        return "[" + serviceName + "]" + "-" + KeyType.Record_Type + "-" + key;
    }

}
