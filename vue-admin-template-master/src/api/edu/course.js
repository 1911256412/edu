import request from '@/utils/request'

// export function getList(params) {
//   return request({
//     url: '/table/list',
//     method: 'get',
//     params
//   })
// }
export default {

    addCourse(eduCourseVo) {
        return request({
            //用`符号  模板字符串 
            url: `/eduservice/educourse/addCourse`,
            method: 'post',
            //条件的对象   后端使用requestBody来获取 
            data: eduCourseVo
        }
        )
    },
    //查询所有讲师 
    getAllTeacher() {
        return request({
            url: `/eduservice/teacher/findAll`,
            method: 'get',
        })

    },
    //得到所有分类标题  
    getAllSubject() {
        
        return request({
            url: `/eduservice/subject/getsubject`,
            method: 'get',
        })
    },
    
      //查询课程信息 
      getCourse(courseId) {
        return request({
            url: `/eduservice/educourse/getCourse/${courseId}`,
            method: 'get',
        })
    },
    //修改课程 
    updateCourse(eduCourseVo){
        return request({
            url: `/eduservice/educourse/updataCourse`,
            method: 'post',
            data: eduCourseVo
        })
    },

    //查询所有课程列表 
    getAllCourse(){
        return request({
            url: `/eduservice/educourse/getAllCourse`,
            method: 'get',
        })
    },
    //删除课程
    deleteCourse(courseId){
        return request({
            url: `/eduservice/educourse/deleteCourse/${courseId}`,
            method: 'delete',
        })
    },
   

}