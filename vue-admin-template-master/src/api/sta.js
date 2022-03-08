import request from '@/utils/request'

// export function getList(params) {
//   return request({
//     url: '/table/list',
//     method: 'get',
//     params
//   })
// }
export default {

    staCreate(day) {
        return request({
            //用`符号  模板字符串 
            url: `/service/ustatistics/createSta/${day}`,
            method: 'post',
            //条件的对象   后端使用requestBody来获取 
        }
        )
    },
    showChart(searchObj){
        return request({
            //用`符号  模板字符串 
            url: `/service/ustatistics/showChart/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method: 'get',
            //条件的对象   后端使用requestBody来获取 
        }
        )
    }
  

}