package com.he.ucenter.controller;

import com.google.gson.Gson;
import com.he.exception.HeException;
import com.he.ucenter.entity.Member;
import com.he.ucenter.service.MemberService;
import com.he.util.ConstantPropertiesUtil;
import com.he.util.HttpClientUtils;
import com.he.utils.JwtUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WXapiController {

    @Resource
    private MemberService memberService;

    @GetMapping("login")
    public String getVx() {

        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String redirectUri = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUri = URLEncoder.encode(redirectUri, "utf-8");

        } catch (UnsupportedEncodingException e) {
            throw new HeException(20001, e.getMessage());
        }


        String url = String.format(
                //一个%s代表一个问号
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                "http://localhost:8160/api/ucenter/wx/callback",
                "atguigu"
        );
        return "redirect:" + url;
    }

    //获取扫描人信息
    @GetMapping("callback")
    public String callback(String code, String state) {
//向认证服务器发送请求换取access_token
        try {
            String baseAccessTokenUrl =
                    "https://api.weixin.qq.com/sns/oauth2/access_token" + "?appid=%s" +
                            "&secret=%s" +
                            "&code=%s" +
                            "&grant_type=authorization_code";
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code);
            //请求拼接好的地址 :用httpclient技术
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl, "utf-8");
            //取出每个中的 openid，和 access_token
            //使用gson（歌森）转换
            Gson gson = new Gson();
            HashMap accessTokenMap = gson.fromJson(accessTokenInfo, HashMap.class);
            String openid = (String) accessTokenMap.get("openid");
            String access_token = (String) accessTokenMap.get("access_token");

            //判断是否使用过微信
            Member member = memberService.getOpenidMember(openid);
            if (member == null) {
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userinfoUrl = String.format(
                        baseUserInfoUrl, access_token, openid);
                //发送请求
                String userinfo = HttpClientUtils.get(userinfoUrl);

                Gson gson1 = new Gson();
                HashMap info = gson1.fromJson(userinfo, HashMap.class);
                String nickname = (String) info.get("nickname");
                String headimgurl = (String) info.get("headimgurl");

                //如果没有数据插入 数据
                member=new Member();
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                member.setOpenid(openid);

                boolean save = memberService.save(member);
            }
            //加一个token解决跨域问题，利用jwt
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            throw new HeException(20001, "登录注册失败");
        }

    }
}
