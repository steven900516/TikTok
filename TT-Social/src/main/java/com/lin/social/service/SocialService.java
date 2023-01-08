package com.lin.social.service;

import com.lin.common.result.JsonResult;
import com.lin.user.entity.UserCommon;

public interface SocialService {



    JsonResult registUserNodeToGraph(UserCommon userCommon);


    JsonResult updateUserNodeName(UserCommon userCommon,String newName);


    JsonResult listUserFans(UserCommon userCommon,int curPage,int limit);


    JsonResult listUserSubscribe(UserCommon userCommon,int curPage,int limit);


    JsonResult follow(String ownUID,String otherUID);


    JsonResult cancleFollow(String ownUID,String otherUID);


    JsonResult isFriend(String ownUID,String otherUID);
}
