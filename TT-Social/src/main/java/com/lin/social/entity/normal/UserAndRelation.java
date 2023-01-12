package com.lin.social.entity.normal;

import com.lin.social.entity.node.UserNode;
import com.lin.social.entity.relation.Follow;
import lombok.Data;
import org.apache.catalina.User;
import org.springframework.data.neo4j.repository.query.ExistsQuery;
import org.springframework.data.neo4j.repository.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class UserAndRelation {


    UserNode userInfo;

    LocalDateTime followAt;
}
