package com.lin.web.controller;


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
                                          @RequestParam("curPage")Integer curPage
                                        , @RequestParam("limit")Integer limit       ){
        return userService.listUserFans(token,uid,did,curPage,limit);
    }

    @PostMapping(value = "/listUserSubscribe")
    public JsonResult listUserSubscribe(@RequestHeader(required = false,value = "token")String token,
                                        @RequestHeader(required = true,value = "uid")String uid,
                                        @RequestHeader(required = false,value = "did")String did,
                                        @RequestParam("curPage")Integer curPage
                                         ,@RequestParam("limit")Integer limit ){
        return userService.listUserSubscribe(token,uid,did,curPage,limit);
    }


    @PostMapping(value = "/follow")
    public JsonResult follow(@RequestHeader(required = false,value = "token")String token,
                             @RequestHeader(required = true,value = "uid")String uid,
                             @RequestHeader(required = false,value = "did")String did,
                             @RequestParam("otherUID")String otherUID ){
        return userService.follow(token,uid,did,otherUID);
    }

    @PostMapping(value = "/cancleFollow")
    public JsonResult cancleFollow(@RequestHeader(required = false,value = "token")String token,
                                   @RequestHeader(required = true,value = "uid")String uid,
                                   @RequestHeader(required = false,value = "did")String did,
                                   @RequestParam("otherUID")String otherUID ){
        return userService.cancleFollow(token,uid,did,otherUID);
    }


    @PostMapping(value = "/isFriend")
    public JsonResult isFriend(@RequestHeader(required = false,value = "token")String token,
                               @RequestHeader(required = true,value = "uid")String uid,
                               @RequestHeader(required = false,value = "did")String did,
                               @RequestParam("otherUID")String otherUID){
        return userService.isFriend(token,uid,did,otherUID);
    }


    @PostMapping(value = "/countInfo")
    public JsonResult countInfo(@RequestHeader(required = false,value = "token")String token,
                                @RequestHeader(required = true,value = "uid")String uid,
                                @RequestHeader(required = false,value = "did")String did){
        return userService.countInfo(token,uid,did);
    }

}
