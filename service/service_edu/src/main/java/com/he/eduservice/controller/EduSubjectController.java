package com.he.eduservice.controller;


import com.he.eduservice.entity.vo.OneSubjectVo;
import com.he.eduservice.service.EduSubjectService;
import com.he.eduservice.service.EduTeacherService;
import com.he.utils.R;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目; InnoDB free: 8192 kB 前端控制器
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-17
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Resource
    private EduSubjectService eduSubjectService;

    @PostMapping("addsubject")
    public R  addsubject(MultipartFile file){

        //读取文件
        eduSubjectService.addFile(file,eduSubjectService);


        return R.ok();
    }
    @GetMapping("getsubject")
    public R getSubject(){
       List<OneSubjectVo> list= eduSubjectService.getSubjectAll();
        return R.ok().data("items",list);
    }

}

