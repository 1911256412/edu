import request from '@/utils/request'

export default {
    
    getByPageList(current,limit) {
    return request({
      url: `/eduservice/front/selectByPage/${current}/${limit}`,
      method: 'get',
    })

  },
  //通过id查询 课程讲师信息和课程信息 
   getTeacherAndCourse(teacherId){
       return request({
        url: `/eduservice/front/getTeacher/${teacherId}`,
        method: 'get',
       })
   }

}