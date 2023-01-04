package com.lin.social.dao;

import com.lin.social.entity.node.UserNode;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNodeRepository extends Neo4jRepository<UserNode, String> {
    @Query("MATCH (u:Uovie) where u.uid=$uid RETURN u")
    List<UserNode> findByTitle(@Param("uid") String uid);
}
