package com.lin.web.controller;


import com.lin.common.result.JsonResult;
import com.lin.user.service.UserCommonService;
import com.lin.web.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/registUserCommon")
    public JsonResult registUser(){
        return userService.registInitParam();
    }




}
