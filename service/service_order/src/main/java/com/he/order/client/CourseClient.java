package com.he.order.client;

import com.he.entity.CourseInfo;
import com.he.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-edu")
public interface CourseClient {
    //通过课程id进行查询

    @GetMapping("/eduservice/educourse/getByCourseId/{courseId}")
    public CourseInfo selectBycourse(@PathVariable("courseId") String courseId) ;
}
