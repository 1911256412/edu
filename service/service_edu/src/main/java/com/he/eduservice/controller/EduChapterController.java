package com.he.eduservice.controller;


import com.he.eduservice.entity.EduChapter;
import com.he.eduservice.entity.vo.ChapterVo;
import com.he.eduservice.service.EduChapterService;
import com.he.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程; InnoDB free: 8192 kB 前端控制器
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-18
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Resource
    private EduChapterService eduChapterService;

    @GetMapping("getChapter/{courseId}")
    public R getChapter(@PathVariable String courseId) {
        List<ChapterVo> chapterVoList = eduChapterService.getChapterOrVideo(courseId);
        return R.ok().data("ChapterItems", chapterVoList);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    //删除章节
    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        //删除章节需要判断是否有小结
      boolean flag= eduChapterService.deleteChapter(chapterId);
      if(flag){
          return R.ok();

      }else {
          return R.error();
      }
    }
    //根据章节id查询章节
    @GetMapping("getChapterById/{chapterId}")
    public R selectChapterById(@PathVariable String chapterId ){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

}

