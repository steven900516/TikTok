package com.lin.user.service.Impl;

import com.lin.common.result.JsonResult;
import com.lin.common.result.ResultCode;
import com.lin.common.result.ResultTool;
import com.lin.common.util.ConvertData;
import com.lin.storage.constant.KeyType;
import com.lin.user.entity.School;
import com.lin.user.entity.UserDetail;
import com.lin.user.interfaces.RedisService;
import com.lin.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisService redisService;


    @Override
    public JsonResult getUserDetail(String token, String uid, String did) {
        // 1. 校验token是否合理，是否登录
        if (!isValidToken(token)){
            return ResultTool.fail(ResultCode.TOKEN_ERROR);
        }

        // 2. 去Redis存储服务拿信息 3787398274UserDetail
        String key = generateKey(uid,com.lin.user.constant.Service.User_Detail_Key);
        JsonResult jsonResult = redisService.getKV(com.lin.user.constant.Service.Service_Name, key, KeyType.Storage_Int_type);
        // 成功查询
        if (ConvertData.isResultIllegal(jsonResult)){
            // 若查询用户Detail 信息为空，则创建新对象返回
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

        // 检验是否30天内修改1次
        String recordKey = generateKey(uid,com.lin.user.constant.Service.User_Detail_Key + "-Name");
        if (redisService.getKV(com.lin.user.constant.Service.Service_Name,recordKey,KeyType.Record_Int_type) != null) {
            return ResultTool.fail(ResultCode.USER_DETAIL_NAME_HAS_CHANGED);
        }

        String storageKey = generateKey(uid,com.lin.user.constant.Service.User_Detail_Key);
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
        return null;
    }

    @Override
    public JsonResult setUserDetailGender(String token, String uid, String did, String newGender) {
        return null;
    }

    @Override
    public JsonResult setUserDetailBirth(String token, String uid, String did, LocalDate newDate) {
        return null;
    }

    @Override
    public JsonResult setUserDetailLocate(String token, String uid, String did, String locate) {
        return null;
    }

    @Override
    public JsonResult setUserDetailSchool(String token, String uid, String did, School school) {
        return null;
    }

    @Override
    public JsonResult setTtAccount(String token, String uid, String did, String newTtAccount) {
        return null;
    }


    private String generateKey(String uid,String key){
        return uid + key;
    }

    private UserDetail getUserDetail(String uid){
        String key = uid + com.lin.user.constant.Service.User_Detail_Key;

        JsonResult jsonResult = redisService.getKV(com.lin.user.constant.Service.Service_Name, key, KeyType.Storage_Int_type);
        UserDetail userDetail = (UserDetail)ConvertData.ConvertDataIntoObj(jsonResult, UserDetail.class);
        return userDetail == null ? new UserDetail() : userDetail;
    }

    private boolean isValidToken(String token){
        return true;
    }
}
