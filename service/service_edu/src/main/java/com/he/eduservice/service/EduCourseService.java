package com.he.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.he.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.he.eduservice.entity.vo.CoursePublicVo;
import com.he.eduservice.entity.vo.CourseWebVo;
import com.he.eduservice.entity.vo.EduCourseVo;
import com.he.eduservice.entity.vo.FrontCourseVo;
import com.he.entity.CourseInfo;

import java.util.Map;

/**
 * <p>
 * 课程; InnoDB free: 8192 kB 服务类
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-18
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourse(EduCourseVo eduCourseVo);

    EduCourseVo getCourseVoById(String courseId);

    void updateCourse(EduCourseVo eduCourseVo);
    public CoursePublicVo selectPublicCourse(String courseId);

    void deleteCourse(String courseId);

    Map<String, Object> selectFrontCourse(Page<EduCourse> courseVoPage, FrontCourseVo frontCourseVo);

    CourseWebVo selectFrontWebCourse(String courseId);

    CourseInfo getCourseById(String courseId);
}
