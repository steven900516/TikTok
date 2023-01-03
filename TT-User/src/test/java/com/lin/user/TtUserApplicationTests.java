package com.lin.user;

import com.lin.common.result.JsonResult;
import com.lin.common.util.SerializeUtils;
import com.lin.storage.constant.KeyType;
import com.lin.user.constant.Service;
import com.lin.user.entity.UserDetail;
import com.lin.user.interfaces.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.lin.user.util.Key.generateKey;

@SpringBootTest
@Slf4j
class TtUserApplicationTests {

    @Autowired
    RedisService redisService;

    @Test
    void contextLoads() throws IOException, ClassNotFoundException {
//        JsonResult jsonResult = redisService.getKV(com.lin.user.constant.Service.Service_Name, "109620637727110923" + "-UserDetail", KeyType.Storage_Int_type);
//        System.out.println(jsonResult.getData());
        UserDetail userDetail1 = new UserDetail();
        userDetail1.setName("hdajsdhjsahdja");
        userDetail1.setGender("男");
        redisService.setKVWithoutExpire(com.lin.user.constant.Service.Service_Name, "1" + "-UserDetail", SerializeUtils.serialize(userDetail1),KeyType.Storage_Int_type);
        UserDetail o = (UserDetail)SerializeUtils.serializeToObject(redisService.getKV(Service.Service_Name, "1" + "-UserDetail", KeyType.Storage_Int_type).getData());
        System.out.println(o);
    }

    private UserDetail getUserDetail(String uid){
        String key = generateKey(uid , com.lin.user.constant.Service.User_Detail_Storage_Key);
        UserDetail userDetail = null;
        JsonResult jsonResult = redisService.getKV(com.lin.user.constant.Service.Service_Name, key, KeyType.Storage_Int_type);

        try {
            userDetail = (UserDetail) SerializeUtils.serializeToObject((String)jsonResult.getData());
        }catch (Exception e){
            log.error("UserDetail_unserialize_fial,uid={}",uid);
            return null;
        }
        log.info("getUserDetail,key = {},jsonResult = {},userDetail:{}",key,jsonResult,userDetail);
        return userDetail == null ? new UserDetail() : userDetail;
    }


}
