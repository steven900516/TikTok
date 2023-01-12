package com.lin.social.entity.relation;


import com.lin.social.entity.node.UserNode;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

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


    @Property("followAt")
    private LocalDateTime followAt;

    public Follow(UserNode userNode) {
        this.userNode = userNode;
        this.followAt = LocalDateTime.now();
    }


}
