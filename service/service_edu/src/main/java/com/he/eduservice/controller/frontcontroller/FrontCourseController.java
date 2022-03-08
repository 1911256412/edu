package com.he.eduservice.controller.frontcontroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.he.eduservice.client.OrderClient;
import com.he.eduservice.entity.EduChapter;
import com.he.eduservice.entity.EduCourse;
import com.he.eduservice.entity.vo.ChapterVo;
import com.he.eduservice.entity.vo.CourseWebVo;
import com.he.eduservice.entity.vo.FrontCourseVo;
import com.he.eduservice.service.EduChapterService;
import com.he.eduservice.service.EduCourseService;
import com.he.utils.JwtUtils;
import com.he.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/frontcourse")
public class FrontCourseController {

    @Resource
    private EduCourseService eduCourseService;

    @Resource
    private EduChapterService eduChapterService;

    @Resource
    private OrderClient orderClient;
    @PostMapping("getFrontCourse/{current}/{limit}")
    public R selectCourseByCondition(@PathVariable Integer current, @PathVariable Integer limit, @RequestBody(required = false) FrontCourseVo frontCourseVo) {

        Page<EduCourse> courseVoPage = new Page<>(current, limit);
        Map<String, Object> map = eduCourseService.selectFrontCourse(courseVoPage, frontCourseVo);
        return R.ok().data("course", map);
    }

    //根据课程id查询课程所有信息 ，和小结信息
    @GetMapping("getCourseByCourseId/{courseId}")
    public R selectByCourseId(@PathVariable String courseId, HttpServletRequest request) {

        //查询课程所有信息，三表联合查询
        CourseWebVo courseWebVo = eduCourseService.selectFrontWebCourse(courseId);

        List<ChapterVo> chapterOrVideo = eduChapterService.getChapterOrVideo(courseId);
        //判断是否已经支付过了
        String id = JwtUtils.getMemberIdByJwtToken(request);
        boolean isByCourse = orderClient.isByCourse(courseId, id);

        return R.ok().data("course", courseWebVo).data("chapter", chapterOrVideo).data("isByCourse",isByCourse);
    }
}
