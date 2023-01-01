package com.lin.common.util;

import com.lin.common.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;


@Slf4j
public class ConvertData {

    public static Object ConvertDataIntoObj(JsonResult jsonResult,Class clazz) {
        Object target = null;
        try {
            target = clazz.newInstance();
            BeanUtils.copyProperties(jsonResult.getData(),target);
        } catch (InstantiationException e) {
            e.getStackTrace();
            log.error(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return target;
    }

    public static int ConvertDataIntoInt(JsonResult jsonResult) {
        Object data = jsonResult.getData();
        return (Integer)data;
    }


    public static String ConvertDataIntoString(JsonResult jsonResult) {
        Object data = jsonResult.getData();
        return (String)data;
    }

    public static boolean isResultIllegal(JsonResult jsonResult){
        return jsonResult.getSuccess();
    }
}
