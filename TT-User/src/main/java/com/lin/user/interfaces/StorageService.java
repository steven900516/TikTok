package com.lin.user.interfaces;


import com.lin.common.result.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("tt-storage-service") // nacos 服务 id
public interface StorageService {

    @PostMapping("/tiktok/api/storage/redis/setKV")
    public JsonResult setKV(@RequestParam("serviceName")String serviceName, @RequestParam("key")String key, @RequestParam("object")Object object);


    @GetMapping("/tiktok/api/storage/redis/getKV")
    public JsonResult getKV(@RequestParam("serviceName")String serviceName, @RequestParam("key")String key);

}
