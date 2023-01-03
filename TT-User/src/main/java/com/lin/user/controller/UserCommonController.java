package com.lin.user.controller;

import com.lin.common.result.JsonResult;
import com.lin.user.service.UserCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/userCommon")
public class UserCommonController {

    @Autowired
    UserCommonService userCommonService;

    @PostMapping("/registInitParam")
    public JsonResult registInitParam() throws IOException {
        return userCommonService.registInitCommonParam();
    }
}
