package com.he.benteng.service.serviceimpl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.he.benteng.service.uploadService;
import com.he.benteng.utils.aliyunSdkUtil;
import com.he.benteng.utils.constPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class uploadServiceImpl implements uploadService {

    public String upload(MultipartFile file) {
        /**
         * 流式上传接口
         * @param accessKeyId
         * @param accessKeySecret
         * @param title
         * @param fileName
         * @param inputStream
         */
        String videoId = null;
        try {
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String title=fileName.substring(0,fileName.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(constPropertiesUtils.ACCESS_KEY_ID, constPropertiesUtils.ACCESS_KEY_SECERT, title, fileName, inputStream);
            /* ECS部署区域*/
            // request.setEcsRegionId("cn-shanghai");
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoId;
    }

    public void removeVideo(String videoId) {
        try{
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        /*请求示例*/
        DefaultAcsClient client = aliyunSdkUtil.initVodClient(constPropertiesUtils.ACCESS_KEY_ID, constPropertiesUtils.ACCESS_KEY_SECERT);
        DeleteVideoResponse response = new DeleteVideoResponse();
        request.setVideoIds(videoId);
        client.getAcsResponse(request);
        response = client.getAcsResponse(request);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
    }
}
