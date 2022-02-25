package com.he.eduservice.controller;


import com.he.eduservice.entity.EduVideo;
import com.he.eduservice.service.EduVideoService;
import com.he.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频; InnoDB free: 8192 kB 前端控制器
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-18
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Resource
    private EduVideoService eduVideoService;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
       eduVideoService.save(eduVideo);
        return R.ok();
    }
    //修改
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    //删除
    @DeleteMapping("deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable String videoId) {
        eduVideoService.removeById(videoId);
        return R.ok();
    }
    //通过id查询
    @GetMapping("getVideo/{videoId}")
    public R selectVideo(@PathVariable String videoId) {
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("video", eduVideo);
    }



}

