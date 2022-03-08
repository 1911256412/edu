package com.he.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.he.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 讲师; InnoDB free: 8192 kB 服务类
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-11
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> selectByPage(Page<EduTeacher> page);
}
