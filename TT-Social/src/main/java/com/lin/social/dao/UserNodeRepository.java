package com.lin.social.dao;

import com.lin.social.entity.node.UserNode;

import com.lin.social.entity.normal.UserAndRelation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.ExistsQuery;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNodeRepository extends Neo4jRepository<UserNode, String> {
    @Query("MATCH (u:User) where u.uid=$uid RETURN u")
    List<UserNode> findUserByUid(@Param("uid") String uid);

    @Query("MATCH (u:User)<-[r]-(f:User) " +
            "where u.uid=$uid " +
            "RETURN f as userInfo ,r.followAt as followAt " +
            "order by r.followAt desc " +
            "skip ($curPage - 1) * $limit limit $limit")
    List<UserAndRelation> listUserFans(@Param("uid") String uid,@Param("curPage") int curPage,@Param("limit") int limit);

    @Query("MATCH (u:User)-[r]->(f:User) " +
            "where u.uid=$uid " +
            "RETURN f as userInfo ,r.followAt as followAt "+
            "order by r.followAt desc " +
            "skip ($curPage - 1) * $limit limit $limit")
    List<UserAndRelation> listUserSubscribe(@Param("uid") String uid,@Param("curPage") int curPage,@Param("limit") int limit);


    @Query("MATCH (:User{uid:$ownUID})-[r]-(:User{uid:$otherUID}) " +
            "delete r")
    void deleteFollowRelation(@Param("ownUID")String ownUID,@Param("otherUID")String otherUID);


    @Async
    @Query("MATCH (o:User{uid:$ownUID}),(y:User{uid:$otherUID}),p=(o)-[]-(y)" +
            "RETURN count(p)")
    Integer relationCountBetweenTwo(@Param("ownUID")String ownUID,@Param("otherUID")String otherUID);
}
