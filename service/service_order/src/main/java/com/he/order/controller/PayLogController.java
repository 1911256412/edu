package com.he.order.controller;


import com.he.order.service.PayLogService;
import com.he.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付日志表; InnoDB free: 8192 kB 前端控制器
 * </p>
 *
 * @author hechunyu
 * @since 2022-03-05
 */
@RestController
@RequestMapping("/orderservice/paylog")
@CrossOrigin
public class PayLogController {

    @Resource
    private PayLogService payLogService;
    //创建二维码
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        //返回信息包含二维码
        Map map =payLogService.createNative(orderNo);

        return R.ok().data(map);
    }
    //查询订单支付状态，并且更新记录
    @GetMapping("getOrderStatus/{OrderNo}")
    public R queryPayStatus(@PathVariable String OrderNo){
        Map <String ,String > map =payLogService.queryPayStatus(OrderNo);
        if(map==null){

            return R.error().data("map",map);
        }
        if(map.get("trade_state").equals("SUCCESS")){
            //订单支付成功
            payLogService.updateOrderStatus(map);

            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

