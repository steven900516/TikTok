package com.lin.social.service.serviceImpl;

import com.lin.common.result.JsonResult;
import com.lin.common.result.ResultCode;
import com.lin.common.result.ResultTool;
import com.lin.common.util.SerializeUtils;
import com.lin.social.constant.Neo4j;
import com.lin.social.entity.normal.UserAndRelation;
import com.lin.social.entity.normal.UserRelationResult;
import com.lin.social.entity.relation.Follow;
import com.lin.social.interfaces.RedisService;
import com.lin.social.service.SocialService;
import com.lin.social.dao.UserNodeRepository;
import com.lin.social.entity.node.UserNode;
import com.lin.storage.constant.KeyType;
import com.lin.user.entity.UserCommon;
import com.lin.user.entity.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.soap.Node;
import java.util.ArrayList;
import java.util.List;

import static com.lin.user.util.Key.generateKey;


@Slf4j
@Service
public class SocialServiceImpl implements SocialService {
    @Autowired
    UserNodeRepository userNodeRepository;


    @Autowired
    RedisService redisService;

    @Override
    public JsonResult registUserNodeToGraph(UserCommon userCommon) {
        String key = generateKey(userCommon.getUid() , com.lin.user.constant.Service.User_Detail_Storage_Key);
        UserDetail userDetail = null;
        JsonResult jsonResult = redisService.getKV(com.lin.user.constant.Service.Service_Name, key, KeyType.Storage_Int_type);

        try {
            userDetail = (UserDetail) SerializeUtils.serializeToObject(jsonResult.getData());
        }catch (Exception e){
            log.error("UserDetail_unserialize_fail,uid={}",userCommon.getUid(),e);
            return ResultTool.fail();
        }
        log.info("getUserDetail,key = {},userDetail:{}",key,userDetail);

        try {
            UserNode userNode = new UserNode(userCommon.getUid(), userCommon.getDid(), userDetail.getName());
            userNodeRepository.save(userNode);
        }catch (Exception e){
            log.error("registUserNodeToGraph fail uid={}",userCommon.getUid(),e);
            return ResultTool.fail();
        }

        redisService.setKVWithoutExpire(Neo4j.Service_Name,userCommon.getUid() + "-" + Neo4j.Loves_Count_Totol,0,KeyType.Storage_Int_type);
        redisService.setKVWithoutExpire(Neo4j.Service_Name,userCommon.getUid() + "-" + Neo4j.Friend_Count_Total,0,KeyType.Storage_Int_type);
        redisService.setKVWithoutExpire(Neo4j.Service_Name,userCommon.getUid() + "-" + Neo4j.Follow_Count_Total,0,KeyType.Storage_Int_type);
        redisService.setKVWithoutExpire(Neo4j.Service_Name,userCommon.getUid() + "-" + Neo4j.Fans_Count_Total,0,KeyType.Storage_Int_type);

        log.info("regist_user_node to neo4j success");
        return ResultTool.success();
    }

    @Override
    public JsonResult updateUserNodeName(UserCommon userCommon, String newName) {
        try {
            List<UserNode> userNodeList = userNodeRepository.findUserByUid(userCommon.getUid());
            if (userNodeList.size() == 0){
                log.error("neo4j_not_regist_user_node,userCommon={}",userCommon);
                return ResultTool.fail(ResultCode.GRAPH_NO_REIGIST);
            }
            UserNode userNode = userNodeList.get(0);
            userNode.setName(newName);
            userNodeRepository.save(userNode);
        }catch (Exception e){
            log.error("updateUserNodeName_fail,userCommon={},newName={}",userCommon,newName,e);
            return ResultTool.fail();
        }
        log.info("updateUserNodeName_success,userCommon={}",userCommon);
        return ResultTool.success();
    }

