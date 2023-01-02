package com.lin.web.interfaces;


import com.lin.common.result.JsonResult;
import com.lin.user.entity.School;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient("tt-user-service")
public interface UserService {



    @GetMapping("/tiktok/api/user/userDetail/getUserDetail")
    public JsonResult getUserDetail(@RequestParam("token")String token, @RequestParam("uid")String uid, @RequestParam("did")String did);


    @PostMapping("/tiktok/api/user/userCommon/registInitParam")
    public JsonResult registInitParam();


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
                                          @RequestParam("did")String did,@RequestParam("newLocate") LocalDate newLocate);

    @PostMapping("/tiktok/api/user/userDetail/setUserDetailSchool")
    public JsonResult setUserDetailSchool(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                          @RequestParam("did")String did,@RequestParam("newSchool") School newSchool);


    @PostMapping("/setUserDetailTTAccount")
    public JsonResult setUserDetailTTAccount(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                             @RequestParam("did")String did,@RequestParam("newTTAcount") String newTTAcount);
}
