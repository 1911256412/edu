package com.he.eduservice.client;

import com.he.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "service-vod" ,fallback=VodClientImpl.class)
public interface VodClient {
    //删除云端视频
    @DeleteMapping("/vod/video/deleteVideo/{videoId}")
    public R deleteVideoByid(@PathVariable("videoId") String videoId );

    //删除多个视频
    @DeleteMapping("/vod/video/deletebatch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList);
}
