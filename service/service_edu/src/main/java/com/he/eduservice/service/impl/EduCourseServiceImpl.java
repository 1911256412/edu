package com.he.eduservice.service.impl;

import com.he.eduservice.entity.EduChapter;
import com.he.eduservice.entity.EduCourse;
import com.he.eduservice.entity.EduCourseDescription;
import com.he.eduservice.entity.vo.CoursePublicVo;
import com.he.eduservice.entity.vo.EduCourseVo;
import com.he.eduservice.mapper.EduCourseDescriptionMapper;
import com.he.eduservice.mapper.EduCourseMapper;
import com.he.eduservice.service.EduChapterService;
import com.he.eduservice.service.EduCourseDescriptionService;
import com.he.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.he.eduservice.service.EduVideoService;
import com.he.exception.HeException;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 课程; InnoDB free: 8192 kB 服务实现类
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-18
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Resource
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Resource
    private EduVideoService eduVideoService;
    @Resource
    private EduChapterService eduChapterService;
    //实现添加课程
    public String addCourse(EduCourseVo eduCourseVo) {

        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(eduCourseVo, eduCourse);
        //添加 课程，把表单中数据分开，分别插入到两个表中

        int i = baseMapper.insert(eduCourse);
        if (i == 0) {
            throw new HeException(20001, "保存失败 ");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(eduCourseVo, eduCourseDescription);
        //因为id相同 需要设置id
        eduCourseDescription.setId(eduCourse.getId());
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        if (!save) {
            throw new HeException(20001, "添加失败");
        }
        return eduCourse.getId();//需要把id返回
    }

    //通过id查询course
    public EduCourseVo getCourseVoById(String courseId) {
        EduCourseVo eduCourseVo = new EduCourseVo();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, eduCourseVo);

        //得到描述类信息
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        eduCourseVo.setDescription(eduCourseDescription.getDescription());


        return eduCourseVo;
    }

    public void updateCourse(EduCourseVo eduCourseVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(eduCourseVo, eduCourse);
        //修改课程表
        int i = baseMapper.updateById(eduCourse);
        if (i == 0) {
            throw new HeException(2001, "修改失败");
        }
        //修改课程描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(eduCourseVo, eduCourseDescription);
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublicVo selectPublicCourse(String courseId) {
        CoursePublicVo courseVo = baseMapper.selectPublicVo(courseId);
        return courseVo;
    }

    @Override
    public void deleteCourse(String courseId) {
        //根据课程id删除小结
        eduVideoService.removeByCourseId(courseId);
        //根据课程id删除章节
        eduChapterService.removeByCourseId(courseId);
        //根据课程id删除描述
        eduCourseDescriptionService.removeById(courseId);
        //根据课程id删除课程
        int i = baseMapper.deleteById(courseId);
        if(i==0){
            throw new HeException(20001,"删除课程失败");
        }
    }
}
