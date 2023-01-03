package com.lin.web.interfaces;


import com.lin.common.result.JsonResult;
import com.lin.user.entity.School;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient("tt-user-service")
public interface UserService {
    /*
        UserCommon 初始化  注册
     */
    @PostMapping("/tiktok/api/user/userCommon/registInitParam")
    public JsonResult registInitParam();



    /*
        UserDetail 相关设置接口
     */
    @GetMapping("/tiktok/api/user/userDetail/getUserDetail")
    public JsonResult getUserDetail(@RequestParam("token")String token, @RequestParam("uid")String uid, @RequestParam("did")String did);


    @PostMapping("/tiktok/api/user/userDetail/setUserDetailName")
    public JsonResult setUserDetailName(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                        @RequestParam("did")String did,@RequestParam("newName")String newName);



    @PostMapping("/tiktok/api/user/userDetail/setUserDetailIntroduction")
    public JsonResult setUserDetailIntroduction(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                                @RequestParam("did")String did,@RequestParam("newIntroduction")String newIntroduction);

    @PostMapping("/tiktok/api/user/userDetail/setUserDetailGender")
    public JsonResult setUserDetailGender(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                          @RequestParam("did")String did,@RequestParam("newGender")String newGender);

    @PostMapping("/tiktok/api/user/userDetail/setUserDetailBirth")
    public JsonResult setUserDetailBirth(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                         @RequestParam("did")String did,@RequestParam("newBirth") LocalDate newBirth);


    @PostMapping("/tiktok/api/user/userDetail/setUserDetailLocate")
    public JsonResult setUserDetailLocate(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                          @RequestParam("did")String did,@RequestParam("newLocate") String newLocate);

    @PostMapping(value = "/tiktok/api/user/userDetail/setUserDetailSchool",consumes = "application/json")
    public JsonResult setUserDetailSchool(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                          @RequestParam("did")String did,@RequestBody School school);


    @PostMapping("/tiktok/api/user/userDetail/setUserDetailTTAccount")
    public JsonResult setUserDetailTTAccount(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                             @RequestParam("did")String did,@RequestParam("newTTAccount") String newTTAccount);
}
