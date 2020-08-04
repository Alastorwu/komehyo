package com.komehyo.schedule;

import com.komehyo.service.GoodExcelCleanService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

@Configuration
@EnableScheduling
public class GoodExcelSchedule {

    @Resource
    private GoodExcelCleanService goodExcelCleanService;

    @Scheduled(cron = "0 0 0 * * ?")
    private void configureTasks() {
        goodExcelCleanService.cheanGoods();
    }
}
