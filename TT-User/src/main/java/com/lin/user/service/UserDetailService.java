package com.lin.user.service;


import com.lin.common.result.JsonResult;
import com.lin.user.entity.School;

import java.time.LocalDate;

public interface UserDetailService {

    JsonResult getUserDetail(String token,String uid,String did);


    JsonResult setUserDetailName(String token,String uid,String did,String newName);


    JsonResult setUserDetailIntroduction(String token,String uid,String did,String newIntroduction);


    JsonResult setUserDetailGender(String token,String uid,String did,String newGender);


    JsonResult setUserDetailBirth(String token, String uid, String did, LocalDate newDate);


    JsonResult setUserDetailLocate(String token, String uid, String did, String locate);


    JsonResult setUserDetailSchool(String token, String uid, String did, School school);


    JsonResult setUserDetailTTAccount(String token, String uid, String did, String newTtAccount);

}
