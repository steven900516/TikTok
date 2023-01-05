package com.lin.social.entity.node;


import com.lin.social.constant.Neo4j;
import com.lin.social.entity.relation.Follow;
import com.lin.social.entity.relation.Publish;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Data
@Node("User")
public class UserNode {
    @Id
    private String uid;

    @Property("did")
    private String did;

    @Property("name")
    private String name;

    @Property("ttAccount")
    private String ttAccount;

    @CreatedDate
    private Long createAt;


    public UserNode() {
    }

    public UserNode(String uid, String did, String name, String ttAccount) {
        this.uid = uid;
        this.did = did;
        this.name = name;
        this.ttAccount = ttAccount;
    }

    @Relationship(type = Neo4j.FOLLOW, direction = Relationship.Direction.OUTGOING)
    private List<Follow> followList = new ArrayList<>();

    @Relationship(type = Neo4j.PBLISH, direction = Relationship.Direction.OUTGOING)
    private List<Publish> videoList = new ArrayList<>();
}
