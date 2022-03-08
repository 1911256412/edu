package com.he.controller;


import com.he.service.MsmService;
import com.he.utils.R;

import com.he.utils.RandomUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/msmservice")
@CrossOrigin
public class MsmController {

    @Resource
    private MsmService msmService;

    @Resource
    private RedisTemplate<String ,String > redisTemplate;
    @GetMapping("/send/{phone}")
    public R sendMessage(@PathVariable String phone) {
        //从redis中检查有没有验证码，如果有就不用阿里云发送，如果没有就利用阿里云发送验证码
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            System.out.println("redis验证码"+code);
            return R.ok();

        }
        //生成随机值 ，传递到阿里云进行发送
         code = RandomUtil.getFourBitRandom();
        //把随机值放到map集合中方便得到json数据
      //  Map<String, Object> map = new HashMap<>();
        //map.put("**code**", code+",**minute**:5");
        boolean isSend = msmService.send(phone, code);
        if (isSend) {
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            System.out.println("阿里云发送验证码"+code);
            return R.ok().data("code",code);
        } else {
            return R.error();
        }

    }
}
