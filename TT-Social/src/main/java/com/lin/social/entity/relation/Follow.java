package com.lin.social.entity.relation;


import com.lin.social.entity.node.UserNode;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RelationshipProperties
@Data
public class Follow {
    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private UserNode userNode;

    private LocalDateTime followAt;

    public Follow(UserNode userNode) {
        this.userNode = userNode;
        this.followAt = LocalDateTime.now();
    }


}
