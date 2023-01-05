package com.lin.social.entity.node;


import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;


@Data
@Node("Video")
public class Video {

    @Id
    @Property("vid")
    private String vid;

    @Property("uid")
    private String uid;

    @Property("img")
    private byte[] img;

    @Property("decription")
    private String description;

    @Property("tag")
    private String tag;

    @Property("isPrivate")
    private boolean isPrivate;

    @Property("locate")
    private String locate;

    @Property("loveCount")
    private Integer loveCount;

    @Property("commentCount")
    private Integer commentCount;

    @Property("treasureCount")
    private Integer treasureCount;

    @Property("shareCount")
    private Integer shareCount;

    public Video(String vid, String uid, byte[] img, String description, String tag, boolean isPrivate, String locate, Integer loveCount,Integer treasureCount ,Integer commentCount, Integer shareCount) {
        this.vid = vid;
        this.uid = uid;
        this.img = img;
        this.description = description;
        this.tag = tag;
        this.isPrivate = isPrivate;
        this.locate = locate;
        this.loveCount = loveCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.treasureCount = treasureCount;
    }
}
