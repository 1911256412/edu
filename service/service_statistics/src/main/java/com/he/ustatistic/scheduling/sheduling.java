package com.he.ustatistic.scheduling;

import com.he.ustatistic.service.StatisticsDailyService;
import com.he.ustatistic.utils.DateUtil;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class sheduling {

    @Resource
    private StatisticsDailyService statisticsDailyService;

    @Scheduled(cron = "0 0 1 * * ? ")
    public void run() {
        String date = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.createStatistic(date);

    }
}
