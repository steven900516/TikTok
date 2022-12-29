package com.lin.user.entity;


import lombok.Data;

import java.time.LocalDate;

@Data
public class School {
    // 是否隐私
    private Boolean isPrivate;

    // 学校名称
    private String schoolName;

    // 专业
    private String major;

    // 入学时间
    private LocalDate startTime;

    // 学历
    private String studyDegree;

}
