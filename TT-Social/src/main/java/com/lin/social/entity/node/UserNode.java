package com.lin.social.entity.node;


import com.lin.social.constant.Neo4j;
import com.lin.social.entity.normal.Video;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;


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

    @Property("videoMap")
    private HashMap<String,Video> videoMap;

    @Relationship(type = Neo4j.FOLLOW, direction = Relationship.Direction.OUTGOING)
    private List<UserNode> followList;


    public UserNode addSubscribe(UserNode actor) {
        if (this.followList == null) {
            this.followList = new ArrayList<>();
        }
        this.followList.add(actor);
        return this;
    }

}
