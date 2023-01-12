package com.lin.user.service;

import com.lin.common.result.JsonResult;

public interface UserSocialService {


    JsonResult listUserFans(String token,String uid,String did,int curPage,int limit);


    JsonResult listUserSubscribe(String token,String uid,String did,int curPage,int limit);


    JsonResult follow(String token,String uid,String did,String otherUID);


    JsonResult cancleFollow(String token,String uid,String did,String otherUID);


    JsonResult isFriend(String token,String uid,String did,String otherUID);


    JsonResult countInfo(String token,String uid,String did);
}
