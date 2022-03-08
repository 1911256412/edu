package com.he.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.he.eduservice.entity.EduChapter;
import com.he.eduservice.entity.EduCourse;
import com.he.eduservice.entity.EduCourseDescription;
import com.he.eduservice.entity.EduTeacher;
import com.he.eduservice.entity.vo.CoursePublicVo;
import com.he.eduservice.entity.vo.CourseWebVo;
import com.he.eduservice.entity.vo.EduCourseVo;
import com.he.eduservice.entity.vo.FrontCourseVo;
import com.he.eduservice.mapper.EduCourseDescriptionMapper;
import com.he.eduservice.mapper.EduCourseMapper;
import com.he.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.he.entity.CourseInfo;
import com.he.exception.HeException;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private EduTeacherService eduTeacherService;
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
        if (i == 0) {
            throw new HeException(20001, "删除课程失败");
        }
    }


    public Map<String, Object> selectFrontCourse(Page<EduCourse> page, FrontCourseVo frontCourseVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(frontCourseVo.getSubjectId())) {
            wrapper.eq("subject_id", frontCourseVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(frontCourseVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", frontCourseVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(frontCourseVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(frontCourseVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(frontCourseVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(page, wrapper);
        List<EduCourse> records = page.getRecords();
        long total = page.getTotal();
        boolean hasPrevious = page.hasPrevious();
        boolean hasNext = page.hasNext();
        long current = page.getCurrent();
        long size = page.getSize();
        long pages = page.getPages();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("total", total);
        map.put("hasNewt", hasNext);
        map.put("hasPrevious", hasPrevious);
        map.put("current", current);
        map.put("size", size);
        map.put("pages", pages);
        return map;
    }

    @Override
    public CourseWebVo selectFrontWebCourse(String courseId) {
        CourseWebVo courseWebVo=baseMapper.selectWebCourseInfo(courseId);
        return courseWebVo;
    }

    @Override
    public CourseInfo getCourseById(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);

        CourseInfo info=new CourseInfo();
        BeanUtils.copyProperties(eduCourse,info);
        //通过教师id得到教师name
        EduTeacher eduTeacher = eduTeacherService.getById(eduCourse.getTeacherId());
        info.setTeacherName(eduTeacher.getName());
        return info;
    }
}