    @Override
    public JsonResult listUserFans(UserCommon userCommon,int curPage,int limit) {
        List<UserAndRelation> fans = null;
        try {
            fans = userNodeRepository.listUserFans(userCommon.getUid(), curPage, limit);
        }catch (Exception e){
            log.error("listUserFans_error",e);
            return ResultTool.fail();
        }
        List<UserRelationResult> res = new ArrayList<>();
        for (UserAndRelation  fan: fans) {
            boolean isFriend = isRelationFriend(userCommon.getUid(), fan.getUserInfo().getUid());
            UserRelationResult ur = new UserRelationResult();
            ur.setFriend(isFriend);
            ur.setUserAndRelation(fan);
            res.add(ur);
        }
        log.info("listUserFans_success,count={},res={}",res.size(),res);
        return ResultTool.success(res);
    }

    @Override
    public JsonResult listUserSubscribe(UserCommon userCommon,int curPage,int limit) {
        List<UserAndRelation> subscribers = null;
        try {
            subscribers = userNodeRepository.listUserSubscribe(userCommon.getUid(), curPage, limit);
        }catch (Exception e){
            log.error("listUserFans_error",e);
            return ResultTool.fail();
        }
        List<UserRelationResult> res = new ArrayList<>();
        for (UserAndRelation  fan: subscribers) {
            boolean isFriend = isRelationFriend(userCommon.getUid(), fan.getUserInfo().getUid());
            UserRelationResult ur = new UserRelationResult();
            ur.setFriend(isFriend);
            ur.setUserAndRelation(fan);
            res.add(ur);
        }
        log.info("listUserSubscribe_success,count={},subscriber={}",res.size(),res);
        return ResultTool.success(res);
    }

    @Override
    public JsonResult follow(String ownUID, String otherUID) {
        // 若对方已关注自己
        if (userNodeRepository.relationCountBetweenTwo(ownUID, otherUID) == 1){
            JsonResult friendCount = redisService.getKV(Neo4j.Service_Name, ownUID + "-" + Neo4j.Friend_Count_Total, KeyType.Storage_Int_type);
            Integer friendInteger = Integer.parseInt((String)friendCount.getData());
            redisService.setKVWithoutExpire(Neo4j.Service_Name,otherUID + "-" + Neo4j.Friend_Count_Total,friendInteger + 1,KeyType.Storage_Int_type);

            JsonResult otherFriendCount = redisService.getKV(Neo4j.Service_Name, otherUID + "-" + Neo4j.Friend_Count_Total, KeyType.Storage_Int_type);
            Integer otherFriendInteger = Integer.parseInt((String)otherFriendCount.getData());
            redisService.setKVWithoutExpire(Neo4j.Service_Name,otherUID + "-" + Neo4j.Friend_Count_Total,otherFriendInteger + 1,KeyType.Storage_Int_type);
        }

        List<UserNode> ownNodeList = userNodeRepository.findUserByUid(ownUID);
        if (ownNodeList.size() != 1){
            log.error("follow_neo4j_not_regist_user_node,ownuid={},ownNodeList.size={}",ownUID,ownNodeList.size());
            return ResultTool.fail(ResultCode.GRAPH_NO_REIGIST);
        }
        List<UserNode> otherNodeList = userNodeRepository.findUserByUid(otherUID);
        if (ownNodeList.size() != 1){
            log.error("follow_neo4j_not_regist_user_node,otheruid={},otherNodeList.size={}",otherUID,otherNodeList.size());
            return ResultTool.fail(ResultCode.GRAPH_NO_REIGIST);
        }
        UserNode own = ownNodeList.get(0);
        UserNode other = otherNodeList.get(0);
        own.getFollowList().add(new Follow(other));
        userNodeRepository.save(own);

        JsonResult followCount = redisService.getKV(Neo4j.Service_Name, ownUID + "-" + Neo4j.Follow_Count_Total, KeyType.Storage_Int_type);
        Integer followInteger = Integer.parseInt((String)followCount.getData());
        redisService.setKVWithoutExpire(Neo4j.Service_Name,ownUID + "-" + Neo4j.Follow_Count_Total,followInteger + 1,KeyType.Storage_Int_type);

        JsonResult fansCount = redisService.getKV(Neo4j.Service_Name, otherUID + "-" + Neo4j.Fans_Count_Total, KeyType.Storage_Int_type);
        Integer fansInteger = Integer.parseInt((String)fansCount.getData());
        redisService.setKVWithoutExpire(Neo4j.Service_Name,otherUID + "-" + Neo4j.Fans_Count_Total,fansInteger + 1,KeyType.Storage_Int_type);



        log.info("follow_success,{} 关注了: {}",own.getName(),other.getName());
        return ResultTool.success();
    }

