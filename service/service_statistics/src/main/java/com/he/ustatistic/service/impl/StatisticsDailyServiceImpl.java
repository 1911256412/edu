package com.he.ustatistic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.he.ustatistic.client.MemberClient;
import com.he.ustatistic.entity.StatisticsDaily;
import com.he.ustatistic.mapper.StatisticsDailyMapper;
import com.he.ustatistic.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.he.utils.R;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据; InnoDB free: 7168 kB 服务实现类
 * </p>
 *
 * @author hechunyu
 * @since 2022-03-06
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Resource
    private MemberClient memberClient;

    @Override
    public void createStatistic(String day) {
        R r = memberClient.registCount(day);
        //加之前先删除上一次的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        Integer num = (Integer) r.getData().get("num");
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setRegisterNum(num);
        statisticsDaily.setVideoViewNum(videoViewNum);
        statisticsDaily.setCourseNum(courseNum);
        statisticsDaily.setLoginNum(loginNum);
        baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> showChart(String type, String begin, String end) {
        //根据开始时间和结束时间查询
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        //查询固定列 查询两个列 ，一个是日期列 ，另一个是数量列
        wrapper.select("date_calculated", type);
        List<StatisticsDaily> statisticsDailies = baseMapper.selectList(wrapper);
        //返回值为两个集合 ，一个是日期集合作为x轴，另一个是数字集合为y轴
        List<String> dateList = new ArrayList<>();
        List<Integer> NumList = new ArrayList<>();
        for (StatisticsDaily statisticsDaily : statisticsDailies) {
            dateList.add(statisticsDaily.getDateCalculated());
            switch (type){
                case "register_num":
                    NumList.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num":
                    NumList.add(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num":
                    NumList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    NumList.add(statisticsDaily.getCourseNum());
                    break ;
                    default:
                     break;
            }
        }
        Map <String ,Object> map =new HashMap<>();
        map.put("dateList",dateList);
        map.put("NumList",NumList);
        return map;
    }
}
