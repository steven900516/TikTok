package com.lin.social.controller;


import com.lin.common.result.JsonResult;
import com.lin.social.service.SocialService;
import com.lin.user.entity.UserCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class SocialController {
    @Autowired
    SocialService socialService;


    @ResponseBody
    @PostMapping(value = "/registUserNodeToGraph",produces = "application/json")
    public JsonResult registUserNodeToGraph(@RequestBody UserCommon userCommon){
        return socialService.registUserNodeToGraph(userCommon);
    }

    @ResponseBody
    @PostMapping(value = "/updateUserNodeName",produces = "application/json")
    public JsonResult updateUserNodeName(@RequestBody UserCommon userCommon,@RequestParam("newName")String newName){
        return socialService.updateUserNodeName(userCommon,newName);
    }

    @ResponseBody
    @PostMapping(value = "/listUserFans",produces = "application/json")
    public JsonResult listUserFans(@RequestBody UserCommon userCommon,@RequestParam("curPage")Integer curPage
                                   ,@RequestParam("limit")Integer limit){
        return socialService.listUserFans(userCommon,curPage,limit);
    }


    @ResponseBody
    @PostMapping(value = "/listUserSubscribe",produces = "application/json")
    public JsonResult listUserSubscribe(@RequestBody UserCommon userCommon,@RequestParam("curPage")Integer curPage
            ,@RequestParam("limit")Integer limit){
        return socialService.listUserSubscribe(userCommon,curPage,limit);
    }


    @PostMapping(value = "/follow")
    public JsonResult follow(@RequestParam("ownUID")String ownUID,@RequestParam("otherUID")String otherUID){
        return socialService.follow(ownUID,otherUID);
    }


    @PostMapping(value = "/cancleFollow")
    public JsonResult cancleFollow(@RequestParam("ownUID")String ownUID,@RequestParam("otherUID")String otherUID){
        return socialService.cancleFollow(ownUID,otherUID);
    }

    @PostMapping(value = "/isFriend")
    public JsonResult isFriend(@RequestParam("ownUID")String ownUID,@RequestParam("otherUID")String otherUID){
        return socialService.isFriend(ownUID,otherUID);
    }
}
