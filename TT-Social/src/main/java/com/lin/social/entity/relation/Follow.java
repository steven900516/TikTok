package com.lin.social.entity.relation;


import com.lin.social.entity.node.UserNode;
import com.lin.social.entity.node.Video;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
@Builder
public class Follow {
    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private UserNode userNode;

    @CreatedDate
    private Long followAt;

    public Follow(Long id,UserNode userNode,Long followAt) {
        this.id = null;
        this.userNode = userNode;
        this.followAt = null;
    }
}
