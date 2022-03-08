package com.he.eduservice.controller.frontcontroller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.he.eduservice.entity.EduCourse;
import com.he.eduservice.entity.EduTeacher;
import com.he.eduservice.service.EduCourseService;
import com.he.eduservice.service.EduTeacherService;
import com.he.utils.R;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/eduservice")
@CrossOrigin
public class IndexController {
    @Resource
    private EduCourseService eduCourseService;

    @Resource
    private EduTeacherService eduTeacherService;

    @GetMapping("/index")
    //查询课程前八个 ，查询四个名师，根据id降序排序
    public R  selectCourse(){
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> courses =eduCourseService.list(wrapper);

        QueryWrapper<EduTeacher> wrapper1=new QueryWrapper<>();
        wrapper1.orderByDesc("id");
        wrapper1.last("limit 4");
        List<EduTeacher> teachers=eduTeacherService.list(wrapper1);

        return R.ok().data("courses",courses).data("teachers",teachers);
    }
}
