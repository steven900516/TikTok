package com.lin.user.service.Impl;

import com.lin.common.result.JsonResult;
import com.lin.common.result.ResultTool;
import com.lin.common.util.ConvertData;
import com.lin.common.util.IdGen;
import com.lin.storage.constant.KeyType;

import com.lin.user.entity.User;
import com.lin.user.entity.UserCommon;
import com.lin.user.entity.UserDetail;
import com.lin.user.interfaces.RedisService;
import com.lin.user.service.UserCommonService;
import com.lin.user.service.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.lin.user.util.Key.generateKey;


@Slf4j
@Service
public class UserCommonServiceImpl implements UserCommonService {

    @Autowired
    RedisService redisService;

    @Autowired
    UserDetailService userDetailService;


    @Override
    public JsonResult registInitCommonParam() {
        long uid = IdGen.genUID();
        long did = IdGen.genDID();
        String ttAccount = IdGen.getUUID();

        UserCommon userCommon = new UserCommon();
        userCommon.setUid(String.valueOf(uid));
        userCommon.setDid(String.valueOf(did));
        userCommon.setTtAccount(ttAccount);
        String userCommonKey = generateKey(String.valueOf(uid), com.lin.user.constant.Service.User_Common_Storage_Key);
        JsonResult registJson = redisService.setKVWithoutExpire(com.lin.user.constant.Service.Service_Name, userCommonKey, userCommon, KeyType.Storage_Int_type);
        if (!ConvertData.isResultIllegal(registJson)){
            log.error("regist_user_common_fail: key = {} , userCommon = {}",userCommonKey,userCommon);
            return registJson;
        }

        String userDetailKey = generateKey(String.valueOf(uid),com.lin.user.constant.Service.User_Detail_Storage_Key);
        UserDetail userDetail = new UserDetail();
        userDetail.setName(com.lin.user.constant.Service.User_Detail_Name_Init_Value);
        userDetail.setTtAccount(ttAccount);
        JsonResult userDetailJson = redisService.setKVWithoutExpire(com.lin.user.constant.Service.Service_Name, userDetailKey, userDetail, KeyType.Storage_Int_type);
        if (!ConvertData.isResultIllegal(userDetailJson)){
            log.error("regist_user_detail_fail: key = {} , userDetail = {}",userDetailKey,userDetail);
            return registJson;
        }

        String userKey = generateKey(String.valueOf(uid), com.lin.user.constant.Service.User_Key);
        User user = new User();
        user.setUserCommon(userCommon);
        user.setUserDetail(userDetail);
        JsonResult userJson = redisService.setKVWithoutExpire(com.lin.user.constant.Service.Service_Name, userKey, user, KeyType.Storage_Int_type);
        if (!ConvertData.isResultIllegal(userJson)){
            log.error("regist_user_detail_fail: key = {} , user = {}",userKey,user);
            return registJson;
        }
        return ResultTool.success(userCommon);
    }
}
