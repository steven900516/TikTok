package com.lin.storage.controller;


import com.lin.common.result.JsonResult;
import com.lin.storage.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    RedisService redisService;

    @PostMapping("/setKVWithoutExpire")
    public JsonResult setKVWithoutExpire(@RequestParam("serviceName")String serviceName,
                            @RequestParam("key")String key,@RequestParam("object")Object object
                                         ,@RequestParam("type")Integer type){
        return redisService.setRedisKVWithoutExpire(serviceName, key, object,type);
    }

    @PostMapping("/setKVWithExpire")
    public JsonResult setKVWithExpire(@RequestParam("serviceName")String serviceName,
                            @RequestParam("key")String key,@RequestParam("object")Object object,@RequestParam("second")Long second
                            ,@RequestParam("type")Integer type){
        return redisService.setRedisKVWithExpire(serviceName, key, object,second,type);
    }


    @GetMapping("/getKV")
    public JsonResult setKV(@RequestParam("serviceName")String serviceName,
                            @RequestParam("key")String key,@RequestParam("type")Integer type){
        return redisService.getRedisObject(serviceName, key,type);
    }


    @PostMapping("/deleteKV")
    public JsonResult deleteKV(@RequestParam("serviceName")String serviceName,
                            @RequestParam("key")String key,@RequestParam("type")Integer type){
        return redisService.deleteRedisKey(serviceName, key,type);
    }


}
