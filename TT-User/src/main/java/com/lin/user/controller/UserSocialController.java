package com.lin.user.controller;


import com.lin.common.result.JsonResult;
import com.lin.user.entity.School;
import com.lin.user.interfaces.SocialService;
import com.lin.user.service.UserSocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userSocial")
public class UserSocialController {

    @Autowired
    UserSocialService userSocialService;


    @PostMapping(value = "/listUserFans")
    public JsonResult listUserFans(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                          @RequestParam("did")String did, @RequestParam("curPage")Integer curPage
                                            ,@RequestParam("limit")Integer limit       ){
        return userSocialService.listUserFans(token,uid,did,curPage,limit);
    }

    @PostMapping(value = "/listUserSubscribe")
    public JsonResult listUserSubscribe(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                          @RequestParam("did")String did, @RequestParam("curPage")Integer curPage
            ,@RequestParam("limit")Integer limit ){
        return userSocialService.listUserSubscribe(token,uid,did,curPage,limit);
    }


    @PostMapping(value = "/follow")
    public JsonResult follow(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                        @RequestParam("did")String did, @RequestParam("otherUID")String otherUID ){
        return userSocialService.follow(token,uid,did,otherUID);
    }

    @PostMapping(value = "/cancleFollow")
    public JsonResult cancleFollow(@RequestParam("token")String token, @RequestParam("uid")String uid,
                             @RequestParam("did")String did, @RequestParam("otherUID")String otherUID ){
        return userSocialService.cancleFollow(token,uid,did,otherUID);
    }


    @PostMapping(value = "/isFriend")
    public JsonResult isFriend(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                   @RequestParam("did")String did,@RequestParam("otherUID")String otherUID){
        return userSocialService.isFriend(token,uid,did,otherUID);
    }


    @PostMapping(value = "/countInfo")
    public JsonResult countInfo(@RequestParam("token")String token, @RequestParam("uid")String uid,
                               @RequestParam("did")String did){
        return userSocialService.countInfo(token,uid,did);
    }

}
