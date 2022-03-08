package com.he.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.he.order.entity.Order;
import com.he.order.service.OrderService;
import com.he.utils.JwtUtils;
import com.he.utils.R;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单; InnoDB free: 8192 kB 前端控制器
 * </p>
 *
 * @author hechunyu
 * @since 2022-03-05
 */
@RestController
@RequestMapping("/orderservice/order")
@CrossOrigin
public class OrderController {
    @Resource
    private OrderService orderService;

    //生成一个订单 向订单中添加数据,返回一个订单id
    @PostMapping("saveOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        //通过request获取token，利用jwt获取用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);
        //创建订单

        String orderNo = orderService.createOrder(courseId, id);
        return R.ok().data("orderNo", orderNo);
    }

    //根据订单id查询订单信息
    @GetMapping("getOrderInfo/{OrderId}")
    public R getOrdeInfo(@PathVariable String OrderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", OrderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("order", order);
    }

    //根据用户id和课程id查询是否购买课程
    @GetMapping("isByCourse/{courseId}/{memerId}")
    public boolean isByCourse(@PathVariable String courseId, @PathVariable String memerId) {

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memerId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        return count > 0;
    }

}

