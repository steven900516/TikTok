package com.lin.social.Service;

import com.lin.common.result.JsonResult;
import com.lin.user.entity.UserCommon;

public interface SocialService {



    JsonResult registUserNodeToGraph(UserCommon userCommon);


    JsonResult listUserFans(UserCommon userCommon);


    JsonResult listUserSubscribe(UserCommon userCommon);
}
