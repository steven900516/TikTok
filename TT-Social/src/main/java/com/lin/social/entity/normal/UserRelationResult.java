package com.lin.social.entity.normal;


import lombok.Data;

@Data
public class UserRelationResult {
    UserAndRelation userAndRelation;
    boolean isFriend;
}
