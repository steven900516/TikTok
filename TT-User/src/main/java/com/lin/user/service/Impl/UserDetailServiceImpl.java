package com.lin.user.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.lin.common.result.JsonResult;
import com.lin.common.result.ResultCode;
import com.lin.common.result.ResultTool;
import com.lin.common.util.ConvertData;
import com.lin.common.util.SerializeUtils;
import com.lin.storage.constant.KeyType;
import com.lin.user.entity.School;
import com.lin.user.entity.UserDetail;
import com.lin.user.interfaces.RedisService;
import com.lin.user.service.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.lin.user.util.Key.generateKey;
import static com.lin.user.util.Key.isValidToken;


@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private RedisService redisService;


    @Override
    public JsonResult getUserDetail(String token, String uid, String did) {
        // 1. 校验token是否合理，是否登录
        if (!isValidToken(token)){
            return ResultTool.fail(ResultCode.TOKEN_ERROR);
        }

        // 2. 去Redis存储服务拿信息 3787398274UserDetail
        UserDetail userDetail = getUserDetail(uid);
        // 成功查询
        if (userDetail != null){
            return ResultTool.success(userDetail);
        }


        // 3. 查询Redis 有问题，进行兜底
        log.error("user = {} ,userDetail is null",uid);
        return ResultTool.success(new UserDetail());
    }

    @Override
    public JsonResult setUserDetailName(String token, String uid, String did, String newName) {
        // 检验token
        if (!isValidToken(token)){
            return ResultTool.fail(ResultCode.TOKEN_ERROR);
        }

        // 检验是否30天内修改1次,record:3787398274UserDetail-Name
        String recordKey = generateKey(uid,com.lin.user.constant.Service.User_Detail_Record_Name_Key);
        if (redisService.getKV(com.lin.user.constant.Service.Service_Name,recordKey,KeyType.Record_Int_type).getData() != null) {
            return ResultTool.fail(ResultCode.USER_DETAIL_NAME_HAS_CHANGED);
        }

        String storageKey = generateKey(uid,com.lin.user.constant.Service.User_Detail_Storage_Key);
        UserDetail userDetail = getUserDetail(uid);
        if (userDetail == null){
            log.error("user = {} ,userDetail is null",uid);
            return ResultTool.fail(ResultCode.USER_DETAIL_IS_NULL);
        }
        userDetail.setName(newName);
        // 设置一个月有效期
        JsonResult recordResult = redisService.setKVWithExpire(com.lin.user.constant.Service.Service_Name, recordKey, "1", 2592000L, KeyType.Record_Int_type);
        if (!ConvertData.isResultIllegal(recordResult)){
            log.error("recordKey: {} set_user_detail_record_name fail,newName : {}",recordKey,newName);
            return recordResult;
        }
        JsonResult storageResult = null;
        try {
            storageResult = redisService.setKVWithoutExpire(com.lin.user.constant.Service.Service_Name, storageKey, SerializeUtils.serialize(userDetail), KeyType.Storage_Int_type);
        }catch (Exception e){
            log.error("setUserDetailName_serialize_fail",e);
            return ResultTool.fail();
        }

        if (!ConvertData.isResultIllegal(storageResult)){
            log.error("set_user_detail_storage_name fail,storageKey={},newName={}",storageKey,newName);
            return storageResult;
        }
        log.info("set_user_detail_name both success, storageKey={}, recordKey={}, newName={}",storageKey,recordKey, newName);
        return recordResult;
    }

    @Override
    public JsonResult setUserDetailIntroduction(String token, String uid, String did, String newIntroduction) {
        // 检验token
        if (!isValidToken(token)){
            return ResultTool.fail(ResultCode.TOKEN_ERROR);
        }

        UserDetail userDetail = getUserDetail(uid);
        if (userDetail == null){
            log.error("user = {} ,userDetail is null",uid);
            return ResultTool.fail(ResultCode.USER_DETAIL_IS_NULL);
        }
        userDetail.setIntroduction(newIntroduction);

        return setUserDetail(userDetail,uid);
    }

    @Override
    public JsonResult setUserDetailGender(String token, String uid, String did, String newGender) {
        // 检验token
        if (!isValidToken(token)){
            return ResultTool.fail(ResultCode.TOKEN_ERROR);
        }
        UserDetail userDetail = getUserDetail(uid);
        if (userDetail == null){
            log.error("user = {} ,userDetail is null",uid);
            return ResultTool.fail(ResultCode.USER_DETAIL_IS_NULL);
        }
        userDetail.setGender(newGender);

        return setUserDetail(userDetail,uid);
    }

    @Override
    public JsonResult setUserDetailBirth(String token, String uid, String did, LocalDate newDate) {
        // 检验token
        if (!isValidToken(token)){
            return ResultTool.fail(ResultCode.TOKEN_ERROR);
        }

        UserDetail userDetail = getUserDetail(uid);
        if (userDetail == null){
            log.error("user = {} ,userDetail is null",uid);
            return ResultTool.fail(ResultCode.USER_DETAIL_IS_NULL);
        }
        userDetail.setBirth(newDate);

        return setUserDetail(userDetail,uid);
    }

    @Override
    public JsonResult setUserDetailLocate(String token, String uid, String did, String locate) {
        // 检验token
        if (!isValidToken(token)){
            return ResultTool.fail(ResultCode.TOKEN_ERROR);
        }

        UserDetail userDetail = getUserDetail(uid);
        if (userDetail == null){
            log.error("user = {} ,userDetail is null",uid);
            return ResultTool.fail(ResultCode.USER_DETAIL_IS_NULL);
        }
        userDetail.setLocate(locate);

        return setUserDetail(userDetail,uid);
    }

    @Override
    public JsonResult setUserDetailSchool(String token, String uid, String did, School school) {
        // 检验token
        if (!isValidToken(token)){
            return ResultTool.fail(ResultCode.TOKEN_ERROR);
        }

        UserDetail userDetail = getUserDetail(uid);
        if (userDetail == null){
            log.error("user = {} ,userDetail is null",uid);
            return ResultTool.fail(ResultCode.USER_DETAIL_IS_NULL);
        }
        userDetail.setSchoolInfo(school);

        return setUserDetail(userDetail,uid);
    }

    @Override
    public JsonResult setUserDetailTTAccount(String token, String uid, String did, String newTtAccount) {
        // 检验token
        if (!isValidToken(token)){
            return ResultTool.fail(ResultCode.TOKEN_ERROR);
        }
        // 记录存在
        String recordKey = generateKey(uid, com.lin.user.constant.Service.User_Detail_Record_ttAcount_Key);
        if (redisService.getKV(com.lin.user.constant.Service.Service_Name,recordKey,KeyType.Record_Int_type).getData() != null){
            log.error("ttAccount has changed in 180 days,uid = {}",uid);
            return ResultTool.fail(ResultCode.USER_DETAIL_TTACOUNT_HAS_CHANGED);
        }
        // 抖音号存在
        String storageKey = generateKey(newTtAccount, com.lin.user.constant.Service.TtAccount_Value_Record_Key);
        if (redisService.getKV(com.lin.user.constant.Service.Service_Name,storageKey,KeyType.Record_Int_type).getData() != null){
            log.error("USER_DETAIL_TTACOUNT_EXITST,uid = {},newTTAccount = {}",uid,newTtAccount);
            return ResultTool.fail(ResultCode.USER_DETAIL_TTACOUNT_EXITST);
        }

        UserDetail userDetail = getUserDetail(uid);
        if (userDetail == null){
            log.error("user = {} ,userDetail is null",uid);
            return ResultTool.fail(ResultCode.USER_DETAIL_IS_NULL);
        }

        String oldStorageKey = generateKey(userDetail.getTtAccount(), com.lin.user.constant.Service.TtAccount_Value_Record_Key);
        JsonResult oldTtAccountJson = redisService.deleteKV(com.lin.user.constant.Service.Service_Name, oldStorageKey, KeyType.Record_Int_type);
        if (!ConvertData.isResultIllegal(oldTtAccountJson)){
            log.error("delete_user_detail_storage_tt_Account fail,storageKey={},newAccount={}",storageKey,newTtAccount);
            return oldTtAccountJson;
        }

        JsonResult setNewAccountRecordJson = redisService.setKVWithoutExpire(com.lin.user.constant.Service.Service_Name, storageKey, "1", KeyType.Record_Int_type);
        if (!ConvertData.isResultIllegal(setNewAccountRecordJson)){
            log.error("set_user_detail_storage_new_tt_Account fail,storageKey={},newAccount={}",storageKey,newTtAccount);
            return oldTtAccountJson;
        }

        userDetail.setTtAccount(newTtAccount);
        // 半年修改一次 180天
        JsonResult storageResult = setUserDetail(userDetail, uid);
        if (!ConvertData.isResultIllegal(storageResult)){
            log.error("storageKey: {} set_user_detail_storage_tt_Account fail,newAccount : {}",storageKey,newTtAccount);
            return storageResult;
        }
        JsonResult recordResult = redisService.setKVWithExpire(com.lin.user.constant.Service.Service_Name, recordKey, "1", 15552000L, KeyType.Record_Int_type);
        if (!ConvertData.isResultIllegal(recordResult)){
            log.error("recordKey: {} set_user_detail_record_tt_Account fail,newAccount : {}",recordKey,newTtAccount);
            return recordResult;
        }


        log.info("storageKey: {}, recordKey : {} . set_user_detail_tt_Account both success  , newAccount : {}",storageKey,recordKey, newTtAccount);
        return recordResult;
    }




    private UserDetail getUserDetail(String uid){
        String key = generateKey(uid , com.lin.user.constant.Service.User_Detail_Storage_Key);
        UserDetail userDetail = null;
        JsonResult jsonResult = redisService.getKV(com.lin.user.constant.Service.Service_Name, key, KeyType.Storage_Int_type);

        try {
            userDetail = (UserDetail) SerializeUtils.serializeToObject(jsonResult.getData());
        }catch (Exception e){
            log.error("UserDetail_unserialize_fail,uid={}",uid);
            return null;
        }
        log.info("getUserDetail,key = {},jsonResult = {},userDetail:{}",key,jsonResult,userDetail);
        return userDetail == null ? new UserDetail() : userDetail;
    }


    private JsonResult setUserDetail(UserDetail userDetail,String uid){
        JsonResult res = null;
        try {
            String key = generateKey(uid , com.lin.user.constant.Service.User_Detail_Storage_Key);
            res = redisService.setKVWithoutExpire(com.lin.user.constant.Service.Service_Name, key, SerializeUtils.serialize(userDetail), KeyType.Storage_Int_type);
            if (!ConvertData.isResultIllegal(res)){
                log.error("setUserDetail_fail,key = {},uid={},userDetail={}",key,uid,userDetail);
                return res;
            }
        } catch (Exception e){
            log.error("setUserDetail_fail,uid={},userDetail={}",uid,userDetail);
            return ResultTool.fail(ResultCode.USER_DETAIL_IS_NULL);
        }


        log.info("setUserDetail_success ,uid={},userDetail={}",uid,userDetail);
        return res;
    }


}
