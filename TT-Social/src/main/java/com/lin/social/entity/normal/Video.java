package com.lin.social.entity.normal;


import lombok.Data;


@Data
public class Video {
    private String vid;


    private String uid;


    private byte[] img;


    private String description;


    private String tag;


    private boolean isPrivate;


    private String locate;


    private Integer loveCount;


    private Integer commentCount;


    private Integer shareCount;
}
