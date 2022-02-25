package com.he.benteng.controller;

import com.he.benteng.service.OSSservice;
import com.he.utils.R;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OSScontroller {

    @Resource
    private OSSservice osSservice;

    @PostMapping
    //文件上传
    public R fileupload(MultipartFile file){

        String url =osSservice.upload(file);

        return  R.ok().data("url",url);
    }
}
