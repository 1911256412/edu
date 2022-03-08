<template>
  <div class="app-container">
    <!--查询表单-->
<el-form :inline="true" class="demo-form-inline">
<el-form-item>
<el-input v-model="teacherQuery.name" placeholder="讲师名"/>
</el-form-item>
<el-form-item>
<el-select v-model="teacherQuery.level" clearable placeholder="讲师头衔">
<el-option :value="1" label="高级讲师"/>
<el-option :value="2" label="首席讲师"/>
</el-select>
</el-form-item>
<el-form-item label="添加时间">
<el-date-picker
v-model="teacherQuery.begin"
type="datetime"
placeholder="选择开始时间"
value-format="yyyy-MM-dd HH:mm:ss"
default-time="00:00:00"
/>
</el-form-item>
<el-form-item>
<el-date-picker
v-model="teacherQuery.end"
type="datetime"
placeholder="选择截止时间"
value-format="yyyy-MM-dd HH:mm:ss"
default-time="00:00:00"
/>
</el-form-item>
<el-button type="primary" icon="el-icon-search" @click="List()">查
询</el-button>
<el-button type="default" @click="resetData()">清空</el-button>
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
<el-table-column prop="name" label="名称" width="80" />
<el-table-column label="头衔" width="80">
<template slot-scope="scope">
 {{ scope.row.level===1?'高级讲师':'首席讲师' }}
</template>
</el-table-column>
<el-table-column prop="intro" label="资历" />
<el-table-column prop="gmtCreate" label="添加时间" width="160"/>
<el-table-column prop="sort" label="排序" width="60" />
<el-table-column label="操作" width="200" align="center">
<template slot-scope="scope">
<router-link :to="'/teacher/edit/'+scope.row.id">
<el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
</router-link>
<el-button type="danger"  size="mini" icon="el-icon-delete"
@click="removeDataById(scope.row.id)">删除</el-button>
</template>
</el-table-column>
</el-table>
<!--分页操作 -->
<el-pagination
  background
  layout="total, prev, pager, next, jumper"
  @current-change="List"
  :page-size="limit"
  :current-page="current"
  style="padding: 30px  0;text-align: center;"
  :total="total">
  </el-pagination>
  </div>
</template>

<script>
// 引入teacher.js文件调用接口
import teacher from "@/api/edu/teacher";
export default {
  // 定义变量初始值  ,接口中三个参数
  data() {
    return {
      list: null, //执行之后接口返回的集合
      current: 1,
      limit: 3,
      total:0,
      teacherQuery: {}, //条件封装的对象
    };
  },
  created() {
    //在界面渲染之前执行
    this.List();
  },
  //调用teacher.js中方法
  methods: {
    List(current=1) {
      this.current=current
      teacher
        .getList(this.current, this.limit, this.teacherQuery)
        .then((reponse) => {
          //成功返回的数据
          this.list = reponse.data.rows
          this.total=reponse.data.total
         // console.log(this.list);
        })
        .catch((error) => {
          //失败返回的数据
          console.log("失败");
        })
      
    },
    resetData(){
        // 清空操作 ，
        //先把对象中数据清空，由于是双向绑定清空一个就可以 
        this.teacherQuery={}
        //在查询数据
        this.List()
        
    },
    removeDataById(id){
      // teacher.deleteById(id).then(reponse=>{ }).catch(error=>{ })
       this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
         teacher.deleteById(id).
         then(reponse=>{//删除成功 需要提示信息 
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          //回到列表界面 
          this.List(this.current)
         })
         
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });         
        })

    }
  },
};
</script>
