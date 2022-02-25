package com.he.eduservice.service;

import com.he.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.he.eduservice.entity.vo.ChapterVo;
import sun.security.provider.certpath.CertId;

import java.util.List;

/**
 * <p>
 * 课程; InnoDB free: 8192 kB 服务类
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-18
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterOrVideo(String Cid);

    boolean deleteChapter(String chapterId);

    void removeByCourseId(String courseId);
}
