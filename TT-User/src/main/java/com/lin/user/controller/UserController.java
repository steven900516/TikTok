package com.lin.user.controller;


import com.lin.common.result.JsonResult;
import com.lin.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/detail")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserDetail")
    public JsonResult getUserDetail(@RequestParam("token")String token, @RequestParam("uid")String uid,@RequestParam("did")String did){
        return userService.getUserDetail(token, uid, did);
    }


    @GetMapping("/setUserDetail")
    public JsonResult setUserDetail(@RequestParam("token")String token, @RequestParam("uid")String uid,@RequestParam("did")String did){
        return userService.getUserDetail(token, uid, did);
    }
}
