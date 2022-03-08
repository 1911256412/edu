import request from '@/utils/request'

export default {
    getListIndex(){
        return request({
            //用`符号  模板字符串     //路径传值 
            //得到首页最热老师和课程 
            url: `/eduservice/index`,
            method: 'get',
        })
    },
}