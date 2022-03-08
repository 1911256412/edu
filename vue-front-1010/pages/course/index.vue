<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#" >全部</a>
                </li>
                <li
                  v-for="(item, index) in subjectNestedList"
                  v-bind:key="index"
                >
                  <a
                    :title="item.title"
                    href="javascript:void(0);"
                    @click="searchOne(item.id, index)"
                    >{{ item.title }}</a
                  >
                </li>
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li v-for="TwoSubject in subSubjectList" :key="TwoSubject.id">
                  <a
                    :title="TwoSubject.title"
                    href="#"
                    @click="searchTwo(TwoSubject.id, index)"
                    >{{ TwoSubject.title }}</a
                  >
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{ 'current bg-orange': buyCountSort != '' }">
                <a
                  title="销量"
                  href="javascript:void(0);"
                  @click="searchBuyCount()"
                  >销量
                  <span :class="{ hide: buyCountSort == '' }">↓</span>
                </a>
              </li>
              <li :class="{ 'current bg-orange': gmtCreateSort != '' }">
                <a
                  title="最新"
                  href="javascript:void(0);"
                  @click="searchGmtCreate()"
                  >最新
                  <span :class="{ hide: gmtCreateSort == '' }">↓</span>
                </a>
              </li>
              <li :class="{ 'current bg-orange': priceSort != '' }">
                <a
                  title="价格"
                  href="javascript:void(0);"
                  @click="searchPrice()"
                  >价格&nbsp;
                  <span :class="{ hide: priceSort == '' }">↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="course.total == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam"
              >没有相关数据，小编正在努力整理中...</span
            >
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="course.total > 0">
            <ul class="of" id="bna">
              <li v-for="cour in course.items" :key="cour.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img
                      :src="cour.cover"
                      class="img-responsive"
                      :alt="cour.title"
                    />
                    <div class="cc-mask">
                      <a
                        :href="'/course/'+cour.id"
                        :title="cour.title"
                        class="comm-btn c-btn-1"
                        >开始学习</a
                      >
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a
                      :href="'/course/'+cour.id"
                      :title="cour.title"
                      class="course-title fsize18 c-333"
                      >{{ cour.title }}</a
                    >
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span class="fr jgTag bg-green" v-if="cour.price == 0">
                      <i class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{ cour.buyCount }}人学习</i>
                      |
                      <i class="c-999 f-fA">{{ cour.viewCount }}评论</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{ undisable: !course.hasPrevious }"
              href="#"
              title="首页"
              @click.prevent="gotoPage(1)"
              >首</a
            >
            <a
              :class="{ undisable: !course.hasPrevious }"
              href="#"
              title="前一页"
              @click.prevent="gotoPage(course.current - 1)"
              >&lt;</a
            >
            <a
              v-for="page in course.pages"
              :key="page"
              :class="{
                current: course.current == page,
                undisable: course.current == page,
              }"
              :title="'第' + page + '页'"
              href="#"
              @click.prevent="gotoPage(page)"
              >{{ page }}</a
            >
            <a
              :class="{ undisable: !course.hasNext }"
              href="#"
              title="后一页"
              @click.prevent="gotoPage(course.current + 1)"
              >&gt;</a
            >
            <a
              :class="{ undisable: !course.hasNext }"
              href="#"
              title="末页"
              @click.prevent="gotoPage(course.pages)"
              >末</a
            >
            <div class="clear" />
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
import course from "@/api/course";
export default {
  data() {
    return {
      page: 1,
      course: {},
      subjectNestedList: [], // 一级分类列表
      subSubjectList: [], // 二级分类列表
      searchObj: {}, // 查询表单对象
      oneIndex: -1,
      twoIndex: -1,
      buyCountSort: "",
      gmtCreateSort: "",
      priceSort: "",
    };
  },
  created() {
    this.getCourseList();
    this.getSubjectList();
  },
  methods: {
    getCourseList() {
      course.getByPageCourse(1, 8, this.searchObj).then((response) => {
        this.course = response.data.data.course;
      });
    },
    gotoPage(page) {
      course.getByPageCourse(page, 8, this.searchObj).then((response) => {
        this.course = response.data.data.course;
      });
    },
    //查询所有一级标题和二级标题
    getSubjectList() {
      course.getSubject().then((response) => {
        this.subjectNestedList = response.data.data.items;
      });
    },
    searchOne(OneSubjectid, index) {
      //把一级分类id赋给二级分类的parentId
      this.searchObj.subjectParentId = OneSubjectid;

      this.gotoPage(1);
      //遍历所有一级分类，拿着一份分类的id和二级分类的parent比较
      for (let i = 0; i < this.subjectNestedList.length; i++) {
        //如果选中的一级分类id  等于其中便利的某一个一级级分类id，就可以得到他的二级分类 的信息
        var OneSubject = this.subjectNestedList[i];
        if (OneSubject.id == OneSubjectid) {
          this.subSubjectList = OneSubject.children;
        }
      }
    },
    //点击某个二级分类 遍历出元素
    searchTwo(TwoSubjectId, index) {
      this.searchObj.subjectId = TwoSubjectId;
      this.gotoPage(1);
    },
    searchBuyCount(){
      this.buyCountSort='1'
      this.gmtCreateSort='',
      this.priceSort='',
      this.searchObj.buyCountSort=this.buyCountSort
      this.searchObj.gmtCreateSort=this.gmtCreateSort
      this.searchObj.priceSort=this.priceSort
      this.gotoPage(1)
    },
    searchGmtCreate(){
      this.buyCountSort=''
      this.gmtCreateSort='1',
      this.priceSort='',
      this.searchObj.buyCountSort=this.buyCountSort
      this.searchObj.gmtCreateSort=this.gmtCreateSort
      this.searchObj.priceSort=this.priceSort
      this.gotoPage(1)
    },
    searchPrice(){
      this.buyCountSort=''
      this.gmtCreateSort='',
      this.priceSort='1',
      this.searchObj.buyCountSort=this.buyCountSort
      this.searchObj.gmtCreateSort=this.gmtCreateSort
      this.searchObj.priceSort=this.priceSort
      this.gotoPage(1)
    }
  },
};
</script><style scoped>
.active {
  background: #bdbdbd;
}
.hide {
  display: none;
}
.show {
  display: block;
}
</style>