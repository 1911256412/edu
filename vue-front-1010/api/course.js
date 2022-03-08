import request from '@/utils/request'

export default {
    
    //通过条件分页查询课程 
    getByPageCourse(current,limit,searchObj) {
    return request({
      url: `/eduservice/frontcourse/getFrontCourse/${current}/${limit}`,
      method: 'post',
      data:searchObj
    })
  },
  //查询所有一级标题和二级标题 
   getSubject(){
       return request({
        url: `/eduservice/subject/getsubject`,
        method: 'get',
       })
   },
   //查询课程信息 
   getCourseWeb(courseId){
    return request({
        url: `/eduservice/frontcourse/getCourseByCourseId/${courseId}`,
        method: 'get',
       })
   }
}