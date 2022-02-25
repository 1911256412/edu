package com.he.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherQuery {
    //封装要查询的数据
    private String name;
    private Integer level;
    private String begin;
    private String end;
}
