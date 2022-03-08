package com.he.order.client;

import com.he.entity.CourseInfo;
import com.he.entity.UserInfo;
import com.he.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-ucenter")
public interface UserClient {

    //通过id查询用户信息
    @GetMapping("/api/ucenter/member/getUser/{id}")
    public UserInfo getUserInfo(@PathVariable("id") String id);
}
