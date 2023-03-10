package com.lin.user.service.Impl;


import com.lin.common.result.JsonResult;
import com.lin.common.result.ResultCode;
import com.lin.common.result.ResultTool;
import com.lin.common.util.ConvertData;
import com.lin.common.util.SerializeUtils;
import com.lin.storage.constant.KeyType;
import com.lin.user.constant.Neo4j;
import com.lin.user.entity.CountInfo;
import com.lin.user.entity.UserCommon;
import com.lin.user.interfaces.RedisService;
import com.lin.user.interfaces.SocialService;
import com.lin.user.service.UserSocialService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.lin.user.util.Key.generateKey;

@Slf4j
@Service
public class UserSocialServiceImpl implements UserSocialService {

    @Autowired
    RedisService redisService;


    @Autowired
    SocialService socialService;


    @Override
    public JsonResult listUserFans(String token, String uid, String did, int curPage, int limit) {
        UserCommon userCommon = getUserCommon(uid);
        if (userCommon == null) {
            log.error("userCommon is null,uid : {}",uid);
            return ResultTool.fail(ResultCode.USER_COMMON_IS_NULL);
        }
        return socialService.listUserFans(userCommon,curPage,limit);
    }

    @Override
    public JsonResult listUserSubscribe(String token, String uid, String did, int curPage, int limit) {
        UserCommon userCommon = getUserCommon(uid);
        if (userCommon == null) {
            log.error("userCommon is null,uid : {}",uid);
            return ResultTool.fail(ResultCode.USER_COMMON_IS_NULL);
        }
        return socialService.listUserSubscribe(userCommon,curPage,limit);
    }

    @Override
    public JsonResult follow(String token, String uid, String did, String otherUID) {

        return socialService.follow(uid,otherUID);
    }

    @Override
    public JsonResult cancleFollow(String token, String uid, String did, String otherUID) {
        return socialService.cancleFollow(uid,otherUID);
    }

    @Override
    public JsonResult isFriend(String token, String uid, String did, String otherUID) {
        return socialService.isFriend(uid,otherUID);
    }

    @Override
    public JsonResult countInfo(String token, String uid, String did) {
        String loveCountKey = generateKey(uid, Neo4j.Loves_Count_Totol);
        String friendCountKey = generateKey(uid, Neo4j.Friend_Count_Total);
        String followCountKey = generateKey(uid, Neo4j.Follow_Count_Total);
        String fansCountKey = generateKey(uid, Neo4j.Fans_Count_Total);
        CountInfo countInfo = null;
        try {
            Integer loveCount = Integer.parseInt((String)redisService.getKV(Neo4j.Service_Name, loveCountKey, KeyType.Storage_Int_type).getData());
            Integer friendCount = Integer.parseInt((String)redisService.getKV(Neo4j.Service_Name, friendCountKey, KeyType.Storage_Int_type).getData());
            Integer followCount = Integer.parseInt((String)redisService.getKV(Neo4j.Service_Name, followCountKey, KeyType.Storage_Int_type).getData());
            Integer fansCount = Integer.parseInt((String)redisService.getKV(Neo4j.Service_Name, fansCountKey, KeyType.Storage_Int_type).getData());
            countInfo = new CountInfo(loveCount,friendCount,followCount,fansCount);
        }catch (Exception e) {
            log.error("countInfo_get_fail",e);
            return ResultTool.success(new CountInfo());
        }

        return ResultTool.success(countInfo);
    }


    public UserCommon getUserCommon(String uid){
        UserCommon userCommon = null;
        String userCommonKey = generateKey(uid, com.lin.user.constant.Service.User_Common_Storage_Key);
        JsonResult kv = redisService.getKV(com.lin.user.constant.Service.Service_Name, userCommonKey, KeyType.Storage_Int_type);
        if (!ConvertData.isResultIllegal(kv)){
            log.error("get_userCommon fail,userCommonKey={},result={}",userCommonKey,kv.getData());
            return null;
        }
        try {
            userCommon = (UserCommon) SerializeUtils.serializeToObject(kv.getData());
        }catch (Exception e){
            log.error("userCommon unserialize fail",e);
            return null;
        }

        return userCommon;
    }
}
