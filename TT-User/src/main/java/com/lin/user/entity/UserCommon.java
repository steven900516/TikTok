package com.lin.user.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserCommon implements Serializable {

    private String uid;

    private String did;

    private String ttAccount;
}
