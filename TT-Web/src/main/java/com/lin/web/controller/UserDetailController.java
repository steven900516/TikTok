package com.lin.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.lin.common.result.JsonResult;
import com.lin.common.result.ResultCode;
import com.lin.common.result.ResultTool;
import com.lin.user.entity.School;
import com.lin.web.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/user")
public class UserDetailController {


    @Autowired
    UserService userService;


    @GetMapping("/getUserDetail")
    public JsonResult getUserDetail(@RequestHeader(required = false,value = "token")String token,
                                    @RequestHeader(required = true,value = "uid")String uid,
                                    @RequestHeader(required = false,value = "did")String did){
        return userService.getUserDetail(token, uid, did);
    }


    @PostMapping("/setUserDetailName")
    public JsonResult setUserDetailName(@RequestHeader(required = false,value = "token")String token,
                                        @RequestHeader(required = true,value = "uid")String uid,
                                        @RequestHeader(required = false,value = "did")String did,
                                        @RequestBody JSONObject body){
        String newName = body.getString("newName");
        return userService.setUserDetailName(token, uid, did,newName);
    }

    @PostMapping("/setUserDetailIntroduction")
    public JsonResult setUserDetailIntroduction(@RequestHeader(required = false,value = "token")String token,
                                        @RequestHeader(required = true,value = "uid")String uid,
                                        @RequestHeader(required = false,value = "did")String did,
                                        @RequestBody JSONObject body){
        String newIntroduction = body.getString("newIntroduction");
        return userService.setUserDetailIntroduction(token, uid, did,newIntroduction);
    }


    @PostMapping("/setUserDetailGender")
    public JsonResult setUserDetailGender(@RequestHeader(required = false,value = "token")String token,
                                                @RequestHeader(required = true,value = "uid")String uid,
                                                @RequestHeader(required = false,value = "did")String did,
                                                @RequestBody JSONObject body){
        String newGender = body.getString("newGender");
        return userService.setUserDetailGender(token, uid, did,newGender);
    }

    @PostMapping("/setUserDetailBirth")
    public JsonResult setUserDetailBirth(@RequestHeader(required = false,value = "token")String token,
                                          @RequestHeader(required = true,value = "uid")String uid,
                                          @RequestHeader(required = false,value = "did")String did,
                                          @RequestBody JSONObject body){
        String newBirth = body.getString("newBirth");
        LocalDate birth = null;
        try {
            birth = LocalDate.parse(newBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e){
            return ResultTool.fail(ResultCode.PARAM_TYPE_ERROR);
        }

        return userService.setUserDetailBirth(token, uid, did,birth);
    }

    @PostMapping("/setUserDetailLocate")
    public JsonResult setUserDetailLocate(@RequestHeader(required = false,value = "token")String token,
                                         @RequestHeader(required = true,value = "uid")String uid,
                                         @RequestHeader(required = false,value = "did")String did,
                                         @RequestBody JSONObject body){

        String newLocate = body.getString("newLocate");
        return userService.setUserDetailLocate(token, uid, did,newLocate);
    }

    @PostMapping("/setUserDetailSchool")
    public JsonResult setUserDetailSchool(@RequestHeader(required = false,value = "token")String token,
                                          @RequestHeader(required = true,value = "uid")String uid,
                                          @RequestHeader(required = false,value = "did")String did,
                                          @RequestBody School school){
        return userService.setUserDetailSchool(token, uid, did,school);
    }


    @PostMapping("/setUserDetailTTAccount")
    public JsonResult setUserDetailTTAccount(@RequestHeader(required = false,value = "token")String token,
                                          @RequestHeader(required = true,value = "uid")String uid,
                                          @RequestHeader(required = false,value = "did")String did,
                                          @RequestBody JSONObject body){
        String newTTAccount = body.getString("newTTAccount");
        return userService.setUserDetailTTAccount(token, uid, did,newTTAccount);
    }


}
