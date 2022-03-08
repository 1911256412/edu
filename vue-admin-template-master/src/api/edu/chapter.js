import request from '@/utils/request'

// export function getList(params) {
//   return request({
//     url: '/table/list',
//     method: 'get',
//     params
//   })
// }
export default {

    //得到所有章节和小结
    GetChapterAndVideo(courseId) {
        return request({
            //用`符号  模板字符串     //路径传值 
            url: `/eduservice/chapter/getChapter/${courseId}`,
            method: 'get',
        }
        )
    },
     //添加章节 
     addChapter(chapter) {
        return request({
            //用`符号  模板字符串     //路径传值 
            url: `/eduservice/chapter/addChapter`,
            method: 'post',
            data:chapter
        }
        )
    },
    //根据章节id获取章节  
     getChapter(chapterId) {
        return request({
            //用`符号  模板字符串     //路径传值 
            url: `/eduservice/chapter/getChapterById/${chapterId}`,
            method: 'get',
        }
        )
    },
    //修改章节 
    updateChapter(eduChapter){
     
        return request({
            //用`符号  模板字符串     //路径传值 
            url: `/eduservice/chapter/updateChapter`,
            method: 'post',
            data:eduChapter
        })
    }
    //删除章节 
    ,
    deleteChapter(chapterId){
        return request({
            //用`符号  模板字符串     //路径传值 
            url: `/eduservice/chapter/deleteChapter/${chapterId}`,
            method: 'delete',
        })
    }
}