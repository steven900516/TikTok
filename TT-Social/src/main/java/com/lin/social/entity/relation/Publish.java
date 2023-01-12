package com.lin.social.entity.relation;


import com.lin.social.entity.node.Video;
import lombok.Builder;
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
public class Publish {
    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private Video video;

    private LocalDateTime publishAt;

    public Publish(Video video) {
        this.video = video;
        this.publishAt = LocalDateTime.now();
    }

}
