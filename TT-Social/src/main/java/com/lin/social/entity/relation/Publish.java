package com.lin.social.entity.relation;


import com.lin.social.entity.node.Video;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDate;

@RelationshipProperties
@Data
@Builder
public class Publish {
    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private Video video;

    @CreatedDate
    private LocalDate publishAt;

    public Publish(Long id, Video video, LocalDate publishAt) {
        this.id = id;
        this.video = video;
        this.publishAt = publishAt;
    }
}
