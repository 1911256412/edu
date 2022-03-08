import request from '@/utils/request'

// export function getList(params) {
//   return request({
//     url: '/table/list',
//     method: 'get',
//     params
//   })
// }
export default {
 
    getList() {
        return request({
            //用`符号  模板字符串 
            url: `/eduservice/subject/getsubject`,
            method: 'get',
            //条件的对象   后端使用requestBody来获取 
        }
        )
    },
}
