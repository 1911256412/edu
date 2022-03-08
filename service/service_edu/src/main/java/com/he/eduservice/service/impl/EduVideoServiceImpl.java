package com.he.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.he.eduservice.client.VodClient;
import com.he.eduservice.entity.EduVideo;
import com.he.eduservice.mapper.EduVideoMapper;
import com.he.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.he.exception.HeException;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private VodClient vodClient;

    @Override
    public void removeByCourseId(String courseId) {
        //根据课程id删除所有小结
        //先通过id把所有videoid查询出来
        QueryWrapper<EduVideo> querywrapper = new QueryWrapper<>();
        querywrapper.eq("course_id", courseId);
        querywrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(querywrapper);
        //把对象集合变成String集合
        List<String> stringList = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)){
                stringList.add(eduVideo.getVideoSourceId());
            }
        }
        if (stringList.size() > 0) {
            vodClient.deleteBatch(stringList);
        }
        //通过courseId删除小结
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();

        wrapper.eq("course_id", courseId);
        int delete = baseMapper.delete(wrapper);
        if (delete == 0) {
            throw new HeException(20001, "删除小结失败");
        }
    }


}
