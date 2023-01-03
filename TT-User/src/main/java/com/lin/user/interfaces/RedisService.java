package com.lin.user.interfaces;


import com.lin.common.result.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("tt-storage-service") // nacos 服务 id
public interface RedisService {

    @PostMapping("/tiktok/api/storage/redis/setKVWithoutExpire")
    public JsonResult setKVWithoutExpire(@RequestParam("serviceName")String serviceName, @RequestParam("key")String key, @RequestParam("object")Object object,@RequestParam("type")Integer type);


    @PostMapping("/tiktok/api/storage/redis/setKVWithExpire")
    public JsonResult setKVWithExpire(@RequestParam("serviceName")String serviceName, @RequestParam("key")String key, @RequestParam("object")Object object,@RequestParam("second")Long second,@RequestParam("type")Integer type);

    @GetMapping("/tiktok/api/storage/redis/getKV")
    public JsonResult getKV(@RequestParam("serviceName")String serviceName, @RequestParam("key")String key,@RequestParam("type")Integer type);

    @PostMapping("/tiktok/api/storage/redis/deleteKV")
    public JsonResult deleteKV(@RequestParam("serviceName")String serviceName, @RequestParam("key")String key,@RequestParam("type")Integer type);
}
