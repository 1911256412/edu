<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>
    <el-steps
      :active="1"
      process-status="wait"
      align-center
      style="margin-bottom: 40px"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>
    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input
          v-model="courseInfo.title"
          placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"
        />
      </el-form-item>
      <!-- 所属分类 TODO -->
      <el-form-item label="课程分类">
        <!-- 一级分类 -->
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="请选择"
          @change="subjectLevelChange"
        >
          <el-option
            v-for="oneSubject in OneSubjectList"
            :key="oneSubject.id"
            :label="oneSubject.title"
            :value="oneSubject.id"
          />
          <!-- 二级分类 -->
        </el-select>
        <el-select v-model="courseInfo.subjectId" placeholder="请选择">
          <el-option
            v-for="twoSubject in TwoSubjectList"
            :key="twoSubject.id"
            :label="twoSubject.title"
            :value="twoSubject.id"
          />
        </el-select>
      </el-form-item>
      <!-- 课程讲师 TODO -->
      <el-form-item label="课程讲师">
        <el-select v-model="courseInfo.teacherId" placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number
          :min="0"
          v-model="courseInfo.lessonNum"
          controls-position="right"
          placeholder="请填写课程的总课时数"
        />
      </el-form-item>
      <!-- 课程简介 TODO -->
      <!-- 课程简介-->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description" />
      </el-form-item>
      <!-- <el-form-item label="课程简介">
        <el-input
          :min="0"
          v-model="courseInfo.description"
          controls-position="right"
          placeholder=""
        />
      </el-form-item> -->

      <!-- 课程封面 TODO -->
      <!-- 课程封面-->
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API + '/eduoss/fileoss'"
          class="avatar-uploader"
        >
          <img :src="courseInfo.cover" />
        </el-upload>
      </el-form-item>
      <el-form-item label="课程价格">
        <el-input-number
          :min="0"
          v-model="courseInfo.price"
          controls-position="right"
          placeholder="免费课程请设置为0元"
        />
      </el-form-item>

      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate"
          >保存并下一步</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import Tinymce from "@/components/Tinymce";
import course from "@/api/edu/course";
export default {
  components: { Tinymce },
  data() {
    return {
      courseInfo: {
        title: "",
        subjectId: "",
        subjectParentId: "",
        teacherId: "",
        lessonNum: 0,
        description: "",
        cover: "/static/01.jpg",
        price: 0,
      },
      courseId: "",
      BASE_API: process.env.BASE_API, //获取dev.env.js里面地址
      teacherList: [],
      //一级分类
      OneSubjectList: [],
      //二级分类
      TwoSubjectList: [],
      saveBtnDisabled: false, // 保存按钮是否禁用
    };
  },
  // created() {
  //   //如果路径有courseId就进行查询
  //   if (this.$route.params && this.$route.params.id) {
  //     //进行修改操作
  //     this.courseId = this.$route.params.id;
  //     this.Course();
  //   } else {
  //     //进行添加操作
  //     this.getTeacherList(), this.getOneSubject();
  //   }
  // },
  created() {
    this.init();
  },
  watch: {
    $route(to, from) {
      this.init();
    },
  },
  methods: {
    init() {
      //判断是否有id值
      if (this.$route.params && this.$route.params.id) {
        //从路径中获取id值
        //调用根据id查询的方法
        //进行修改操作
        this.courseId = this.$route.params.id;
        this.Course();
      } else {
        //所以用监听会执行两次这段代码需要把这段抽取出来放到init（）方法中
        //进行添加操作
        this.getTeacherList(), this.getOneSubject();
         //清空courseInfo中的值 因为created只会执行一次
        this.courseInfo = {
           cover: "/static/01.jpg",
        };

      }
    },
    Course() {
      //回显课程信息
      course.getCourse(this.courseId).then((response) => {
        this.courseInfo = response.data.items;
        //回显所有一级分类和二级分类
        course.getAllSubject().then((response) => {
          this.OneSubjectList = response.data.items;
          for (var i = 0; i < this.OneSubjectList.length; i++) {
            //得到每一个一级分类
            var oneSubject = this.OneSubjectList[i];
            //比较当前courseinfo里面的一级分类的id和所有的一级分类id
            if (this.courseInfo.subjectParentId === oneSubject.id) {
              //获取一级分类所有的二级分类
              this.TwoSubjectList = oneSubject.children;
            }
          }
          //显示所有老师
          this.getTeacherList();
        });
      });
    },
    //上传封面成功调用的方法
    handleAvatarSuccess(res, file) {
      this.courseInfo.cover = res.data.url;
    },
    //上传之前调用的方法
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    //点击一个一级分类，触发change，显示二级分类
    subjectLevelChange(value) {
      //遍历所有一级分类 ，一级分类中有二级分类
      for (var i = 0; i < this.OneSubjectList.length; i++) {
        //如果选中一级分类的id等于,每次遍历出来的id
        if (this.OneSubjectList[i].id === value) {
          this.TwoSubjectList = this.OneSubjectList[i].children;
          //每次显示二级分类之后把二级分类的id清空
          this.courseInfo.subjectId = "";
        }
      }
    },
    getOneSubject() {
      course.getAllSubject().then((reponse) => {
        this.OneSubjectList = reponse.data.items;
      });
    },
    getTeacherList() {
      course.getAllTeacher().then((reponse) => {
        this.teacherList = reponse.data.items;
      });
    },
    //添加课程 
    saveCourse(){
        course
        .addCourse(this.courseInfo)
        .then((response) => {
          this.$message({
            type: "success",
            message: "添加课程成功",
          });
          this.$router.push({ path: "/edu/course/chapter/" + response.data.id });
        })
        .catch((error) => {
          this.$message({
            type: "error",
            message: "添加课程失败 ",
          });
        });
    },
    updateCourse(){
      course.updateCourse(this.courseInfo)
      .then(response=>{
            this.$message({
            type: "success",
            message: "修改课程成功",
          });
          this.$router.push({ path: "/edu/course/chapter/" + this.courseId });
      })
    },
    saveOrUpdate() {
      if(!this.courseInfo.id){
        //执行添加操作，如果为空
          this.saveCourse()
      }else{
        //如果不为执行修改操作 
          this.updateCourse()
      }
    },
  },
};
</script>
<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>