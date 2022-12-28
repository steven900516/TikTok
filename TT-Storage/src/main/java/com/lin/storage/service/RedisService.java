package com.lin.storage.service;


import com.lin.common.result.JsonResult;

public interface RedisService {

    JsonResult  setRedisKV(String serviceName,String key,Object value);


    JsonResult getRedisObject(String serviceName,String key);


}
