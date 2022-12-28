package com.lin.user.controller;


import com.lin.common.result.JsonResult;
import com.lin.user.interfaces.StorageService;
import com.lin.user.constant.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private StorageService storageService;

    @RequestMapping("/testSet")
    public JsonResult testSet(){
        return storageService.setKV(Service.Service_Name,"test01",new String("你好，，，"));
    }


    @RequestMapping("/testGet")
    public JsonResult testGet(){
        return storageService.getKV(Service.Service_Name,"test01");
    }
}
