package com.lin.user.util;

import com.lin.common.result.JsonResult;
import com.lin.common.util.ConvertData;
import com.lin.storage.constant.KeyType;
import com.lin.user.entity.UserDetail;

public class Key {

    public static String generateKey(String uid,String key){
        return uid + "-"  + key;
    }


    public static boolean isValidToken(String token){
        return true;
    }
}
