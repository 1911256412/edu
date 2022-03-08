import request from '@/utils/request'

// export function getList(params) {
//   return request({
//     url: '/table/list',
//     method: 'get',
//     params
//   })
// }
export default {
 
    addteacher(teacher) {
        return request({
            //用`符号  模板字符串 
            url: `/eduservice/teacher/save`,
            method: 'post',
            //条件的对象   后端使用requestBody来获取 
            data:teacher
        }
        )
    },
     getList(current,limit,teacherQuery) {
        return request({
            //用`符号  模板字符串 
            url: `/eduservice/teacher/selectByCondition/${current}/${limit}`,
            method: 'post',
            //条件的对象   后端使用requestBody来获取 
            data:teacherQuery
        }
        )
    },
    // 删除操作 
    deleteById(id) {
        return request({
            //用`符号  模板字符串 
            url: `/eduservice/teacher/delete/${id}`,
            method: 'delete',
        }
        )
    },
    //通过id查询信息
    getTeacherById(id) {
        return request({
            //用`符号  模板字符串 
            url: `/eduservice/teacher/getTeacher/${id}`,
            method: 'get',
        }
        )
    },
       //修改teacher信息 
       UpdateTeacher(teacher) {
        return request({
            //用`符号  模板字符串 
            url: `/eduservice/teacher/update`,
            method: 'post',
            data:teacher
        }
        )
    },
}
