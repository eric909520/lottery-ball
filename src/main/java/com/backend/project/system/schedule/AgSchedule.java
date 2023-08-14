package com.backend.project.system.schedule;

import com.backend.framework.config.ThreadPoolConfig;
import com.backend.project.system.service.IAgScheduleService;
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
public class AgSchedule {

    @Resource
    private ThreadPoolConfig threadPoolConfig;
    @Resource
    private IAgScheduleService agScheduleService;

    /**
     * 今日足球联赛数据
     * task - polling today football league data
     */
    @Scheduled(cron="0 0/30 * * * ?")
//    @Scheduled(fixedDelay = 20000L)
    private void pollingFootballDataToday() {
//        threadPoolConfig.threadPoolExecutor().submit(() -> {
        agScheduleService.pollingTodayFootballLeagueData();
//        });
    }

    /**
     * 早盘足球联赛数据
     * task - polling early football league data
     */
    @Scheduled(cron="0 5 0/6 * * ? ")
//    @Scheduled(fixedDelay = 20000L)
    private void pollingFootballDataEarly() {
//        threadPoolConfig.threadPoolExecutor().submit(() -> {
        agScheduleService.pollingEarlyFootballLeagueData();
//        });
    }

    /**
     * 获取比赛信息
     */
    @Scheduled(fixedDelay = 30000L)
    private void getGameInfo() {
//        threadPoolConfig.threadPoolExecutor().submit(() -> {
        agScheduleService.getGameInfo();
//        });
    }

    /**
     * 开赛 - 滚球
     */
    @Scheduled(cron="10 0/30 * * * ? ")
//    @Scheduled(fixedDelay = 20000L)
    private void rollStatus() {
//        threadPoolConfig.threadPoolExecutor().submit(() -> {
        agScheduleService.rollStatus();
//        });
    }

    /**
     * 球赛结束
     */
    @Scheduled(cron="10 0/30 * * * ? ")
//    @Scheduled(fixedDelay = 20000L)
    private void finishStatus() {
//        threadPoolConfig.threadPoolExecutor().submit(() -> {
        agScheduleService.finishStatus();
//        });
    }

}
