package com.he.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.he.eduservice.entity.EduSubject;
import com.he.eduservice.entity.excel.ExcelSubject;
import com.he.eduservice.entity.vo.OneSubjectVo;
import com.he.eduservice.entity.vo.TwoSubjectVo;
import com.he.eduservice.listener.ExcelListener;
import com.he.eduservice.mapper.EduSubjectMapper;
import com.he.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目; InnoDB free: 8192 kB 服务实现类
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    public void addFile(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            //得到文件
            InputStream inputStream = file.getInputStream();
            //读取文件
            EasyExcel.read(inputStream, ExcelSubject.class, new ExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubjectVo> getSubjectAll() {

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<EduSubject>();
        //查询所有一级标题 ，过滤条件为 parentid=0
        wrapper.eq("parent_id", 0);
        List<EduSubject> eduOneSubjects = baseMapper.selectList(wrapper);

        //查询出来所有二级标题
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<EduSubject>();
        //条件为 parentid！=0
        wrapper1.ne("parent_id", 0);
        List<EduSubject> eduTwoSubjects = baseMapper.selectList(wrapper1);

        List<OneSubjectVo> listOneSubjectVo = new ArrayList<OneSubjectVo>();


        //循环遍历出来每一个对象
        for (EduSubject eduSubject : eduOneSubjects) {
            OneSubjectVo oneSubjectVo = new OneSubjectVo();
//            oneSubjectVo.setTitle(eduSubject.getTitle());
//            oneSubjectVo.setId(eduSubject.getId());
            //参数1 复制之前的对象
            //参数2 复制之后的对象
            //可以把一样的属性直接用类复制过去
            BeanUtils.copyProperties(eduSubject, oneSubjectVo);
            //二级标题为集合类型，有一个一级标题就有多个二级标题
            List<TwoSubjectVo> twoSubjectVos = new ArrayList<TwoSubjectVo>();
            for (EduSubject eduSubject1 : eduTwoSubjects) {
                TwoSubjectVo twoSubjectVo = new TwoSubjectVo();
                BeanUtils.copyProperties(eduSubject1, twoSubjectVo);
                //如果一级标题的id 等于 二级标题的parentId 就把他添加到一级标题中
                if (oneSubjectVo.getId().equals(eduSubject1.getParentId())) {
                    twoSubjectVos.add(twoSubjectVo);
                    //把二级标题也添加到一级标题的集合中
                    oneSubjectVo.setChildren(twoSubjectVos);
                }

            }
            listOneSubjectVo.add(oneSubjectVo);
        }
        return listOneSubjectVo;
    }
}
