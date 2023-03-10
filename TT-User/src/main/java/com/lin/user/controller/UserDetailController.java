package com.lin.user.controller;


import com.lin.common.result.JsonResult;
import com.lin.user.entity.School;
import com.lin.user.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/userDetail")
public class UserDetailController {

    @Autowired
    private UserDetailService userDetailService;

    @GetMapping("/getUserDetail")
    public JsonResult getUserDetail(@RequestParam("token")String token, @RequestParam("uid")String uid,@RequestParam("did")String did) throws IOException, ClassNotFoundException {
        return userDetailService.getUserDetail(token, uid, did);
    }


    @PostMapping("/setUserDetailName")
    public JsonResult setUserDetailName(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                        @RequestParam("did")String did,@RequestParam("newName")String newName){
        return userDetailService.setUserDetailName(token, uid, did,newName);
    }



    @PostMapping("/setUserDetailIntroduction")
    public JsonResult setUserDetailIntroduction(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                                @RequestParam("did")String did,@RequestParam("newIntroduction")String newIntroduction){
        return userDetailService.setUserDetailIntroduction(token, uid, did,newIntroduction);
    }

    @PostMapping("/setUserDetailGender")
    public JsonResult setUserDetailGender(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                          @RequestParam("did")String did,@RequestParam("newGender")String newGender){
        return userDetailService.setUserDetailGender(token, uid, did,newGender);
    }

    @PostMapping("/setUserDetailBirth")
    public JsonResult setUserDetailBirth(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                          @RequestParam("did")String did,@RequestParam("newBirth") LocalDate newBirth){
        return userDetailService.setUserDetailBirth(token, uid, did,newBirth);
    }


    @PostMapping("/setUserDetailLocate")
    public JsonResult setUserDetailLocate(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                         @RequestParam("did")String did,@RequestParam("newLocate") String newLocate){
        return userDetailService.setUserDetailLocate(token, uid, did,newLocate);
    }

    @ResponseBody
    @PostMapping(value = "/setUserDetailSchool",produces = "application/json")
    public JsonResult setUserDetailSchool(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                          @RequestParam("did")String did,@RequestBody School school){
        return userDetailService.setUserDetailSchool(token, uid, did,school);
    }

    @PostMapping("/setUserDetailTTAccount")
    public JsonResult setUserDetailTTAccount(@RequestParam("token")String token, @RequestParam("uid")String uid,
                                          @RequestParam("did")String did,@RequestParam("newTTAccount") String newTTAccount){
        return userDetailService.setUserDetailTTAccount(token, uid, did,newTTAccount);
    }
}
