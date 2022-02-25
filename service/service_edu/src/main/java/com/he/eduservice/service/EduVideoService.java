package com.he.eduservice.service;

import com.he.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频; InnoDB free: 8192 kB 服务类
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-18
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);


}
