package com.he.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CoursePublicVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String OneSubject;
    private String TwoSubject;
    private String teacherName;
    private String price;//只用于显示
}
