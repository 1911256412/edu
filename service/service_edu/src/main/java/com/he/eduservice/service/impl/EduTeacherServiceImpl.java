package com.he.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.he.eduservice.entity.EduTeacher;
import com.he.eduservice.entity.excel.ExcelSubject;
import com.he.eduservice.listener.ExcelListener;
import com.he.eduservice.mapper.EduTeacherMapper;
import com.he.eduservice.service.EduSubjectService;
import com.he.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师; InnoDB free: 8192 kB 服务实现类
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-11
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {


    @Override
    public Map<String, Object> selectByPage(Page<EduTeacher> page) {
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        baseMapper.selectPage(page, wrapper);
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        boolean hasPrevious = page.hasPrevious();
        boolean hasNext = page.hasNext();
        long current = page.getCurrent();
        long size = page.getSize();
        long pages = page.getPages();
        Map<String,Object> map =new HashMap<>();
        map.put("items",records);
        map.put ("total",total);
        map.put("hasNewt",hasNext);
        map.put("hasPrevious",hasPrevious);
        map.put("current",current);
        map.put("size",size);
        map.put("pages",pages);
        return map;
    }
}
