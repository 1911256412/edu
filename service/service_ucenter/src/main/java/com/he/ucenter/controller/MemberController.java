package com.he.ucenter.controller;


import com.he.entity.UserInfo;
import com.he.ucenter.entity.Member;
import com.he.ucenter.entity.vo.MemberVo;
import com.he.ucenter.service.MemberService;
import com.he.utils.JwtUtils;
import com.he.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表; InnoDB free: 8192 kB 前端控制器
 * </p>
 *
 * @author hechunyu
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/api/ucenter/member")
@CrossOrigin
public class MemberController {

    @Resource
    private MemberService memberService;


    @PostMapping("userlogin")
    public R login(@RequestBody MemberVo memberVo) {
        System.out.println("登录信息" + memberVo);

        String token = memberService.login(memberVo);

        return R.ok().data("token", token);
    }

    //注册
    @PostMapping("register")
    public R register(@RequestBody MemberVo memberVo) {
        boolean b = memberService.register(memberVo);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    //通过token获取用户信息
    @GetMapping("memerInfo")
    public R memberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(memberId);
        System.out.println("******获取的用户信息" + member);
        return R.ok().data("userInfo", member);
    }

    //通过id查询用户信息
    @GetMapping("getUser/{id}")
    public UserInfo getUserInfo(@PathVariable String id) {


        Member member = memberService.getById(id);

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(member, userInfo);

        return userInfo;
    }
    //查询某天 注册数量
    @GetMapping("registNum/{day}")
    public R registCount(@PathVariable  String day){
        Integer num =memberService.selectRegist(day);
        return R.ok().data("num",num);
    }
}

