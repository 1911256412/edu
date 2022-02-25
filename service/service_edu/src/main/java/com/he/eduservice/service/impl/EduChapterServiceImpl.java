package com.he.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.he.eduservice.entity.EduChapter;
import com.he.eduservice.entity.EduVideo;
import com.he.eduservice.entity.vo.ChapterVo;
import com.he.eduservice.entity.vo.VideoVo;
import com.he.eduservice.mapper.EduChapterMapper;
import com.he.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.he.eduservice.service.EduVideoService;
import com.he.exception.HeException;
import net.bytebuddy.description.method.ParameterList;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程; InnoDB free: 8192 kB 服务实现类
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-18
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterOrVideo(String cid) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", cid);
        //根据课程id查询所有章节
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);
        //根据课程id查询所有小结
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", cid);
        List<EduVideo> eduVideos = eduVideoService.list(videoQueryWrapper);
        List<ChapterVo> chapterVoList = new ArrayList<>();
        //遍历所有章节 ，章节id等于小结的chapterid,把值赋给educhapterVo
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            List<VideoVo> videoVos = new ArrayList<>();
            //遍历所有小结video
            for (EduVideo eduVideo : eduVideos) {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo, videoVo);
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    videoVos.add(videoVo);
                    chapterVo.setChildren(videoVos);
                }
            }
            chapterVoList.add(chapterVo);
        }
        return chapterVoList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        // 判断章节里面是否有小结 ，如果有小结就不可以删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(wrapper);
        if (count > 0) {
            throw new HeException(20001, "章节中有小结不能删除 ");
        } else {
            int result = baseMapper.deleteById(chapterId);
            //成功 1>0   失败 0>0
            return result > 0;
        }

    }

    @Override
    public void removeByCourseId(String courseId) {
        //通过courseId删除章节
        QueryWrapper<EduChapter> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        int i = baseMapper.delete(wrapper);
        if(i==0){
            throw new HeException(20001,"删除章节失败");
        }
    }
}
