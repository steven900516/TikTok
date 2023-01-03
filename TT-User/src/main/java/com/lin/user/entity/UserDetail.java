package com.lin.user.entity;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserDetail implements Serializable {

    private byte[] userImage;

    private String name;

    private String introduction;

    private String gender;

    private LocalDate birth;

    private String locate;

    private School schoolInfo;

    private String ttAccount;
}
