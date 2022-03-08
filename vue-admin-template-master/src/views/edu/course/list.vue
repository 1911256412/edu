<template>
  <div class="app-container">
    <!--查询表单-->
<el-form :inline="true" class="demo-form-inline">
<el-form-item>
<el-input v-model="course.title" placeholder="课程名"/>
</el-form-item>
<el-form-item>
<el-select v-model="course.status" clearable placeholder="发布状态">
<el-option :value="Draft"  label="未发布"/>
<el-option :value="Normal" label="已发布"/>
</el-select>
<el-button type="primary" icon="el-icon-search" @click="List()">查
询</el-button>
<el-button type="default" @click="resetData()">清空</el-button>
</el-form-item>
</el-form>

    <!-- 表格 -->
<el-table
:data="list"
border
fit
highlight-current-row>
<el-table-column
label="序号"
width="70"
align="center">
<template slot-scope="scope">
 {{ (current - 1) * limit + scope.$index + 1 }}
</template>
</el-table-column>
<el-table-column prop="title" label="课程名称" width="80" />
<el-table-column label="发布状态" width="80">
<template slot-scope="scope">
  {{ scope.row.status === "Normal" ? "已发布" : "未发布" }}
</template>
</el-table-column>
<el-table-column prop="lessonNum" label="课时数" />
<el-table-column prop="gmtCreate" label="添加时间" width="160"/>
<el-table-column prop="viewCount" label="浏览数量" width="60" />
<el-table-column label="操作" width="150" align="center">
<template slot-scope="scope">
<router-link :to="'/edu/course/info/'+scope.row.id">
<el-button type="text" size="mini" icon="el-icon-edit">编辑课程信息</el-button>
</router-link>
<router-link :to="'/edu/course/chapter/'+scope.row.id">
<el-button type="text" size="mini" icon="el-icon-edit">编辑课程大纲</el-button>
</router-link>
<el-button type="text" size="mini" @click="deleteCourseById(scope.row.id)" icon="el-icon-delete">删除</el-button>
</template>
</el-table-column>
</el-table>
  </div>
</template> 
<script>
// 引入teacher.js文件调用接口
import course from "@/api/edu/course";
export default {
  // 定义变量初始值  ,接口中三个参数
  data() {
    return {
      list: null, //执行之后接口返回的集合
        current: 1,
        limit: 3,
        total: 0,
        course: {}, //条件封装的对象
    };
  },
  created() {
    //在界面渲染之前执行
    this.List();
  },
  //调用teacher.js中方法
  methods: {
      deleteCourseById(courseId){
          this.courseId=courseId
          course.deleteCourse(this.courseId)
          .then(response=>{
             this.$message({
              type: "success",
              message: "删除成功!",
            });
            this.List()
          })
      },
    List(){
      course
        .getAllCourse()
        .then((response) => {
          //成功返回的数据
          this.list = response.data.CourseList;
        })
        .catch((error) => {
          //失败返回的数据
          console.log("失败");
        });
    },
    resetData() {
      // 清空操作 ，
      //先把对象中数据清空，由于是双向绑定清空一个就可以
      this.course = {};
      //在查询数据
      this.List();
    },
    removeDataById(id) {},
  },
};
</script>
