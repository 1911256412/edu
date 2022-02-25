package com.he.eduservice.controller;

import com.he.utils.R;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/eduservice/user")
@CrossOrigin//解决跨域问题
public class EduLoginController {

    //login登录方法
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }
    //info
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","zsan").data("avatar","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp06%2F200629214K14602-0-lp-380.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1647337984&t=35ae7a143aca0a242c8466f4932b4c30");
    }


}
