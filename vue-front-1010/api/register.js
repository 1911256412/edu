import request from '@/utils/request'

export default {
    //发送短信验证码 
    mobileSend(phone){
        return request({
            //用`符号  模板字符串     //路径传值 
            url: `/msmservice/send/${phone}`,
            method: 'get',
        })
    },
    //注册 
    register(memberVo){
        return request({
            //用`符号  模板字符串     //路径传值 
            url: `/api/ucenter/member/register`,
            method: 'post',
            data:memberVo
        })
    }
}