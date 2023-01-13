package com.lin.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.lin.common.result.JsonResult;
import com.lin.web.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserSocialController {
    
    @Autowired
    UserService userService;

    @PostMapping(value = "/listUserFans")
    public JsonResult setUserDetailSchool(@RequestHeader(required = false,value = "token")String token,
                                          @RequestHeader(required = true,value = "uid")String uid,
                                          @RequestHeader(required = false,value = "did")String did,
                                          @RequestBody JSONObject body){
        Integer curPage = body.getInteger("curPage");
        Integer limit = body.getInteger("limit");
        return userService.listUserFans(token,uid,did,curPage,limit);
    }

    @PostMapping(value = "/listUserSubscribe")
    public JsonResult listUserSubscribe(@RequestHeader(required = false,value = "token")String token,
                                        @RequestHeader(required = true,value = "uid")String uid,
                                        @RequestHeader(required = false,value = "did")String did,
                                        @RequestBody JSONObject body){
        Integer curPage = body.getInteger("curPage");
        Integer limit = body.getInteger("limit");
        return userService.listUserSubscribe(token,uid,did,curPage,limit);
    }


    @PostMapping(value = "/follow")
    public JsonResult follow(@RequestHeader(required = false,value = "token")String token,
                             @RequestHeader(required = true,value = "uid")String uid,
                             @RequestHeader(required = false,value = "did")String did,
                             @RequestBody JSONObject body){
        String otherUID = body.getString("otherUID");
        return userService.follow(token,uid,did,otherUID);
    }

    @PostMapping(value = "/cancleFollow")
    public JsonResult cancleFollow(@RequestHeader(required = false,value = "token")String token,
                                   @RequestHeader(required = true,value = "uid")String uid,
                                   @RequestHeader(required = false,value = "did")String did,
                                   @RequestBody JSONObject body){
        String otherUID = body.getString("otherUID");
        return userService.cancleFollow(token,uid,did,otherUID);
    }


    @PostMapping(value = "/isFriend")
    public JsonResult isFriend(@RequestHeader(required = false,value = "token")String token,
                               @RequestHeader(required = true,value = "uid")String uid,
                               @RequestHeader(required = false,value = "did")String did,
                               @RequestBody JSONObject body){
        String otherUID = body.getString("otherUID");
        return userService.isFriend(token,uid,did,otherUID);
    }


    @PostMapping(value = "/countInfo")
    public JsonResult countInfo(@RequestHeader(required = false,value = "token")String token,
                                @RequestHeader(required = true,value = "uid")String uid,
                                @RequestHeader(required = false,value = "did")String did){
        return userService.countInfo(token,uid,did);
    }

}
