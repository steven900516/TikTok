package com.lin.storage.service;


import com.lin.common.result.JsonResult;

public interface RedisService {

    JsonResult  setRedisKVWithoutExpire(String serviceName,String key,Object value,Integer type);

    JsonResult  setRedisKVWithExpire(String serviceName,String key,Object value,Long second,Integer type);

    JsonResult getRedisObject(String serviceName,String key,Integer type);

    JsonResult deleteRedisKey(String serviceName,String key,Integer type);
}
