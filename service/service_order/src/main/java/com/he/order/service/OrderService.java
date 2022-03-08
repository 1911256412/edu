package com.he.order.service;

import com.he.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单; InnoDB free: 8192 kB 服务类
 * </p>
 *
 * @author hechunyu
 * @since 2022-03-05
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String id);
}
