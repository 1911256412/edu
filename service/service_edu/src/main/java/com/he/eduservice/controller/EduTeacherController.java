package com.he.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.he.eduservice.entity.EduTeacher;
import com.he.eduservice.entity.vo.TeacherQuery;
import com.he.eduservice.service.EduTeacherService;
import com.he.exception.HeException;
import com.he.utils.R;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师; InnoDB free: 8192 kB 前端控制器
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-11
 */
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin(origins = "*",maxAge = 3600)//解决跨域问题
@CrossOrigin
public class EduTeacherController {
    @Resource
    private EduTeacherService service;

    @GetMapping("/findAll")
    public R findAll() {
        List<EduTeacher> list = service.list(null);
        //链式编程存入数据
        return R.ok().data("items", list);
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable String id) {

        boolean flag = service.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //分页查询
    @GetMapping("/selectByPage/{current}/{limit}")
    public R selectByPage(@PathVariable Integer current, @PathVariable Integer limit) {
        Page<EduTeacher> page = new Page<>(current, limit);
        service.page(page, null);

//        try{
//        //int n = 10 / 0;
//        }catch (Exception ex){
//            throw new HeException(20001,"执行自定义异常");
//        }
        List<EduTeacher> records = page.getRecords();//每页数据list集合
        long total = page.getTotal();//总记录数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", total);
        map.put("rows", records);
        R.ok().setData(map);
        return R.ok().data("total", total).data("rows", records);//用链式设置两个键值对
    }

    //带条件查询的分页的组合查询
    @PostMapping("/selectByCondition/{current}/{limit}")
    public R selectBycondition(@PathVariable Integer current, @PathVariable Integer limit, @RequestBody TeacherQuery teacherQuery) {
        Page<EduTeacher> page = new Page<>(current, limit);
        QueryWrapper wrapper = new QueryWrapper();
        //条件设置
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        wrapper.orderByDesc("gmt_create");
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.gt("gmt_create", end);
        }
        service.page(page, wrapper);
        long total = page.getTotal();
        List<EduTeacher> list = page.getRecords();//所有记录数

        return R.ok().data("total", total).data("rows", list);
    }

    @PostMapping("/save")
    public R save(@RequestBody EduTeacher teacher) {
        boolean save = service.save(teacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }


    }

    //要想修改数据首先需要通过id查询数据
    @GetMapping("getTeacher/{id}")
    public R select(@PathVariable String id) {
        EduTeacher teacher = service.getById(id);

        return R.ok().data("items", teacher);
    }

    @PostMapping("update")
    public R update(@RequestBody EduTeacher teacher) {
        boolean flag = service.updateById(teacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

