package com.he.benteng.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.he.benteng.utils.constPropertiesUtils;
import com.he.utils.R;
import org.springframework.web.bind.annotation.*;

import static com.he.benteng.utils.aliyunSdkUtil.initVodClient;

@RestController
@CrossOrigin
@RequestMapping("/vod/video")
public class VideoPlayerController {

    @GetMapping("/getPlayAuth/{videoId}")
    public R getPlayerAuth(@PathVariable String videoId) {

        try {


            DefaultAcsClient client = initVodClient(constPropertiesUtils.ACCESS_KEY_ID, constPropertiesUtils.ACCESS_KEY_SECERT);
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            response = client.getAcsResponse(request);
            //播放凭证
            String playAuth = response.getPlayAuth();
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
            return R.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        return R.error();

    }
}