    @Override
    public JsonResult cancleFollow(String ownUID, String otherUID) {
        if (isRelationFriend(ownUID, otherUID)){
            JsonResult friendCount = redisService.getKV(Neo4j.Service_Name, ownUID + "-" + Neo4j.Friend_Count_Total, KeyType.Storage_Int_type);
            Integer friendInteger = Integer.parseInt((String)friendCount.getData());
            redisService.setKVWithoutExpire(Neo4j.Service_Name,otherUID + "-" + Neo4j.Friend_Count_Total,friendInteger - 1,KeyType.Storage_Int_type);

            JsonResult otherFriendCount = redisService.getKV(Neo4j.Service_Name, otherUID + "-" + Neo4j.Friend_Count_Total, KeyType.Storage_Int_type);
            Integer otherFriendInteger = Integer.parseInt((String)otherFriendCount.getData());
            redisService.setKVWithoutExpire(Neo4j.Service_Name,otherUID + "-" + Neo4j.Friend_Count_Total,otherFriendInteger - 1,KeyType.Storage_Int_type);
        }

        List<UserNode> ownNodeList = userNodeRepository.findUserByUid(ownUID);
        if (ownNodeList.size() != 1){
            log.error("cancleFollow_neo4j_not_regist_user_node,ownuid={},ownNodeList.size={}",ownUID,ownNodeList.size());
            return ResultTool.fail(ResultCode.GRAPH_NO_REIGIST);
        }
        List<UserNode> otherNodeList = userNodeRepository.findUserByUid(otherUID);
        if (ownNodeList.size() != 1){
            log.error("cancleFollow_neo4j_not_regist_user_node,otheruid={},otherNodeList.size={}",otherUID,otherNodeList.size());
            return ResultTool.fail(ResultCode.GRAPH_NO_REIGIST);
        }
        UserNode own = ownNodeList.get(0);
        UserNode other = otherNodeList.get(0);
        userNodeRepository.deleteFollowRelation(ownUID,otherUID);

        JsonResult followCount = redisService.getKV(Neo4j.Service_Name, ownUID + "-" + Neo4j.Follow_Count_Total, KeyType.Storage_Int_type);
        Integer followInteger = Integer.parseInt((String)followCount.getData());
        redisService.setKVWithoutExpire(Neo4j.Service_Name,ownUID + "-" + Neo4j.Follow_Count_Total,followInteger - 1,KeyType.Storage_Int_type);

        JsonResult fansCount = redisService.getKV(Neo4j.Service_Name, otherUID + "-" + Neo4j.Fans_Count_Total, KeyType.Storage_Int_type);
        Integer fansInteger = Integer.parseInt((String)fansCount.getData());
        redisService.setKVWithoutExpire(Neo4j.Service_Name,otherUID + "-" + Neo4j.Fans_Count_Total,fansInteger - 1,KeyType.Storage_Int_type);

        log.info("cancle_follow_success,{} 取消关注了: {}",own.getName(),other.getName());
        return ResultTool.success();
    }

    @Override
    public JsonResult isFriend(String ownUID, String otherUID) {
        boolean isFriend = isRelationFriend(ownUID, otherUID);
        return ResultTool.success(isFriend);
    }


    public boolean isRelationFriend(String ownUID, String otherUID){
        Integer integer = userNodeRepository.relationCountBetweenTwo(ownUID, otherUID);
        boolean isFriend = integer == 2;
        return isFriend;
    }
}
