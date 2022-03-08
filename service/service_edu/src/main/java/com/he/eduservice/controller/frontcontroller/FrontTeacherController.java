package com.he.eduservice.controller.frontcontroller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.he.eduservice.entity.EduCourse;
import com.he.eduservice.entity.EduTeacher;
import com.he.eduservice.service.EduCourseService;
import com.he.eduservice.service.EduTeacherService;
import com.he.utils.R;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/eduservice/front")
@RestController
public class FrontTeacherController {

    @Resource
    private EduTeacherService teacherService;

    @Resource
    private EduCourseService eduCourseService;
    //分页查询讲师
    @GetMapping("selectByPage/{current}/{limit}")
    public R selectByPage(@PathVariable Integer current, @PathVariable Integer limit ){
        Page<EduTeacher> page=new Page<>(current,limit);
        Map<String ,Object> map=teacherService.selectByPage(page);
        return R.ok().data(map);
    }
    //通过讲师id查询讲师和课程
    @GetMapping("getTeacher/{teacherId}")
    public R getFrontTeacherAndCourse(@PathVariable  String teacherId ){
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        wrapper.eq("id ",teacherId);
        EduTeacher eduTeacher = teacherService.getOne(wrapper);
        //通过教师id查询课程
        QueryWrapper<EduCourse> eduCourseQueryWrapper=new QueryWrapper<>();
        eduCourseQueryWrapper.eq("teacher_id",teacherId);
        List<EduCourse> courses = eduCourseService.list(eduCourseQueryWrapper);
        return R .ok().data("teacher",eduTeacher).data("courses",courses);
    }
}
