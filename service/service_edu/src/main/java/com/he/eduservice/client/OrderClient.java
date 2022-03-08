package com.he.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-order")
public interface OrderClient {
    @GetMapping("/orderservice/order/isByCourse/{courseId}/{memerId}")
    public boolean isByCourse(@PathVariable("courseId") String courseId, @PathVariable("memerId") String memerId) ;

}
