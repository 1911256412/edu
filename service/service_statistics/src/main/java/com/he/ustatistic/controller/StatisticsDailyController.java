package com.he.ustatistic.controller;


import com.he.ustatistic.service.StatisticsDailyService;
import com.he.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据; InnoDB free: 7168 kB 前端控制器
 * </p>
 *
 * @author hechunyu
 * @since 2022-03-06
 */
@RestController
@RequestMapping("/service/ustatistics")
@CrossOrigin
public class StatisticsDailyController {

    @Resource
    private StatisticsDailyService statisticsDailyService;

   //创建统计把注册人数放到数据库中
    @PostMapping("createSta/{day}")
    public R  createSta(@PathVariable  String day){
        statisticsDailyService.createStatistic(day);
        return R.ok();
    }
    //把数据查出来返回到前端，前端需要两个数组json类型
    @GetMapping("showChart/{type}/{begin}/{end}")
    public R showChart(@PathVariable  String type ,@PathVariable String begin ,@PathVariable String end ){
        Map<String ,Object> map =statisticsDailyService.showChart(type,begin,end);
        return R.ok().data(map);
    }


}

