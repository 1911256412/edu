import request from '@/utils/request'
export default {
    //1、创建订单
    createOrder(courseId) {
        return request({
            url: `/orderservice/order/saveOrder/${courseId}`,
            method: 'post'
        })
    },
    //查询订单 
    getOrder(OrderId) {
        return request({
            url: `/orderservice/order/getOrderInfo/${OrderId}`,
            method: 'get'
        })
    },
    //生成二维码
    CreateWXEEM(orderNo){
        return request({
            url: `/orderservice/paylog/createNative/${orderNo}`,
            method: 'get'
        })
    },
    //查询订单状态 
    queryStatus(OrderNo){
        return request({
            url: `/orderservice/paylog/getOrderStatus/${OrderNo}`,
            method: 'get'
        })
    }
}