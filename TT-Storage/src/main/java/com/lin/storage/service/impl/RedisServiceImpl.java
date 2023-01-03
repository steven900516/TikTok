package com.lin.storage.service.impl;

import com.lin.common.result.JsonResult;
import com.lin.common.result.ResultCode;
import com.lin.common.result.ResultTool;
import com.lin.common.util.SerializeUtils;
import com.lin.storage.constant.KeyType;
import com.lin.storage.service.RedisService;
import com.lin.storage.util.RedisUtil;
import com.lin.storage.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.pqc.crypto.ExchangePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisUtil redisUtil;


    @Override
    public JsonResult setRedisKVWithoutExpire(String serviceName, String key, Object value,Integer type) {
        if (!type.equals(KeyType.Record_Int_type) && !type.equals(KeyType.Storage_Int_type)){
            return ResultTool.fail(ResultCode.REDIS_TYPE_ERROR);
        }
        String newKey = StringUtil.CombineKey(serviceName, key,type);
        try {

            boolean isSet = redisUtil.set(newKey, value);
            if (isSet) {
                return ResultTool.success();
            }
            return ResultTool.fail();
        }catch (Exception e){
            e.printStackTrace();
            return ResultTool.fail();
        }
    }


    @Override
    public JsonResult setRedisKVWithExpire(String serviceName, String key, Object value,Long second,Integer type) {
        if (!type.equals(KeyType.Record_Int_type) && !type.equals(KeyType.Storage_Int_type)){
            return ResultTool.fail(ResultCode.REDIS_TYPE_ERROR);
        }
        String newKey = StringUtil.CombineKey(serviceName, key,type);
        try {
            boolean isSet = redisUtil.set(newKey, value);
            boolean isExpire = redisUtil.setExpire(newKey,second);
            if (isSet && isExpire) {
                return ResultTool.success();
            }
            return ResultTool.fail();
        }catch (Exception e){
            e.printStackTrace();
            return ResultTool.fail();
        }
    }

    @Override
    public JsonResult getRedisObject(String serviceName, String key,Integer type) {
        String newKey;
        Object obj;
        try {
            newKey = StringUtil.CombineKey(serviceName,key,type);
            obj = redisUtil.get(newKey);
        }catch (Exception e){
            e.printStackTrace();
            return ResultTool.fail();
        }

        return ResultTool.success(obj);
    }

    @Override
    public JsonResult deleteRedisKey(String serviceName, String key, Integer type) {
        String newKey;
        Object obj;
        try {
            newKey = StringUtil.CombineKey(serviceName,key,type);
            boolean del = redisUtil.del(newKey);
            if (del) {
                return ResultTool.success();
            }
            return ResultTool.fail();
        }catch (Exception e){
            e.printStackTrace();
            return ResultTool.fail();
        }
    }
}
