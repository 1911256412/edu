import request from '@/utils/request'

// export function getList(params) {
//   return request({
//     url: '/table/list',
//     method: 'get',
//     params
//   })
// }
export default {
    addVideo(eduVideo){
        return request({
            //用`符号  模板字符串     //路径传值 
            url: `/eduservice/video/addVideo`,
            method: 'post',
            data:eduVideo
        })
    },
    deleteVideoById(VideoId){
        return request({
            //用`符号  模板字符串     //路径传值 
            url: `/eduservice/video/deleteVideo/${VideoId}`,
            method: 'delete',
        })
    },
     //删除云端视频 
     deleteVideoByvideoID(videoId){
        return request({
            url: `/vod/video/deleteVideo/${videoId}`,
            method: 'delete',
        })
    }
}