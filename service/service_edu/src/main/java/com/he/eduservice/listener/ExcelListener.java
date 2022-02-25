package com.he.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.he.eduservice.entity.EduSubject;
import com.he.eduservice.entity.excel.ExcelSubject;
import com.he.eduservice.service.EduSubjectService;
import com.he.exception.HeException;

public class ExcelListener extends AnalysisEventListener<ExcelSubject> {

    //想要读取数据库需要把业务层引过来 service
    private EduSubjectService eduSubjectService;

    public ExcelListener() {
    }

    //使用有参的构造方法把service读过来
    public ExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(ExcelSubject excelSubject, AnalysisContext analysisContext) {
        //一行一行读取数据
        if (excelSubject == null) {
            throw new HeException(20001, "添加失败 ");
        }
//        //通过查询数据库来判断数据是否重复 跟数据库中数据进行对比
        String OneSubjectName = excelSubject.getOneSubjectName();
        //判断是一级标题是否重复
        EduSubject eduSubject=this.existOneSubject(OneSubjectName);
        if (eduSubject == null) {
            //如果数据库中没有数据就新建数据，进行添加
            eduSubject = new EduSubject();
            eduSubject.setTitle(excelSubject.getOneSubjectName());
            eduSubject.setParentId("0");
            //添加到数据库中
            eduSubjectService.save(eduSubject);
        }
        String TwoSubjectName=excelSubject.getTwoSubjectName();
        //一行中的一级标题的id一定是二级标题的父id
        //获取一级标题id
        String parentId =eduSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(TwoSubjectName, parentId);
        //如果二级标题不重复 新建一个二级标题
        if(existTwoSubject==null){
            existTwoSubject=new EduSubject();
            existTwoSubject.setParentId(parentId);
            existTwoSubject.setTitle(TwoSubjectName);
            eduSubjectService.save(existTwoSubject);
        }
    }

    //把同样的抽取出来 ，变成一个类的 方法
    private EduSubject existOneSubject(String OneSubjectName) {
        //通过查询数据库来判断数据是否重复 跟数据库中数据进行对比
        QueryWrapper queryWrapper = new QueryWrapper();
        //判断数据库中是否有一级标题的名字
        queryWrapper.eq("title", OneSubjectName);
        //判断数据库中是否有一级是否有父节点 0
        queryWrapper.eq("parent_id", 0);
        EduSubject eduSubject = eduSubjectService.getOne(queryWrapper);
        return eduSubject;
    }
    //判断二级标题是否重复
    private EduSubject existTwoSubject(String TwoSubjectName,String parentId ) {
        //通过查询数据库来判断数据是否重复 跟数据库中数据进行对比
        QueryWrapper queryWrapper = new QueryWrapper();
        //判断数据库中是否有二级标题的名字
        queryWrapper.eq("title", TwoSubjectName);
        //判断数据库中是否有一级是否有父节点 0
        queryWrapper.eq("parent_id", parentId);
        EduSubject eduSubject = eduSubjectService.getOne(queryWrapper);
        return eduSubject;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
