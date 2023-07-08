package com.backend.project.system.schedule;

import com.backend.framework.config.ThreadPoolConfig;
import com.backend.project.system.service.IHgScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 */
@Configuration
@EnableScheduling
@Slf4j
public class HgSchedule {

    @Resource
    private ThreadPoolConfig threadPoolConfig;
    @Resource
    private IHgScheduleService hgScheduleService;

    /**
     * 今日足球数据
     * task - polling today football data
     */
//    @Scheduled(cron="0 0/1 * * * ?")
    @Scheduled(fixedDelay = 20000L)
    private void pollingFootballDataToday() {
//        threadPoolConfig.threadPoolExecutor().submit(() -> {
        hgScheduleService.pollingFootballDataToday();
//        });
    }

    /**
     * 今日足球数据
     * task - polling early football data
     */
//    @Scheduled(cron="0 0/1 * * * ?")
    @Scheduled(fixedDelay = 20000L)
    private void pollingFootballDataEarly() {
//        threadPoolConfig.threadPoolExecutor().submit(() -> {
        hgScheduleService.pollingFootballDataEarly();
//        });
    }

    public static void main(String[] args) {

    }

}
