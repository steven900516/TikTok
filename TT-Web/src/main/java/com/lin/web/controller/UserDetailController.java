package com.lin.web.controller;


import com.lin.common.result.JsonResult;
import com.lin.web.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserDetailController {


    @Autowired
    UserService userService;


    @RequestMapping("/getUserDetail")
    public JsonResult getUserDetail(@RequestParam("token")String token, @RequestParam("uid")String uid, @RequestParam("did")String did){
        return userService.getUserDetail(token, uid, did);
    }
}
