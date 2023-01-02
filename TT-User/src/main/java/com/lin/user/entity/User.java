package com.lin.user.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private UserCommon userCommon;

    private UserDetail userDetail;
}
