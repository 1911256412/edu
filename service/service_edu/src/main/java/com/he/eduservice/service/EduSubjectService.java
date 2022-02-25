package com.he.eduservice.service;

import com.he.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.he.eduservice.entity.vo.OneSubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目; InnoDB free: 8192 kB 服务类
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-17
 */
public interface EduSubjectService extends IService<EduSubject> {
    void addFile(MultipartFile file, EduSubjectService eduSubjectService) ;

    List<OneSubjectVo> getSubjectAll();
}
