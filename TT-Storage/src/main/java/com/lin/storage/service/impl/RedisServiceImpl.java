package com.lin.storage.service.impl;

import com.lin.common.result.JsonResult;
import com.lin.common.result.ResultTool;
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
    public JsonResult setRedisKV(String serviceName, String key, Object value) {
        String newKey = StringUtil.CombineKey(serviceName, key);
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
    public JsonResult getRedisObject(String serviceName, String key) {
        String newKey;
        Object obj;
        try {
            newKey = StringUtil.CombineKey(serviceName,key);
            obj = redisUtil.get(newKey);
        }catch (Exception e){
            e.printStackTrace();
            return ResultTool.fail();
        }

        return ResultTool.success(obj);
    }
}
