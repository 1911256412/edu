package com.he.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.he.eduservice.entity.EduVideo;
import com.he.eduservice.mapper.EduVideoMapper;
import com.he.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.he.exception.HeException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频; InnoDB free: 8192 kB 服务实现类
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-18
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public void removeByCourseId(String courseId) {
        //通过courseId删除小结
        QueryWrapper<EduVideo > wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        int delete = baseMapper.delete(wrapper);
        if(delete==0){
            throw  new HeException(20001,"删除小结失败");
        }
    }


}
