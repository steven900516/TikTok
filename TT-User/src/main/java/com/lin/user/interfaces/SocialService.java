package com.lin.user.interfaces;


import com.lin.common.result.JsonResult;
import com.lin.user.entity.UserCommon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("tt-social-service")
public interface SocialService {

    @ResponseBody
    @PostMapping(value = "/tiktok/api/social/user/registUserNodeToGraph",consumes = "application/json")
    public JsonResult registUserNodeToGraph(@RequestBody UserCommon userCommon);

    @ResponseBody
    @PostMapping(value = "/tiktok/api/social/user/updateUserNodeName",consumes = "application/json")
    public JsonResult updateUserNodeName(@RequestBody UserCommon userCommon,@RequestParam("newName")String newName);

    @ResponseBody
    @PostMapping(value = "/tiktok/api/social/user/listUserFans",consumes = "application/json")
    public JsonResult listUserFans(@RequestBody UserCommon userCommon,@RequestParam("curPage")Integer curPage
            ,@RequestParam("limit")Integer limit);


    @ResponseBody
    @PostMapping(value = "/tiktok/api/social/user/listUserSubscribe",consumes = "application/json")
    public JsonResult listUserSubscribe(@RequestBody UserCommon userCommon,@RequestParam("curPage")Integer curPage
            ,@RequestParam("limit")Integer limit);


    @PostMapping(value = "/tiktok/api/social/user/follow")
    public JsonResult follow(@RequestParam("ownUID")String ownUID,@RequestParam("otherUID")String otherUID);


    @PostMapping(value = "/tiktok/api/social/user/cancleFollow")
    public JsonResult cancleFollow(@RequestParam("ownUID")String ownUID,@RequestParam("otherUID")String otherUID);

    @PostMapping(value = "/tiktok/api/social/user/isFriend")
    public JsonResult isFriend(@RequestParam("ownUID")String ownUID,@RequestParam("otherUID")String otherUID);
}
