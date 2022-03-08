package com.he.ustatistic.service;

import com.he.ustatistic.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据; InnoDB free: 7168 kB 服务类
 * </p>
 *
 * @author hechunyu
 * @since 2022-03-06
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void createStatistic(String day);

    Map<String ,Object > showChart(String type, String begin, String end);
}
