package com.he.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.he.eduservice.entity.EduCourse;
import com.he.eduservice.entity.vo.CoursePublicVo;
import com.he.eduservice.entity.vo.EduCourseVo;
import com.he.eduservice.service.EduCourseService;
import com.he.entity.CourseInfo;
import com.he.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程; InnoDB free: 8192 kB 前端控制器
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-18
 */
@RestController
@RequestMapping("/eduservice/educourse")
@CrossOrigin
public class EduCourseController {
    @Resource
    private EduCourseService eduCourseService;

    @PostMapping("addCourse")
    public R addCourse(@RequestBody EduCourseVo eduCourseVo) {

        String id = eduCourseService.addCourse(eduCourseVo);
        return R.ok().data("id", id);
    }

    //通过课程id进行查询
    @GetMapping("getCourse/{courseId}")
    public R selectBycourseId(@PathVariable String courseId) {

        EduCourseVo eduCourseVo = eduCourseService.getCourseVoById(courseId);
        return R.ok().data("items", eduCourseVo);
    }

    //修改课程信息
    @PostMapping("updataCourse")
    public R updateCourse(@RequestBody EduCourseVo eduCourseVo) {

        eduCourseService.updateCourse(eduCourseVo);
        return R.ok();
    }

    @GetMapping("getPublicCourse/{courseId}")
    public R getPublic(@PathVariable String courseId) {
        CoursePublicVo coursePublicVo = eduCourseService.selectPublicCourse(courseId);

        return R.ok().data("coursePublicVo", coursePublicVo);
    }

    //发布课程 ，修改课程状态
    @PutMapping("update/{courseId}")
    public R fabuPublic(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //查询所有课程列表
    @GetMapping("getAllCourse")
    public R Public() {
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        List<EduCourse> list = eduCourseService.list(wrapper);

        return R.ok().data("CourseList", list);
    }
    //删除课程
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable("courseId")  String courseId){
        eduCourseService.deleteCourse(courseId);
        return R.ok();
    }
    //通过课程id进行查询
    @GetMapping("getByCourseId/{courseId}")
    public CourseInfo selectBycourse(@PathVariable String courseId) {

        CourseInfo CourseInfo = eduCourseService.getCourseById(courseId);
        return CourseInfo;
    }
}

