<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number
          v-model="teacher.sort"
          controls-position="right"
          :min="0"
        />
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
数据类型一定要和取出的json中的一致，否则没法回填
因此，这里value使用动态绑定的值，保证其数据类型是number
-->
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" />
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" />
      </el-form-item>
      <!-- 讲师头像：TODO -->
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar" />
        <!-- 文件上传按钮 -->
        <el-button
          type="primary"
          icon="el-icon-upload"
          @click="imagecropperShow = true"
          >更换头像
        </el-button>
        <!--
v-show：是否显示上传组件
:key：类似于id，如果一个页面多个图片上传控件，可以做区分
:url：后台上传的url地址
@close：关闭上传组件
@crop-upload-success：上传成功后的回调 -->
        <image-cropper
                        v-show="imagecropperShow"
                        :width="300"
                        :height="300"
                        :key="imagecropperKey"
                        :url="BASE_API+'/eduoss/fileoss'"
                        field="file"
                        @close="close"
                        @crop-upload-success="cropSuccess"/>
     
      </el-form-item>
      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate"
          >保存</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
// 引入teacher.js文件调用接口
import teacherApi from "@/api/edu/teacher";
import ImageCropper from "@/components/ImageCropper";
import PanThumb from "@/components/PanThumb";
export default {
  //声明组件
  components: { ImageCropper, PanThumb },
  // 定义变量初始值  ,接口中三个参数
  data() {
    return {
      teacher: {

      },
       //上传弹框组件是否显示
      imagecropperShow:false,
      imagecropperKey:0,//上传组件key值
      BASE_API:process.env.BASE_API, //获取dev.env.js里面地址
      saveBtnDisabled:false  // 保存按钮是否禁用,
    }
  },

  created() {
    this.init();
  },
  watch: {  
    $route(to, from) {
      this.init();
    },
  },
  //调用teacher.js中方法
  methods: {
    cropSuccess(data) {
      this.imagecropperShow = false;
     // alert(data.url)
      this.teacher.avatar = data.url;
      //唯一标识加 1 让每次都是不同的
      this.imagecropperKey=this.imagecropperKey+1;
      
    },
    close() {
      //关闭上传弹窗方法
      this.imagecropperShow = false; 
      //唯一标识加 1  让每次都是不同的
      this.imagecropperKey=this.imagecropperKey+1;
    },

    init() {
      //判断是否有id值
      if (this.$route.params && this.$route.params.id) {
        //从路径中获取id值
        const id = this.$route.params.id;
        //调用根据id查询的方法
        this.getTeacher(id);
      } else {
        //清空teacher中的值 因为created只会执行一次
        //所以用监听会执行两次这段代码需要把这段抽取出来放到init（）方法中
        this.teacher = {};
      }
    },
    getTeacher(id) {
      teacherApi
        .getTeacherById(id)
        .then((reponse) => {
          this.teacher = reponse.data.items;
        })
        .catch((error) => {
          this.$message({
            type: "error",
            message: "获取数据失败",
          });
        });
    },
    saveOrUpdate() {
      if (!this.teacher.id) {
        this.saveTeacher();
      } else {
        this.updateTeacher();
      }
    },
    saveTeacher() {
      //释放保存按钮
      this.saveBtnDisabled = true;
      teacherApi
        .addteacher(this.teacher)
        .then((reponse) => {
          //提示信息
          this.$message({
            type: "success",
            message: "保存成功 !",
          });
          //路由跳转到显示界面
          this.$router.push({ path: "/teacher/teacherlist" });
        })
        .catch((error) => {
          this.$message({
            type: "error",
            message: "保存失败  !",
          });
        });
    },
    updateTeacher() {
      teacherApi
        .UpdateTeacher(this.teacher)
        .then((reponse) => {
          this.$message({
            type: "success",
            message: "修改成功 !",
          });
          //路由跳转到显示界面
          this.$router.push({ path: "/teacher/teacherlist" });
        })
        .catch((error) => {
          this.$message({
            type: "error",
            message: "添加失败!",
          });
        });
    },
  },
};
</script>
 