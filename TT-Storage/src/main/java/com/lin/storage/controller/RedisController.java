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

    @PostMapping("/setKV")
    public JsonResult setKV(@RequestParam("serviceName")String serviceName,
                            @RequestParam("key")String key,@RequestParam("object")Object object){
        return redisService.setRedisKV(serviceName, key, object);
    }


    @GetMapping("/getKV")
    public JsonResult setKV(@RequestParam("serviceName")String serviceName,
                            @RequestParam("key")String key){
        return redisService.getRedisObject(serviceName, key);
    }





}
