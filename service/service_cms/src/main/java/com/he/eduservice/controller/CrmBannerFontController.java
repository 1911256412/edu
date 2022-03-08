package com.he.eduservice.controller;

import com.he.eduservice.entity.CrmBanner;
import com.he.eduservice.service.CrmBannerService;
import com.he.utils.R;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/cmsservice/frontcrmbanner")
@CrossOrigin
public class CrmBannerFontController {

    @Resource
    private CrmBannerService crmBannerService;


    @GetMapping("getAllBanner")
    public R selectAllBanner(){
        //自己定义一个方法为了使用redis方便
       List<CrmBanner> list= crmBannerService.selectAllBanner();
        return R.ok().data("items",list);
    }
}
