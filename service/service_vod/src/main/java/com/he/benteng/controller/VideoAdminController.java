package com.he.benteng.controller;

import com.he.benteng.service.uploadService;
import com.he.utils.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/vod/video")
@CrossOrigin
public class VideoAdminController {

    @Resource
    private uploadService uploadService;

    @PostMapping("upload")
    public R uploadVideo(MultipartFile file){

        String videoId =uploadService.upload(file);
        return R.ok().data("videoId",videoId);
    }
    //删除云端视频
    @DeleteMapping("deleteVideo/{videoId}")
    public R deleteVideoByid(@PathVariable  String videoId ){
        uploadService.removeVideo(videoId);

        return R.ok();
    }
    //删除多个视频
    @DeleteMapping("deletebatch")
    public R deleteBatch(@RequestParam("videoList") List videoList){
        uploadService.deleteBatch(videoList);
        return R.ok();
    }

}
