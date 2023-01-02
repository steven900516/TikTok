package com.lin.user.service.Impl;

import com.lin.common.result.JsonResult;
import com.lin.common.result.ResultCode;
import com.lin.common.result.ResultTool;
import com.lin.common.util.ConvertData;
import com.lin.storage.constant.KeyType;
import com.lin.user.entity.School;
import com.lin.user.entity.UserDetail;
import com.lin.user.interfaces.RedisService;
import com.lin.user.service.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
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
        String key = generateKey(uid,com.lin.user.constant.Service.User_Detail_Storage_Key);
        JsonResult jsonResult = redisService.getKV(com.lin.user.constant.Service.Service_Name, key, KeyType.Storage_Int_type);
        // 成功查询
        if (ConvertData.isResultIllegal(jsonResult)){
            // 若查询用户Detail 信息为空，则创建新对象返回
            log.info("getUserDetail_success , uid = {}",uid);
            if (jsonResult.getData() == null){
                return ResultTool.success(new UserDetail());
            }
            return jsonResult;
        }


        // 3. 查询Redis 有问题，进行兜底
        log.error(jsonResult.getmessage() + ", code : {} ",jsonResult.getcode());
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
        if (redisService.getKV(com.lin.user.constant.Service.Service_Name,recordKey,KeyType.Record_Int_type) != null) {
            return ResultTool.fail(ResultCode.USER_DETAIL_NAME_HAS_CHANGED);
        }

        String storageKey = generateKey(uid,com.lin.user.constant.Service.User_Detail_Storage_Key);
        UserDetail userDetail = getUserDetail(uid);
        userDetail.setName(newName);
        // 设置一个月有效期
        JsonResult recordResult = redisService.setKVWithExpire(com.lin.user.constant.Service.Service_Name, recordKey, "1", 2592000L, KeyType.Record_Int_type);
        JsonResult storageResult = redisService.setKVWithoutExpire(com.lin.user.constant.Service.Service_Name, storageKey, userDetail, KeyType.Storage_Int_type);
        if (ConvertData.isResultIllegal(recordResult) && ConvertData.isResultIllegal(storageResult)){
            log.info("storageKey: {}, recordKey : {} . set_user_detail_name both success  , newName : {}",storageKey,recordKey, newName);
            return recordResult;
        }else{
            if (!ConvertData.isResultIllegal(recordResult)){
                log.error("recordKey: {} set_user_detail_record_name fail,newName : {}",recordKey,newName);
                return recordResult;
            }

            if (!ConvertData.isResultIllegal(storageResult)){
                log.error("storageKey: {} set_user_detail_storage_name fail,newName : {}",storageKey,newName);
                return storageResult;
            }
            return recordResult;
        }

    }

    @Override
    public JsonResult setUserDetailIntroduction(String token, String uid, String did, String newIntroduction) {
        // 检验token
        if (!isValidToken(token)){
            return ResultTool.fail(ResultCode.TOKEN_ERROR);
        }

        UserDetail userDetail = getUserDetail(uid);
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
        if (redisService.getKV(com.lin.user.constant.Service.Service_Name,recordKey,KeyType.Record_Int_type) != null){
            return ResultTool.fail(ResultCode.USER_DETAIL_TTACOUNT_HAS_CHANGED);
        }
        // 抖音号存在
        String storageKey = generateKey(uid, com.lin.user.constant.Service.User_Detail_Storage_Key);
        if (redisService.getKV(com.lin.user.constant.Service.Service_Name,storageKey,KeyType.Storage_Int_type) != null){
            return ResultTool.fail(ResultCode.USER_DETAIL_TTACOUNT_EXITST);
        }

        UserDetail userDetail = getUserDetail(uid);
        userDetail.setTtAccount(newTtAccount);

        // 半年修改一次 180天
        JsonResult storageResult = setUserDetail(userDetail, uid);
        JsonResult recordResult = redisService.setKVWithExpire(com.lin.user.constant.Service.Service_Name, recordKey, "1", 15552000L, KeyType.Record_Int_type);

        if (ConvertData.isResultIllegal(recordResult) && ConvertData.isResultIllegal(storageResult)){
            log.info("storageKey: {}, recordKey : {} . set_user_detail_tt_Account both success  , newAccount : {}",storageKey,recordKey, newTtAccount);
            return recordResult;
        }else{
            if (!ConvertData.isResultIllegal(recordResult)){
                log.error("recordKey: {} set_user_detail_record_tt_Account fail,newAccount : {}",recordKey,newTtAccount);
                return recordResult;
            }

            if (!ConvertData.isResultIllegal(storageResult)){
                log.error("storageKey: {} set_user_detail_storage_tt_Account fail,newAccount : {}",storageKey,newTtAccount);
                return storageResult;
            }
            return recordResult;
        }
    }




    private UserDetail getUserDetail(String uid){
        String key = generateKey(uid , com.lin.user.constant.Service.User_Detail_Storage_Key);

        JsonResult jsonResult = redisService.getKV(com.lin.user.constant.Service.Service_Name, key, KeyType.Storage_Int_type);
        UserDetail userDetail = (UserDetail)ConvertData.ConvertDataIntoObj(jsonResult, UserDetail.class);

        log.info("getUserDetail,key = {},jsonResult = {},userDetail:{}",key,jsonResult,userDetail);
        return userDetail == null ? new UserDetail() : userDetail;
    }


    private JsonResult setUserDetail(UserDetail userDetail,String uid){
        String key = generateKey(uid , com.lin.user.constant.Service.User_Detail_Storage_Key);
        JsonResult res = redisService.setKVWithoutExpire(com.lin.user.constant.Service.Service_Name, key, userDetail, KeyType.Storage_Int_type);
        if (!ConvertData.isResultIllegal(res)){
            log.error("setUserDetail_fail,key = {},uid={},userDetail={}",key,uid,userDetail);
            return res;
        }

        log.info("setUserDetail_success ,key = {},uid={},userDetail={}",key,uid,userDetail);
        return res;
    }


}
