package com.he.order.service.impl;

import com.he.entity.CourseInfo;
import com.he.entity.UserInfo;
import com.he.order.client.CourseClient;
import com.he.order.client.UserClient;
import com.he.order.entity.Order;
import com.he.order.mapper.OrderMapper;
import com.he.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.he.utils.OrderNoUtil;
import com.he.utils.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 订单; InnoDB free: 8192 kB 服务实现类
 * </p>
 *
 * @author hechunyu
 * @since 2022-03-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Resource
    private CourseClient courseClient;
    @Resource
    private UserClient userClient;
    public String createOrder(String courseId, String id) {
        Order order=new Order();
        CourseInfo courseInfo = courseClient.selectBycourse(courseId);
        UserInfo userInfo = userClient.getUserInfo(id);
        order.setCourseId(courseId);
        order.setTeacherName(courseInfo.getTeacherName());
        order.setTotalFee(courseInfo.getPrice());
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setMemberId(userInfo.getId());
        order.setMobile(userInfo.getMobile());
        order.setPayType(1);
        order.setStatus(0);
        order.setNickname(userInfo.getNickname());
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
