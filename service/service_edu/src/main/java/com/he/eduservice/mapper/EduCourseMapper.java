package com.he.eduservice.mapper;

import com.he.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.he.eduservice.entity.vo.CoursePublicVo;
import com.he.eduservice.entity.vo.CourseWebVo;

/**
 * <p>
 * 课程; InnoDB free: 8192 kB Mapper 接口
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-18
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublicVo selectPublicVo(String courseId);

    CourseWebVo selectWebCourseInfo(String courseId);
}
